package com.deliktas.internshipproject.serializer;

import com.deliktas.internshipproject.model.TransactionBanDTO;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.serialization.Deserializer;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class TransactionBanDTODeserializer implements Deserializer<List<TransactionBanDTO>> {
    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        Deserializer.super.configure(configs, isKey);
    }

    @Override
    public List<TransactionBanDTO> deserialize(String topic, byte[] data) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        List<TransactionBanDTO> dto = null;
        try {
            dto = objectMapper.readValue(data, new TypeReference<List<TransactionBanDTO>>() {});
        } catch (IOException e) {
            // Log the exception details
            System.out.println("Error during deserialization: {} " + e.getMessage());


        }

        return dto;
    }



    @Override
    public void close() {
        Deserializer.super.close();
    }
}
