package com.deliktas.internshipproject.controller;

import com.deliktas.internshipproject.model.TransactionBanDTO;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/kafka-messages")
public class KafkaMessageController {

    private KafkaTemplate<String, List<TransactionBanDTO>> kafkaTemplate;

    public KafkaMessageController(KafkaTemplate<String,List<TransactionBanDTO>> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @PostMapping
    public void publish(@RequestBody List<TransactionBanDTO> transactionBanDTO) {
        kafkaTemplate.send("transaction-bans", transactionBanDTO);
    }

}
