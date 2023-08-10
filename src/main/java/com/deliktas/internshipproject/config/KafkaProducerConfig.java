package com.deliktas.internshipproject.config;


import com.deliktas.internshipproject.client.RemoteServiceClient;
import com.deliktas.internshipproject.model.TransactionBanDTO;
import com.deliktas.internshipproject.serializer.TransactionBanDTOSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class KafkaProducerConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServer;


    @Bean
    public ProducerFactory<String, List<TransactionBanDTO>> producerFactory() {
        Map<String, Object> props = new HashMap<>();
            props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,bootstrapServer);
            props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
            props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, TransactionBanDTOSerializer.class);

        return new DefaultKafkaProducerFactory<>(props);
    }

    @Bean
    public KafkaTemplate<String,List<TransactionBanDTO>>
                kafkaTemplate(ProducerFactory<String, List<TransactionBanDTO>> producerFactory) {
        return new KafkaTemplate<>(producerFactory);
    }




}
