package com.deliktas.internshipproject.service;


import com.deliktas.internshipproject.client.RemoteServiceClient;
import com.deliktas.internshipproject.model.TransactionBan;
import com.deliktas.internshipproject.repository.TransactionBanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionBanService {

    @Autowired
    private TransactionBanRepository transactionBanRepository;

    @Autowired
    private RemoteServiceClient remoteServiceClient;

    public boolean fetchDataAndSave() {

        try {

/*
        RestTemplate restTemplate = new RestTemplate();
            restTemplate.getForObject(SERVER_API_URL,TransactionBan[].class);
*/

        TransactionBan [] data = remoteServiceClient.getRemoteData();

            if(data == null)
                throw new RestClientException("Error occurred while fetching data from server ");

            transactionBanRepository.saveAll(Arrays.asList(data));
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
            e.printStackTrace();
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
            existingBan.setPayKodu(ban.getPayKodu());
            existingBan.setKurulKararTarihi(ban.getKurulKararTarihi());

            transactionBanRepository.save(existingBan);
            return new ResponseEntity<>("SUCCESS",HttpStatus.OK);
        } catch (RestClientException e) {
            e.printStackTrace();
        }

        return new ResponseEntity<>("FAILED",HttpStatus.BAD_REQUEST);
    }
}
