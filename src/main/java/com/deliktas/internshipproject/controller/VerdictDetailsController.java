package com.deliktas.internshipproject.controller;


import com.deliktas.internshipproject.model.VerdictDetailsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/verdict-details")
public class VerdictDetailsController {

    private VerdictDetailsDTO verdictDetailsDTO;

    /*
    @GetMapping("/get-all")
    public ResponseEntity<List<VerdictDetailsDTO>> getAllVerdictDetails() {
        return new ResponseEntity<>(verdictDetailsDTO, HttpStatus.OK);
    }*/
}
