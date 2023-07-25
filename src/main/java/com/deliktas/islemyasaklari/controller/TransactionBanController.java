package com.deliktas.islemyasaklari.controller;


import com.deliktas.islemyasaklari.model.TransactionBan;
import com.deliktas.islemyasaklari.service.TransactionBanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TransactionBanController {

    @Autowired
    private TransactionBanService transactionBanService;

    @GetMapping("tümislemyasaklari")
    public ResponseEntity<List<TransactionBan>> getAllTransactionBans() {
        return transactionBanService.getAllTransactionBans();
    }

    @GetMapping("/kaydet")
    public ResponseEntity<String> fetchDataFromServer() {
        transactionBanService.fetchDataAndSave();
        return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
    }

}
