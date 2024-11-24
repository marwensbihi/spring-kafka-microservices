package com.angMetal.orders.kafka;

import models.FactureEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Serializer;

import java.util.Map;

public class FactureEventSerializer implements Serializer<FactureEvent> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        // Configuration logic, if needed
    }

    @Override
    public byte[] serialize(String topic, FactureEvent data) {
        try {
            if (data == null) {
                return null;
            }
            // Convert FactureEvent to JSON and then to byte array
            return objectMapper.writeValueAsBytes(data);
        } catch (JsonProcessingException e) {
            // Handle serialization exception
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void close() {
        // Any cleanup logic, if needed
    }
}
