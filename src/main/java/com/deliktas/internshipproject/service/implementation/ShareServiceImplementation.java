package com.deliktas.internshipproject.service.implementation;

import com.deliktas.internshipproject.mapper.EntityMapper;
import com.deliktas.internshipproject.mapper.EntityMapperCustomImpl;
import com.deliktas.internshipproject.model.Share;
import com.deliktas.internshipproject.model.ShareDTO;
import com.deliktas.internshipproject.repository.ShareRepository;
import com.deliktas.internshipproject.service.ShareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.Serial;
import java.util.ArrayList;
import java.util.List;

@Service
public class ShareServiceImplementation implements ShareService {

    @Autowired
    private ShareRepository shareRepository;

    private final EntityMapper entityMapper;

    public ShareServiceImplementation(EntityMapperCustomImpl entityMapper) {
        this.entityMapper = entityMapper;
    }

    @Override
    public ResponseEntity<List<ShareDTO>> getAllShares() {
        List<ShareDTO> shareList = new ArrayList<>();

        for(Share share : shareRepository.findAll()) {
                shareList.add(entityMapper.shareToShareDTO(share));
        }

        return new ResponseEntity<>(shareList,HttpStatus.OK);
    }

}
