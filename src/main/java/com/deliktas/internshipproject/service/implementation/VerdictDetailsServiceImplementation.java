package com.deliktas.internshipproject.service.implementation;

import com.deliktas.internshipproject.model.VerdictDetails;
import com.deliktas.internshipproject.repository.VerdictDetailsRepository;
import com.deliktas.internshipproject.service.VerdictDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VerdictDetailsServiceImplementation implements VerdictDetailsService {

    @Autowired
    private VerdictDetailsRepository verdictDetailsRepository;
    @Override
    public ResponseEntity<List<VerdictDetails>> getAllVerdictDetails() {
        return new ResponseEntity<>(verdictDetailsRepository.findAll(), HttpStatus.OK);
    }
}
