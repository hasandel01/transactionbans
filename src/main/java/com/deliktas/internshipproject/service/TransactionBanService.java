package com.deliktas.internshipproject.service;


import com.deliktas.internshipproject.client.RemoteServiceClient;
import com.deliktas.internshipproject.model.Share;
import com.deliktas.internshipproject.model.TransactionBan;
import com.deliktas.internshipproject.model.TransactionBanDTO;
import com.deliktas.internshipproject.repository.ShareRepository;
import com.deliktas.internshipproject.repository.TransactionBanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class TransactionBanService {

    @Autowired
    private TransactionBanRepository transactionBanRepository;

    @Autowired
    private RemoteServiceClient remoteServiceClient;

    @Autowired
    private ShareRepository shareRepository;

    public boolean fetchDataAndSave() {
        try {
            List<TransactionBanDTO> dataDTO = remoteServiceClient.getRemoteData();
            List<TransactionBan> transactionBans = new ArrayList<>();

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

                TransactionBan transactionBan = new TransactionBan();
                transactionBan.setPay(share);
                transactionBan.setUnvan(data.getUnvan());
                transactionBan.setKurulKararNo(data.getKurulKararNo());
                transactionBan.setMkkSicilNo(data.getMkkSicilNo());
                transactionBan.setKurulKararTarihi(data.getKurulKararTarihi());

                transactionBans.add(transactionBan);
            }

            transactionBanRepository.saveAll(transactionBans);
            return true;
        } catch (RestClientException e) {
            System.out.println(e.getMessage());
        }

        return false;
    }


    public ResponseEntity<List<TransactionBan>> getAllTransactionBans() {
        return new ResponseEntity<>(transactionBanRepository.findAll(), HttpStatus.OK);
    }

    public ResponseEntity<String > saveATransaction(TransactionBan transactionBan ) {
        transactionBanRepository.save(transactionBan);
        return new ResponseEntity<>("SUCCESS", HttpStatus.CREATED);
    }

    public ResponseEntity<String> deleteATransaction(Integer id) {

        Optional<TransactionBan> toBeDeleted = transactionBanRepository.findById(id);

        try {

            if(toBeDeleted.isEmpty())
                throw new RestClientException("Ban with the id " + id + " is not present.");

            TransactionBan existingBan = toBeDeleted.get();
            transactionBanRepository.delete(existingBan);

            return new ResponseEntity<>("SUCCESS",HttpStatus.OK);
        } catch (RestClientException e) {
            System.out.println(e.getMessage());
        }

        return new ResponseEntity<>("FAILED",HttpStatus.BAD_REQUEST);
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
            existingBan.setKurulKararNo(ban.getKurulKararNo());
            existingBan.setKurulKararTarihi(ban.getKurulKararTarihi());

            transactionBanRepository.save(existingBan);
            return new ResponseEntity<>("SUCCESS",HttpStatus.OK);
        } catch (RestClientException e) {
            System.out.println(e.getMessage());
        }

        return new ResponseEntity<>("FAILED",HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<List<TransactionBan>> getTransactionBanByVerdictNo(String verdictNo) {
        try {
            return new ResponseEntity<>(transactionBanRepository.findByVerdictNo(verdictNo), HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return new ResponseEntity<>(transactionBanRepository.findByVerdictNo(verdictNo), HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<List<TransactionBan>> getTransactionBanByName(String name) {
        try {
            return new ResponseEntity<>(transactionBanRepository.findByVerdictNo(name),HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return new ResponseEntity<>(transactionBanRepository.findByVerdictNo(name),HttpStatus.BAD_REQUEST);
    }
}
