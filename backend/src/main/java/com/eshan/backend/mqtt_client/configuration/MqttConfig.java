package com.eshan.backend.mqtt_client.configuration;

import com.hivemq.client.mqtt.MqttClient;
import com.hivemq.client.mqtt.mqtt5.Mqtt5BlockingClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MqttConfig {

    @Value("${mqtt.broker.host}")
    private String host;

    @Value("${mqtt.broker.port:8883}")
    private int port;

    @Bean
    public Mqtt5BlockingClient mqtt5Client() {
        return MqttClient.builder()
                .useMqttVersion5()
                .serverHost(host)
                .serverPort(port)
                .sslWithDefaultConfig()
                .buildBlocking();
    }
}
