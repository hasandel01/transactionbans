package com.deliktas.internshipproject.controller;


import com.deliktas.internshipproject.model.TransactionBan;
import com.deliktas.internshipproject.service.TransactionBanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/registry")
public class TransactionBanController {

    @Autowired
    private TransactionBanService transactionBanService;

    @GetMapping("save-to-database")
    public ResponseEntity<String> fetchDataAndSave() {

        boolean IsCreated = transactionBanService.fetchDataAndSave();

        return IsCreated ?
                new ResponseEntity<>("SUCCESS", HttpStatus.CREATED) :
                new ResponseEntity<>("FAILED", HttpStatus.BAD_REQUEST);
    }


    @GetMapping("all-bans")
    public ResponseEntity<List<TransactionBan>> getAllTransactionBans() {
        return transactionBanService.getAllTransactionBans();
    }


    @PostMapping("add")
    public ResponseEntity<String> getAllTransaction(@RequestBody TransactionBan ban) {
        return transactionBanService.saveATransaction(ban);
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteATransaction(@PathVariable("id") Integer id) {
        return transactionBanService.deleteATransaction(id);
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateATransaction(@PathVariable("id") Integer id, @RequestBody TransactionBan ban) {
        return transactionBanService.updateATransaction(id,ban);
    }


}