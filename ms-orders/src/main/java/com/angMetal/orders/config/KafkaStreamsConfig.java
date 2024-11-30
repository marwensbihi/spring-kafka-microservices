package com.angMetal.orders.config;


import models.FactureEvent;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.StreamsConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Configuration
public class KafkaStreamsConfig {

    private static final String FACTURE_TOPIC = "facture-events";

    @Bean
    public KStream<String, FactureEvent> kStream(StreamsBuilder streamsBuilder) {
        // Create the stream from the topic
        KStream<String, FactureEvent> stream = streamsBuilder.stream(FACTURE_TOPIC);

        // Example processing of the stream (for illustration)
        stream.foreach((key, value) -> {
            System.out.println("Processing FactureEvent with key: " + key + ", value: " + value);
        });

        return stream;
    }

    @Bean
    public KafkaStreams kafkaStreams(StreamsBuilder streamsBuilder) {
        // Kafka Streams configuration properties
        Properties properties = new Properties();
        properties.put(StreamsConfig.APPLICATION_ID_CONFIG, "facture-streams-app");
        properties.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");

        // Create and start KafkaStreams instance
        KafkaStreams kafkaStreams = new KafkaStreams(streamsBuilder.build(), properties);
        kafkaStreams.start();
        return kafkaStreams;
    }
}