package com.deliktas.internshipproject.listener;

import com.deliktas.internshipproject.model.TransactionBanDTO;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class KafkaListeners {

    private List<TransactionBanDTO> transactionBanDTOSList = new ArrayList<>();
    private final Object lock = new Object();

    @KafkaListener(topics = "transaction-bans", groupId = "groupId")
    public void receiveMessage(List<TransactionBanDTO> transactionBanDTO) {
        synchronized (lock) {
            transactionBanDTOSList.addAll(transactionBanDTO);
            lock.notifyAll(); // Notify waiting threads
        }
    }

    public List<TransactionBanDTO> getMessage() {
        synchronized (lock) {
            while (transactionBanDTOSList.isEmpty()) {
                try {
                    lock.wait(); // Wait until data is available
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    throw new RuntimeException("Interrupted while waiting for data.");
                }
            }
            return new ArrayList<>(transactionBanDTOSList); // Return a copy of the list
        }
    }
}

