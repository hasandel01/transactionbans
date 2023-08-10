package com.deliktas.internshipproject.service;

import com.deliktas.internshipproject.model.ShareDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ShareService {

    ResponseEntity<List<ShareDTO>> getAllShares();
}
