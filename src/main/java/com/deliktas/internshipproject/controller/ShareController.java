package com.deliktas.internshipproject.controller;

import com.deliktas.internshipproject.model.Share;
import com.deliktas.internshipproject.model.ShareDTO;
import com.deliktas.internshipproject.service.ShareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/shares/")
public class ShareController {

    @Autowired
    private ShareService shareService;

    @GetMapping("/get-all")
    public ResponseEntity<List<ShareDTO>> getAllShares() {
        return shareService.getAllShares();
    }

}
