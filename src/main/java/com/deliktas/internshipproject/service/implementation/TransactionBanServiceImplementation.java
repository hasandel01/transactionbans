package com.deliktas.internshipproject.service.implementation;

import com.deliktas.internshipproject.client.RemoteServiceClient;
import com.deliktas.internshipproject.listener.KafkaListeners;
import com.deliktas.internshipproject.mapper.EntityMapper;
import com.deliktas.internshipproject.mapper.EntityMapperCustomImpl;
import com.deliktas.internshipproject.model.*;
import com.deliktas.internshipproject.repository.ShareRepository;
import com.deliktas.internshipproject.repository.TransactionBanRepository;
import com.deliktas.internshipproject.repository.VerdictDetailsRepository;
import com.deliktas.internshipproject.service.TransactionBanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;

import java.util.*;

@Service
public class TransactionBanServiceImplementation implements TransactionBanService {

    @Autowired
    private TransactionBanRepository transactionBanRepository;

    @Autowired
    private ShareRepository shareRepository;

    @Autowired
    private VerdictDetailsRepository verdictDetailsRepository;

    private final EntityMapper entityMapper;

    @Autowired
    private KafkaListeners kafkaListeners;

    public TransactionBanServiceImplementation(EntityMapperCustomImpl entityMapper) {
        this.entityMapper = entityMapper;
    }

    private List<TransactionBanDTO> dataDTO;

    @Override
    public boolean fetchDataAndSave() {

        try {

          List<TransactionBanDTO> dataDTO = kafkaListeners.getMessage();

            if (dataDTO == null) {
                throw new RestClientException("Error occurred while fetching data from the server.");
            }

            for (TransactionBanDTO data : dataDTO) {
                // Fetch or create the associated Share entity based on the pay field
                Share share = shareRepository.findByPay(data.getPay());
                if (share == null) {
                    share = new Share(data.getPay(), data.getPayKodu());
                    shareRepository.save(share); // Persist the Share entity
                }

                TransactionBan transactionBan = entityMapper.transactionBanDtoToTransactionBan(data);
                transactionBan.setPay(share);
                transactionBanRepository.save(transactionBan); // Save the TransactionBan first

                //UPDATING OR ADDING NEW VERDICT DETAILS
                VerdictDetails verdictDetails = transactionBan.verdictDetailsSetToVerdictDetails();

                if (verdictDetails != null) {
                    // Associate the transactionBan with the verdictDetails
                    verdictDetails.getTransactionBans().add(transactionBan);
                    verdictDetailsRepository.save(verdictDetails);
                }
            }

            return true;
        } catch (RestClientException e) {
            System.out.println(e.getMessage());
        }

        return false;
    }

