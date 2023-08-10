package com.deliktas.internshipproject.serializer;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.kafka.common.serialization.Serializer;
import org.springframework.stereotype.Component;
import com.deliktas.internshipproject.model.TransactionBanDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

@Component
public class TransactionBanDTOSerializer implements Serializer<List<TransactionBanDTO>> {


    @Override
    public byte[] serialize(String topic, List<TransactionBanDTO> data) {
        byte[] result = null;
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        try {
            result  = objectMapper.writeValueAsBytes(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        // No additional configuration required
    }

    @Override
    public void close() {
        // No resources to close
    }
}
