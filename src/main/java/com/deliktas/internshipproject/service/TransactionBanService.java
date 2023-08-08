package com.deliktas.internshipproject.service;


import com.deliktas.internshipproject.client.RemoteServiceClient;
import com.deliktas.internshipproject.model.Share;
import com.deliktas.internshipproject.model.TransactionBan;
import com.deliktas.internshipproject.model.TransactionBanDTO;
import com.deliktas.internshipproject.model.VerdictDetails;
import com.deliktas.internshipproject.repository.ShareRepository;
import com.deliktas.internshipproject.repository.TransactionBanRepository;
import com.deliktas.internshipproject.repository.VerdictDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;

import java.util.*;

@Service
public class TransactionBanService {

    @Autowired
    private TransactionBanRepository transactionBanRepository;

    @Autowired
    private RemoteServiceClient remoteServiceClient;

    @Autowired
    private ShareRepository shareRepository;

    @Autowired
    private VerdictDetailsRepository verdictDetailsRepository;


    public boolean fetchDataAndSave() {

        try {
            List<TransactionBanDTO> dataDTO = remoteServiceClient.getRemoteData();

            if (dataDTO == null) {
                throw new RestClientException("Error occurred while fetching data from the server.");
            }

            for (TransactionBanDTO data : dataDTO) {
                // Fetch or create the associated Share entity based on the pay field
                Share share = shareRepository.findByPay(data.getPay());
                if (share == null) {
                    share = new Share(data.getPay(), data.getPayKodu());
                    shareRepository.save(share);
                }

                VerdictDetails verdictDetails = new VerdictDetails(data.getKurulKararTarihi(), data.getKurulKararNo());

                TransactionBan transactionBan = new TransactionBan();
                transactionBan.setPay(share);
                transactionBan.addVerdict(verdictDetails);
                transactionBan.setUnvan(data.getUnvan());
                transactionBan.setMkkSicilNo(data.getMkkSicilNo());

                transactionBanRepository.save(transactionBan); // Save the TransactionBan first

                verdictDetails.addBan(transactionBan); // Add TransactionBan to VerdictDetails
                verdictDetailsRepository.save(verdictDetails); // Save VerdictDetails after adding TransactionBan

                // Since you have cascading settings, there's no need to explicitly save VerdictDetails again here.
                // The changes made in verdictDetails.addBan(transactionBan) will be automatically persisted.

            }

            return true;
        } catch (RestClientException e) {
            System.out.println(e.getMessage());
        }

        return false;
    }



    public ResponseEntity<List<TransactionBan>> getAllTransactionBans() {
        return new ResponseEntity<>(transactionBanRepository.findAll(), HttpStatus.OK);
    }

    public ResponseEntity<String> saveATransaction(TransactionBanDTO transactionBanDTO ) {

        try {

            Share share = shareRepository.findByPay(transactionBanDTO.getPay());

            if (share == null) {
                share = new Share(transactionBanDTO.getPay(), transactionBanDTO.getPayKodu());
                shareRepository.save(share);
            }

            VerdictDetails verdictDetails = new VerdictDetails(transactionBanDTO.getKurulKararTarihi(),
                                                                transactionBanDTO.getKurulKararNo());

            TransactionBan transactionBan = new TransactionBan();
            transactionBan.setPay(share);
            transactionBan.addVerdict(verdictDetails);
            transactionBan.setUnvan(transactionBanDTO.getUnvan());
            transactionBan.setMkkSicilNo(transactionBanDTO.getMkkSicilNo());

            transactionBanRepository.save(transactionBan); // Save the TransactionBan first
            verdictDetails.addBan(transactionBan); // Add TransactionBan to VerdictDetails
            verdictDetailsRepository.save(verdictDetails); // Save VerdictDetails after adding TransactionBan

            return new ResponseEntity<>("SUCCESS", HttpStatus.CREATED);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>("FAILURE",HttpStatus.BAD_REQUEST);
        }
    }

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

    public ResponseEntity<String> updateATransaction(Integer id,TransactionBan ban) {

        Optional<TransactionBan> toBeDeleted = transactionBanRepository.findById(id);

        try {

            if(toBeDeleted.isEmpty())
                throw new RestClientException("Ban with the id " + id + " is not present.");

            TransactionBan existingBan = toBeDeleted.get();
            existingBan.setPay(ban.getPay());
            existingBan.setUnvan(ban.getUnvan());
            existingBan.setMkkSicilNo(ban.getMkkSicilNo());

            transactionBanRepository.save(existingBan);
            return new ResponseEntity<>("SUCCESS",HttpStatus.OK);
        } catch (RestClientException e) {
            System.out.println(e.getMessage());
        }

        return new ResponseEntity<>("FAILED",HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<List<TransactionBan>> getTransactionBanByName(String name) {
        try {
            return new ResponseEntity<>(transactionBanRepository.findByName(name),HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return new ResponseEntity<>(transactionBanRepository.findByName(name),HttpStatus.BAD_REQUEST);
    }

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

    public ResponseEntity<List<TransactionBanDTO>> getAllTransactionBansDTO() {

            List<TransactionBan> list = transactionBanRepository.findAll();
            List<TransactionBanDTO> allData = new ArrayList<>();
            List<VerdictDetails> verdictDetails = verdictDetailsRepository.findAll();

                for(int i = 0 ; i  < list.size() ; i++) {

                    TransactionBanDTO newData = TransactionBanDTO.builder()
                            .unvan(list.get(i).getUnvan())
                            .mkkSicilNo(list.get(i).getMkkSicilNo())
                            .pay(list.get(i).getPay().getPay())
                            .payKodu(list.get(i).getPay().getPay())
                            .kurulKararNo(verdictDetails.get(i).getKurulKararNo())
                            .kurulKararTarihi(verdictDetails.get(i).getKurulKararTarihi())
                            .build();
                    allData.add(newData);
                }

        return new ResponseEntity<>(allData,HttpStatus.OK);
    }

    public ResponseEntity<List<Share>> getAllShares() {
        return new ResponseEntity<>(shareRepository.findAll(),HttpStatus.OK);
    }


    public ResponseEntity<String> updateTransactionBan() {

        return new ResponseEntity<>("SUCCESS",HttpStatus.OK);
    }
}
