package com.deliktas.islemyasaklari.service;


import com.deliktas.islemyasaklari.model.TransactionBan;
import com.deliktas.islemyasaklari.repository.TransactionBanRepository;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class TransactionBanService {

    @Autowired
    private TransactionBanRepository transactionBanRepository;

    private final String SERVER_API_URL = "https://ws.spk.gov.tr/IdariYaptirimlar/api/IslemYasaklari";

    public void fetchDataAndSave() {

        try {
            RestTemplate restTemplate = new RestTemplate();

            TransactionBan[] data = restTemplate.getForObject(SERVER_API_URL,TransactionBan[].class);

            if(data == null)
                throw new RestClientException("Error occured while getting data from the url ");

            transactionBanRepository.saveAll(Arrays.asList(data));

        }catch (RestClientException e) {
            System.out.println(e.getMessage() + SERVER_API_URL);
        }

    }

    public ResponseEntity<List<TransactionBan>> getAllTransactionBans() {
        try {
            return new ResponseEntity<>(transactionBanRepository.findAll(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(transactionBanRepository.findAll(), HttpStatus.BAD_REQUEST);
    }
}