    @Override
    public ResponseEntity<String> saveATransaction(TransactionBanDTO transactionBanDTO) {

        try {

            Share share = shareRepository.findByPay(transactionBanDTO.getPay());

            if (share == null) {
                share = new Share(transactionBanDTO.getPay(), transactionBanDTO.getPayKodu());
                shareRepository.save(share);
            }

            TransactionBan transactionBan = entityMapper.transactionBanDtoToTransactionBan(transactionBanDTO);
            transactionBan.setPay(share);
            VerdictDetails verdictDetails = transactionBan.verdictDetailsSetToVerdictDetails();
            transactionBanRepository.save(transactionBan); // Save the TransactionBan first
            verdictDetails.addBan(transactionBan); // Add TransactionBan to VerdictDetails
            verdictDetailsRepository.save(verdictDetails); // Save VerdictDetails after adding TransactionBan
            return new ResponseEntity<>("SUCCESS", HttpStatus.CREATED);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>("FAILURE",HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<String> deleteATransaction(Integer id) {

        Optional<TransactionBan> toBeDeleted = transactionBanRepository.findById(id);

        try {

            if(toBeDeleted.isEmpty())
                throw new RestClientException("Ban with the id " + id + " is not present.");

            TransactionBan existingBan = toBeDeleted.get();
            transactionBanRepository.delete(existingBan);

            List<VerdictDetails> verdictDetails = verdictDetailsRepository.findAll();


            for (VerdictDetails verdictDetails1 : verdictDetails) {
                for (TransactionBan ban : verdictDetails1.getTransactionBans()) {
                    if(ban.equals(existingBan)) {
                        verdictDetailsRepository.delete(verdictDetails1);
                        verdictDetails1.removeBan(existingBan);
                        verdictDetailsRepository.save(verdictDetails1);
                    }
                }
            }

            List<TransactionBan> allBans = transactionBanRepository.findAll();

            boolean found = false;
            for (TransactionBan ban : allBans) {

                if(ban.getPay().getPay() == null)
                        continue;

                if(ban.getPay().getPay().equals(existingBan.getPay().getPay())) {
                    found = true;
                    break;
                }
            }

            if(!found)
                shareRepository.delete(existingBan.getPay());

            return new ResponseEntity<>("SUCCESS",HttpStatus.OK);
        } catch (RestClientException e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>("FAILURE",HttpStatus.BAD_REQUEST);

        }
    }

    @Override
    public ResponseEntity<String> updateATransaction(Integer id, TransactionBanDTO ban) {

        Optional<TransactionBan> toBeDeleted = transactionBanRepository.findById(id);

        try {
            //Check whether the object is in the database or not.
            if(toBeDeleted.isEmpty())
                throw new RestClientException("Ban with the id " + id + " is not present.");

            //Save basics.
            TransactionBan existingBan = toBeDeleted.get();
            existingBan.setUnvan(ban.getUnvan());
            existingBan.setMkkSicilNo(ban.getMkkSicilNo());

            //Look for existing shares, if share is already in database then set it to the new ban, if not create a new
            //share and save it to the new one also save it to the repository.
            List<Share> allShares = shareRepository.findAll();
            boolean found = false;
            for (Share myShare : allShares) {

                if(myShare.getPay() == null)
                    continue;

                if(myShare.getPay().equals(ban.getPay())) {
                    found = true;
                    existingBan.setPay(myShare);
                    break;
                }
            }

            if(!found) {
                Share newShare = new Share(ban.getPay(), ban.getPayKodu());
                existingBan.setPay(newShare);
                shareRepository.save(newShare);
            }


            //Check VD is already the same.
            Set<VerdictDetails> verdictDetailsSet = existingBan.getVerdictDetails();

            for (VerdictDetails verdictDetails : verdictDetailsSet) {

                if(verdictDetails.getKurulKararNo().equals(ban.getKurulKararNo()) &&
                        verdictDetails.getKurulKararTarihi().equals(ban.getKurulKararTarihi()))
                    break;

                for(TransactionBan transactionBan : verdictDetails.getTransactionBans()) {
                    if(transactionBan.equals(existingBan)) {
                        verdictDetails.getTransactionBans().remove(existingBan);
                    }
                }
            }

            List<VerdictDetails> verdictDetails = verdictDetailsRepository.findAll();


            boolean foundVD = false;
            for (VerdictDetails verdictDetails1 : verdictDetails ) {
                if(verdictDetails1.getKurulKararNo().equals(ban.getKurulKararNo()) &&
                        verdictDetails1.getKurulKararTarihi().equals(ban.getKurulKararTarihi())) {
                    foundVD = true;
                    break;
                }
            }


            existingBan.setVerdictDetails(verdictDetailsSet);

            transactionBanRepository.save(existingBan);

            VerdictDetails newVerdictDetails = existingBan.verdictDetailsSetToVerdictDetails();
            newVerdictDetails.addBan(existingBan);

            if (!foundVD)
                verdictDetailsRepository.save(newVerdictDetails);

            return new ResponseEntity<>("SUCCESS",HttpStatus.OK);
        } catch (RestClientException e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>("FAILED",HttpStatus.BAD_REQUEST);

        }
    }

    @Override
    public ResponseEntity<List<TransactionBan>> getTransactionBanByName(String name) {
        try {
            return new ResponseEntity<>(transactionBanRepository.findByName(name),HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return new ResponseEntity<>(transactionBanRepository.findByName(name),HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<List<TransactionBan>> getTransactionBanByRegNumber(String registrationNumber) {
        try {
            return new ResponseEntity<>(transactionBanRepository.findByMkkSicilNo(registrationNumber),
                    HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return new ResponseEntity<>(transactionBanRepository.findByMkkSicilNo(registrationNumber),
                HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<List<TransactionBanDTO>> getAllTransactionBansDTO() {

        List<TransactionBan> list = transactionBanRepository.findAll();
        List<TransactionBanDTO> allData = new ArrayList<>();
        List<VerdictDetails> verdictDetails = verdictDetailsRepository.findAll();

        for(int i = 0 ; i  < list.size() ; i++) {
            allData.add(entityMapper.transactionBanToTransactionBanDTO(list.get(i)));
            allData.get(i).setPay((entityMapper.shareToShareDTO(list.get(i).getPay())).getPay());
            allData.get(i).setPayKodu((entityMapper.shareToShareDTO(list.get(i).getPay())).getPayKodu());
        }

        return new ResponseEntity<>(allData,HttpStatus.OK);
    }

}
