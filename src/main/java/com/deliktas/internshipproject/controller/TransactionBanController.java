package com.deliktas.internshipproject.controller;

import com.deliktas.internshipproject.model.Share;
import com.deliktas.internshipproject.model.TransactionBan;
import com.deliktas.internshipproject.model.TransactionBanDTO;
import com.deliktas.internshipproject.service.TransactionBanService;
import com.deliktas.internshipproject.service.implementation.DataManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("api/registry")
@CrossOrigin(origins = "*")
public class TransactionBanController {

    @Autowired
    private TransactionBanService transactionBanService;

    @Autowired
    private DataManagerImpl dataManager;

    @GetMapping("save-to-database")
    public ResponseEntity<String> fetchDataAndSave() {

        dataManager.fetchDataAndSendToKafka();

        boolean IsCreated = transactionBanService.fetchDataAndSave();

        return IsCreated ?
                new ResponseEntity<>("SUCCESS", HttpStatus.CREATED) :
                new ResponseEntity<>("FAILED", HttpStatus.BAD_REQUEST);
    }


    @GetMapping("all-bans-dto")
    public ResponseEntity<List<TransactionBanDTO>> getAllBansDTO() {
        return transactionBanService.getAllTransactionBansDTO();
    }

    @PostMapping("add")
    public ResponseEntity<String> getAllTransaction(@RequestBody TransactionBanDTO ban) {
        return transactionBanService.saveATransaction(ban);
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteATransaction(@PathVariable("id") Integer id) {
        return transactionBanService.deleteATransaction(id);
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateATransaction(@PathVariable("id") Integer id, @RequestBody TransactionBanDTO ban) {
        return transactionBanService.updateATransaction(id,ban);
    }

    @GetMapping("/verdict-by-registration-number")
    public ResponseEntity<List<TransactionBan>> getTransactionBanByRegistrationNumber(
            @RequestParam(required = false) String registrationNumber) {
        if (registrationNumber != null && !registrationNumber.isEmpty()) {
            return transactionBanService.getTransactionBanByRegNumber(registrationNumber);
        } else {
            // Handle the case where verdictNumber is not provided
            return ResponseEntity.ok(Collections.emptyList());
        }
    }

    @GetMapping("/verdict-by-name")
    public ResponseEntity<List<TransactionBan>> getTransactionBanByName(
            @RequestParam(required = false) String name) {
        if (name != null && !name.isEmpty()) {
            return transactionBanService.getTransactionBanByName(name);
        } else {
            // Handle the case where name is not provided
            return ResponseEntity.ok(Collections.emptyList());
        }
    }


}
