package test.de.hdm.photon.schema.logging;

import de.hdm.photon.schema.logging.ILogging;
import de.hdm.photon.schema.logging.LoggingFactory;
import de.hdm.photon.schema.logging.LoggingRecord;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import test.de.hdm.photon.TestField;

public final class TestLogging {

  @Test
  @DisplayName("Testing Logging")
  public void testLogging() {
    final ILogging logging = this.createLogging();
    this.checkLoggingIntegrity(logging);
  }

  @Test
  @DisplayName("Testing LoggingFactory negative")
  public void testHardwareFactoryNegative() {
    Assertions.assertThrows(NullPointerException.class, () -> {
      LoggingFactory.createHardware(0, null, null);
    });
    Assertions.assertThrows(NullPointerException.class, () -> {
      LoggingFactory.createHardware(0, TestField.HARDWARE_IP, null);
    });
    Assertions.assertThrows(NullPointerException.class, () -> {
      LoggingFactory.convertToRecord(null);
    });
  }

  @Test
  @DisplayName("Testing LoggingFactory to-record")
  public void testHardwareFactoryToRecord() {
    // Create hardware instance
    final ILogging logging = this.createLogging();
    this.checkLoggingIntegrity(logging);

    // Convert to record and check integrity
    final ILogging recordLogging = LoggingFactory.convertToRecord(logging);
    this.checkLoggingIntegrity(recordLogging);
  }

  private @NotNull ILogging createLogging() {
    return new LoggingRecord(TestField.HARDWARE_ID,
        TestField.HARDWARE_IP,
        TestField.TIMESTAMP_1);
  }

  private void checkLoggingIntegrity(@Nullable final ILogging hardware) {
    Assertions.assertNotNull(hardware, "");
    Assertions.assertEquals(hardware.id(), TestField.HARDWARE_ID, "");
    Assertions.assertEquals(hardware.ipAddress(), TestField.HARDWARE_IP, "");
    Assertions.assertEquals(hardware.timestampOfIp(), TestField.TIMESTAMP_1, "");
  }

}
