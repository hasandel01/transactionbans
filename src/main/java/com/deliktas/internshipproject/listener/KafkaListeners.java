package com.deliktas.internshipproject.listener;

import com.deliktas.internshipproject.model.TransactionBanDTO;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;

import java.util.List;

@Configuration
public class KafkaListeners {

    @KafkaListener(topics = "transaction-bans", groupId = "your-group-id")
    public void receiveMessage(List<TransactionBanDTO> transactionBanDTO) {
        // Process the received TransactionBanDTO object
        System.out.println("Received: " + transactionBanDTO);
    }

}
