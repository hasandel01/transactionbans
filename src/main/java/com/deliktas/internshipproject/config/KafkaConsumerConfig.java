package com.deliktas.internshipproject.config;


import com.deliktas.internshipproject.model.TransactionBanDTO;
import com.deliktas.internshipproject.serializer.TransactionBanDTODeserializer;
import com.deliktas.internshipproject.serializer.TransactionBanDTOSerializer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class KafkaConsumerConfig {

    @Value("localhost:9092")
    private String bootstrapServer;

    @Bean
    public ConsumerFactory<String, List<TransactionBanDTO>> consumerFactory() {
        Map<String ,Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,bootstrapServer);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, TransactionBanDTODeserializer.class);
        return new DefaultKafkaConsumerFactory<>(props);
    }

    @Bean
    public KafkaListenerContainerFactory<
            ConcurrentMessageListenerContainer<String, List<TransactionBanDTO>>>
                    factory(ConsumerFactory<String, List<TransactionBanDTO>> consumerFactory) {
        ConcurrentKafkaListenerContainerFactory<String, List<TransactionBanDTO>> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);
        return factory;
    }


}
