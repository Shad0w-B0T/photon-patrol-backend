package test.de.hdm.photon.queue.application;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.hdm.photon.logic.hardware.IHardwareManager;
import de.hdm.photon.logic.measurement.IMeasurementManager;
import de.hdm.photon.queue.logic.connection.IRabbitConnection;
import de.hdm.photon.queue.logic.connection.IRabbitPublisher;
import de.hdm.photon.schema.exception.EntityNotPresentException;
import de.hdm.photon.schema.hardware.IHardware;
import de.hdm.photon.schema.payload.IMessagePayload;
import de.hdm.photon.schema.payload.ImmutableMessagePayload;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.annotation.DirtiesContext;
import test.de.hdm.photon.AbstractSpringTest;

import java.util.Map;

@SpringBootApplication(scanBasePackages="de.hdm.photon")
@ComponentScan(basePackages="de.hdm.photon")
@DirtiesContext(classMode=DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest(classes=TestQueueIntegration.class)
public class TestQueueIntegration extends AbstractSpringTest {

  @Autowired
  private IHardwareManager hardwareManager;
  @Autowired
  private IMeasurementManager measurementManager;

  @Autowired
  private IRabbitConnection rabbitConnection;
  @Autowired
  private ObjectMapper objectMapper;

  @Disabled
  @Test
  @Timeout(value = 5)
  @DisplayName("Testing Queue Integration")
  public void testQueueIntegration() {
    //Create
    final IHardware hardware = this.hardwareManager.createHardware();
    Assertions.assertDoesNotThrow(() -> {
      this.hardwareManager.getHardware(hardware.id());
    });

    final IMessagePayload messagePayload = new ImmutableMessagePayload(
        "00:00:00:00:00:00",
        hardware.token(),
        "127.0.0.1",
        12,
        Map.of("v", 5)
    );

    //Publish message
    Assertions.assertDoesNotThrow(() -> {
      final IRabbitPublisher publisher = this.rabbitConnection.publisher();
      Assertions.assertNotNull(publisher);

      publisher.publish(this.objectMapper.writeValueAsBytes(messagePayload));

      while (true) {
        try {
          if (!this.measurementManager.getMeasurementsOfKey(hardware.token(), "v").values().isEmpty()) {
            break;
          }
        } catch (final EntityNotPresentException ignore) {
        }
      }

    });

    Assertions.assertDoesNotThrow(() -> {
      final IHardware presentHardware = this.hardwareManager.getHardware(hardware.id());

      //Published valid
      Assertions.assertNotNull(presentHardware);
      Assertions.assertEquals(presentHardware.macAddress(), "00:00:00:00:00:00");
    });
  }
}
