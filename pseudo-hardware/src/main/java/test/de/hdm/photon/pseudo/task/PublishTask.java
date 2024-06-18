package test.de.hdm.photon.pseudo.task;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
public final class PublishTask extends AbstractPublishTask {

  @Autowired
  public PublishTask(@Nullable ObjectMapper objectMapper,
                     @Nullable IMqttClient mqttClient,
                     @Value("${application.mqtt.topic}") @Nullable String topic,
                     @Value("${application.hardware.mac}") @Nullable String macAddress,
                     @Value("${application.hardware.token}") @Nullable String token,
                     @Value("${application.hardware.values}") @Nullable String valuePattern) throws JsonProcessingException {
    super(objectMapper, mqttClient, topic, macAddress, token, valuePattern);
  }
}
