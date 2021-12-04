package com.api.power.config;

import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

@Configuration
public class MqttConfig {

    @Value("${config.mqtt.host}")
    private String host;

    @Value("${config.mqtt.topic}")
    private String topic;

    @Value("${config.mqtt.username}")
    private String username;

    @Value("${config.mqtt.password}")
    private String password;

    @Bean
    public MqttPahoClientFactory mqttClientFactory() {
        final DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
        final MqttConnectOptions options = new MqttConnectOptions();
        options.setServerURIs(new String[] { "tcp://" + host });
        options.setUserName(username);
        options.setPassword(password.toCharArray());
        factory.setConnectionOptions(options);

        return factory;
    }

    @Bean
    @ServiceActivator(inputChannel = "mqttOutboundChannel")
    public MessageHandler mqttOutbound() {
        final MqttPahoMessageHandler messageHandler = new MqttPahoMessageHandler("powerApiClient", mqttClientFactory());
        messageHandler.setAsync(true);
        messageHandler.setDefaultTopic(topic);

        return messageHandler;
    }

    @Bean
    public MessageChannel mqttOutboundChannel() {
        return new DirectChannel();
    }

    @MessagingGateway(defaultRequestChannel = "mqttOutboundChannel")
    public interface MqttGateway {
        void sendToMqtt(final String data);
    }

}