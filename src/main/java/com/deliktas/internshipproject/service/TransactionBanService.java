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

public interface TransactionBanService {

    boolean fetchDataAndSave();

    ResponseEntity<String> saveATransaction(TransactionBanDTO transactionBanDTO );

    ResponseEntity<String> deleteATransaction(Integer id);
    ResponseEntity<String> updateATransaction(Integer id,TransactionBanDTO ban);

    ResponseEntity<List<TransactionBan>> getTransactionBanByName(String name);

    ResponseEntity<List<TransactionBan>> getTransactionBanByRegNumber(String registrationNumber);

    ResponseEntity<List<TransactionBanDTO>> getAllTransactionBansDTO();

}
