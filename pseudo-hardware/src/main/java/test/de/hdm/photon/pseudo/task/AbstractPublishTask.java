package test.de.hdm.photon.pseudo.task;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.hdm.photon.schema.payload.IMessagePayload;
import de.hdm.photon.schema.payload.ImmutableMessagePayload;
import lombok.extern.log4j.Log4j2;
import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.TimerTask;
import java.util.concurrent.ThreadLocalRandom;


@Log4j2
public abstract class AbstractPublishTask extends TimerTask {

  private final @NotNull ObjectMapper objectMapper;
  private final @NotNull IMqttClient mqttClient;

  private final @NotNull String topic;
  private final @NotNull String macAddress;
  private final @NotNull String token;
  private final @NotNull Map<String, Object> values;

  protected AbstractPublishTask(@Nullable final ObjectMapper objectMapper,
                                @Nullable final IMqttClient mqttClient,
                                @Nullable final String topic,
                                @Nullable final String macAddress,
                                @Nullable final String token,
                                @Nullable final String valuePattern) throws JsonProcessingException {
    //Null check
    Objects.requireNonNull(objectMapper);
    Objects.requireNonNull(mqttClient);
    Objects.requireNonNull(topic);
    Objects.requireNonNull(macAddress);
    Objects.requireNonNull(token);
    Objects.requireNonNull(valuePattern);

    this.objectMapper = objectMapper;
    this.mqttClient = mqttClient;
    this.topic = topic;
    this.macAddress = macAddress;
    this.token = token;

    this.values = this.objectMapper.readValue(valuePattern, PatternMap.class);
  }

  @Override
  public void run() {
    final Map<String, Object> objectMap = new HashMap<>();

    final Random random = ThreadLocalRandom.current();
    this.values.forEach((string, o) -> {
      Object setValue = o;
      if (o instanceof String value) {
        if (value.equals("random-int")) {
          setValue = random.nextInt();
        }
        if (value.equals("random-double")) {
          setValue = random.nextDouble();
        }
      }

      objectMap.put(string, setValue);
    });

    final IMessagePayload messagePayload = new ImmutableMessagePayload(this.token, this.macAddress, objectMap);

    try {
      this.mqttClient.publish(this.topic, objectMapper.writeValueAsBytes(messagePayload), 1, true);
      log.info("Published message to topic of mqtt.");
    } catch (final MqttException | JsonProcessingException exception) {
      throw new RuntimeException(exception);
    }
  }

  private static class PatternMap extends HashMap<String, Object> {

  }
}
