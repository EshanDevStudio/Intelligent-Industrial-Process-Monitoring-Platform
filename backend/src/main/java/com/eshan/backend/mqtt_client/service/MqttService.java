package com.eshan.backend.mqtt_client.service;

import com.hivemq.client.mqtt.mqtt5.Mqtt5BlockingClient;
import com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5Publish;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.CompletableFuture;

import static com.hivemq.client.mqtt.MqttGlobalPublishFilter.ALL;

@Service
public class MqttService {

    private static Logger logger = LoggerFactory.getLogger(MqttService.class);

    @Autowired
    private Mqtt5BlockingClient mqtt5Client;

    @Autowired
    private MessageProcessor messageProcessor;

    @Value("${mqtt.broker.username}")
    private String username;

    @Value("${mqtt.broker.password}")
    private String password;

    @Value("${mqtt.topic.sensor-data}")
    private String sensorDataTopic;

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

            // Subscribe to sensor data topic
            mqtt5Client.subscribeWith()
                    .topicFilter(sensorDataTopic)
                    .send();

            logger.info("Subscribed to topic: {}", sensorDataTopic);

            // Set up message listener using async API
            mqtt5Client.toAsync().publishes(ALL, this::handleIncommingMessage);

        } catch (Exception e) {
            logger.error("Failed to connect or subscribe to MQTT broker", e);
            throw new RuntimeException("MQTT connection failed", e);
        }
    }

    private void handleIncommingMessage(Mqtt5Publish mqtt5Publish) {
        try {
            String topic = mqtt5Publish.getTopic().toString();
            String payload = StandardCharsets.UTF_8.decode(
                    mqtt5Publish.getPayload().orElseThrow()
            ).toString();

            logger.info("Received message on topic '{}': {}", topic, payload);

            // Process the message asynchronously

            messageProcessor.processMessage(topic, payload);


        } catch (Exception e) {
            logger.error("Error handling incoming MQTT message", e);
        }
    }

    @PreDestroy
    public void disconnect() {
        if(mqtt5Client != null && mqtt5Client.getState().isConnected()) {
            try {
                mqtt5Client.disconnect();
                logger.info("MQTT Client disconnected");
            } catch (Exception e) {
                throw new RuntimeException("Error disconnecting MQTT client", e);
            }
        }
    }

}
