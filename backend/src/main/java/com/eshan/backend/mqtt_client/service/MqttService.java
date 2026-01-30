package com.eshan.backend.mqtt_client.service;

import com.hivemq.client.mqtt.mqtt5.Mqtt5BlockingClient;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

@Service
public class MqttService {

    private static Logger logger = LoggerFactory.getLogger(MqttService.class);

    @Autowired
    private Mqtt5BlockingClient mqtt5Client;

    @Value("${mqtt.broker.username}")
    private String username;

    @Value("${mqtt.broker.password}")
    private String password;

    @PostConstruct
    public void connectAndSubscribe() {
        try {
            // connect to HiveMQ Cloud
            mqtt5Client.connectWith()
                    .simpleAuth()
                    .username(username)
                    .password(StandardCharsets.UTF_8.encode(password))
                    .applySimpleAuth()
                    .send();

            logger.info("MQTT Client connected successfully to broker");

        } catch (Exception e) {
            logger.error("Failed to connect or subscribe to MQTT broker", e);
            throw new RuntimeException("MQTT connection failed", e);
        }
    }

}
