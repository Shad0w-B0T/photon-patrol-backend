package test.de.hdm.photon.queue.application;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.hdm.photon.database.influxdb.connection.IInfluxConnection;
import de.hdm.photon.logic.hardware.IHardwareManager;
import de.hdm.photon.logic.measurement.IMeasurementManager;
import de.hdm.photon.queue.logic.connection.IRabbitConnection;
import de.hdm.photon.queue.logic.connection.IRabbitPublisher;
import de.hdm.photon.schema.exception.EntityNotPresentException;
import de.hdm.photon.schema.hardware.IHardware;
import de.hdm.photon.schema.payload.IMessagePayload;
import de.hdm.photon.schema.payload.ImmutableMessagePayload;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import test.de.hdm.photon.AbstractSpringTest;
import test.de.hdm.photon.TestField;

import java.util.Map;


/**
 * Integration tests for queue-based messaging in the system.
 */
@SpringBootApplication(scanBasePackages="de.hdm.photon")
@SpringBootTest(classes=TestQueueIntegration.class)
@ComponentScan(basePackages="de.hdm.photon")
public class TestQueueIntegration extends AbstractSpringTest {
  @Autowired
  private IHardwareManager hardwareManager;
  @Autowired
  private IMeasurementManager measurementManager;
  @Autowired
  private IRabbitConnection rabbitConnection;
  @Autowired
  private ObjectMapper objectMapper;
  @Autowired
  private IInfluxConnection influxConnection;

  @BeforeEach
  public void cleanUp() {
    this.influxConnection.getMeasurements().forEach(this.influxConnection::deleteMeasurements);
  }

  /**
   * Prepares a new hardware instance and verifies its creation.
   *
   * @return The created hardware instance.
   */
  private @NotNull IHardware prepareHardware() {
    final IHardware hardware = this.hardwareManager.createHardware();
    Assertions.assertDoesNotThrow(() -> {
      this.hardwareManager.getHardware(hardware.id());
    });
    return hardware;
  }

  /**
   * Publishes a message payload to the queue.
   *
   * @param messagePayload The message payload to be published.
   */
  private void publish(@NotNull final IMessagePayload messagePayload) {
    Assertions.assertDoesNotThrow(() -> {
      final IRabbitPublisher publisher = this.rabbitConnection.publisher();
      Assertions.assertNotNull(publisher);

      publisher.publish(this.objectMapper.writeValueAsBytes(messagePayload));
    });
  }

  /**
   * Tests the integration of the queue by creating a hardware instance,
   * publishing a message, and verifying the processing of the message.
   */
  @Test
  @Timeout(value=5)
  @DisplayName("Testing Queue Integration")
  public void testQueueIntegration() {
    final IHardware hardware = this.prepareHardware();

    this.publish(new ImmutableMessagePayload(
        TestField.HARDWARE_MAC,
        hardware.token(),
        "127.0.0.1",
        12,
        Map.of("v", 5)
    ));

    Assertions.assertDoesNotThrow(() -> {
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

      Assertions.assertNotNull(presentHardware);
      Assertions.assertEquals(presentHardware.macAddress(), TestField.HARDWARE_MAC);
    });
  }

  /**
   * Tests the queue integration with an invalid token.
   */
  @Test
  @Timeout(value=5)
  @DisplayName("Testing Queue Integration with wrong token")
  public void testQueueIntegrationWithWrongToken() {
    final IHardware hardware = this.prepareHardware();

    this.publish(new ImmutableMessagePayload(
        TestField.HARDWARE_MAC,
        "",
        "127.0.0.1",
        12,
        Map.of("v", 5)
    ));
  }

  /**
   * Tests the queue integration with an invalid IP address.
   */
  @Test
  @Timeout(value=5)
  @DisplayName("Testing Queue Integration with wrong IP")
  public void testQueueIntegrationWithWrongIP() {
    final IHardware hardware = this.prepareHardware();

    this.publish(new ImmutableMessagePayload(
        TestField.HARDWARE_MAC,
        "",
        "aaa.bbb.ccc.ddd",
        12,
        Map.of("v", 5)
    ));
  }
}

