package test.de.hdm.photon.pseudo.task;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


public final class PublishTaskTest {

  @Test
  @DisplayName("Testing PublishTask Positive")
  public void testPublishTaskPositive() {
    //Positive Tests
    IMqttClient client = null;
  }

  @Test
  @DisplayName("Testing PublishTask Negative")
  public void testPublishTaskNegative() {
    //Negative Tests
    Assertions.assertThrows(NullPointerException.class,
        () -> new PublishTask(null, null, null, null, null, null, 0, null));
    Assertions.assertThrows(NullPointerException.class,
        () -> new PublishTask(new ObjectMapper(), null, null, null, null, null, 0, null));
    Assertions.assertThrows(NullPointerException.class,
        () -> new PublishTask(new ObjectMapper(), null, "", "", "", "", 0, ""));
  }
}
