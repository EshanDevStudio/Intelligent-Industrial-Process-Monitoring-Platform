package com.eshan.backend.mqtt_client.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class MessageProcessor {
    private static final Logger logger = LoggerFactory.getLogger(MessageProcessor.class);

    @Async
    public void processMessage(String topic, String payload) {
        try {
            logger.info("Processing message from topic: {}", topic);
            //System.out.println("Processing message");
            // Parse the topic to extract device information
            // Example topic: "sensor/device123/data"
            String[] topicParts = topic.split("/");

            if (topicParts.length >= 3 && "sensor".equals(topicParts[0])) {
                String deviceId = topicParts[1];
                processSensorData(deviceId, payload);
            } else {
                logger.warn("Unknown topic format: {}", topic);
            }
        } catch (Exception e) {
            logger.error("Error processing message from topic: " + topic, e);
        }
    }

    private void processSensorData(String deviceId, String payload) {
        try {
            logger.info("Sensor data from device {}: {}", deviceId, payload);

            // business logic here

        } catch (Exception e) {
            logger.error("Error processing sensor data from device: " + deviceId, e);
        }
    }
}
