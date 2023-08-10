package com.deliktas.internshipproject.service.implementation;

import com.deliktas.internshipproject.client.RemoteServiceClient;
import com.deliktas.internshipproject.model.TransactionBan;
import com.deliktas.internshipproject.model.TransactionBanDTO;
import com.deliktas.internshipproject.service.DataManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DataManagerImpl implements DataManager {

    @Autowired
    private RemoteServiceClient remoteServiceClient;

    @Autowired
    private KafkaTemplate< String , List<TransactionBanDTO>> kafkaTemplate;

    @Override
    public void fetchDataAndSendToKafka() {
        kafkaTemplate.send("transaction-bans", remoteServiceClient.getRemoteData());
    }
}
