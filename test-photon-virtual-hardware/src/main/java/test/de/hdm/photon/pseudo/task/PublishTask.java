package test.de.hdm.photon.pseudo.task;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


/**
 * PublishTask is a concrete implementation of AbstractPublishTask that is used to publish messages
 * to an MQTT topic.
 */
@Component
public final class PublishTask extends AbstractPublishTask {

  /**
   * Constructs a PublishTask with the given parameters.
   *
   * @param objectMapper the ObjectMapper for JSON processing
   * @param mqttClient   the MQTT client for publishing messages
   * @param topic        the MQTT topic to publish to
   * @param macAddress   the MAC address for the message payload
   * @param token        the token for the message payload
   * @param valuePattern the JSON string representing the value pattern
   * @throws JsonProcessingException if the valuePattern cannot be processed
   */
  @Autowired
  public PublishTask(@Nullable ObjectMapper objectMapper,
                     @Nullable IMqttClient mqttClient,
                     @Value("${application.mqtt.topic}") @Nullable String topic,
                     @Value("${application.hardware.mac}") @Nullable String macAddress,
                     @Value("${application.hardware.token}") @Nullable String token,
                     @Value("${application.hardware.ip}") @Nullable String ipAddress,
                     @Value("${application.hardware.resolution}") int bitResolution,
                     @Value("${application.hardware.values}") @Nullable String valuePattern) throws JsonProcessingException {
    super(objectMapper, mqttClient, topic, macAddress, token, ipAddress, bitResolution, valuePattern);
  }
}
