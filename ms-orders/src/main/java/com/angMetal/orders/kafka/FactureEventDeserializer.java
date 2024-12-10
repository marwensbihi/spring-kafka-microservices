package com.angMetal.orders.kafka;

import models.FactureEvent;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serializer;
import org.apache.kafka.common.serialization.Serdes;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.streams.errors.StreamsException;

public class FactureEventSerde extends Serdes.WrapperSerde<FactureEvent> {

    public FactureEventSerde() {
        super(new FactureEventSerializer(), new FactureEventDeserializer());
    }

    public static class FactureEventSerializer implements Serializer<FactureEvent> {

        private final ObjectMapper objectMapper = new ObjectMapper();

        @Override
        public byte[] serialize(String topic, FactureEvent data) {
            try {
                return objectMapper.writeValueAsBytes(data);
            } catch (Exception e) {
                throw new StreamsException("Error serializing FactureEvent", e);
            }
        }
    }

    public static class FactureEventDeserializer implements Deserializer<FactureEvent> {

        private final ObjectMapper objectMapper = new ObjectMapper();

        @Override
        public FactureEvent deserialize(String topic, byte[] data) {
            try {
                return objectMapper.readValue(data, FactureEvent.class);
            } catch (Exception e) {
                throw new StreamsException("Error deserializing FactureEvent", e);
            }
        }
    }
}
