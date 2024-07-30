package test.de.hdm.photon.pseudo.config;

import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.UUID;


/**
 * Configuration class for setting up MQTT client.
 */
@Configuration
public class ApplicationConfig {
  /**
   * Creates and configures an MQTT client bean.
   *
   * @param url      the URL of the MQTT broker
   * @param user     the username for the MQTT broker
   * @param password the password for the MQTT broker
   * @return a configured {@link IMqttClient} instance
   * @throws MqttException if any MQTT error occurs
   */
  @Bean
  public @NotNull IMqttClient client(@Value("${application.mqtt.url}") @NotNull final String url,
                                     @Value("${application.mqtt.user}") @NotNull final String user,
                                     @Value("${application.mqtt.password}") @NotNull final String password) throws MqttException {
    final IMqttClient client = new MqttClient(url, UUID.randomUUID().toString());

    final MqttConnectOptions connOpts = new MqttConnectOptions();
    connOpts.setCleanSession(true);
    connOpts.setUserName(user);
    connOpts.setPassword(password.toCharArray());
    client.connect(connOpts);

    return client;
  }
}
