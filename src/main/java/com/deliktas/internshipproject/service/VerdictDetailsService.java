package com.deliktas.internshipproject.service;


import com.deliktas.internshipproject.model.VerdictDetails;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

public interface VerdictDetailsService {

    ResponseEntity<List<VerdictDetails>> getAllVerdictDetails();

}
