package com.deliktas.internshipproject.serializer;

import com.deliktas.internshipproject.model.TransactionBanDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.serialization.Deserializer;

import java.io.IOException;
import java.util.Map;

public class TransactionBanDTODeserializer implements Deserializer<TransactionBanDTO> {
    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        Deserializer.super.configure(configs, isKey);
    }

    @Override
    public TransactionBanDTO deserialize(String topic, byte [] data) {
        ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            TransactionBanDTO dto = null;
            try {
                dto = objectMapper.readValue(data,TransactionBanDTO.class);
            }catch (IOException e) {
                e.printStackTrace();
            }

        return dto;
    }

    @Override
    public TransactionBanDTO deserialize(String topic, Headers headers, byte[] data) {
        return Deserializer.super.deserialize(topic, headers, data);
    }

    @Override
    public void close() {
        Deserializer.super.close();
    }
}
