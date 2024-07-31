package test.de.hdm.photon.schema.hardware.session;

import de.hdm.photon.schema.hardware.session.IHardwareSession;
import de.hdm.photon.schema.hardware.session.HardwareSessionFactory;
import de.hdm.photon.schema.hardware.session.ImmutableHardwareSession;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import test.de.hdm.photon.TestField;

public final class TestHardwareSession {

  @Test
  @DisplayName("Testing Logging")
  public void testLogging() {
    final IHardwareSession logging = this.createLogging();
    this.checkLoggingIntegrity(logging);
  }

  @Test
  @DisplayName("Testing LoggingFactory negative")
  public void testHardwareFactoryNegative() {
    Assertions.assertThrows(NullPointerException.class, () -> {
      HardwareSessionFactory.createHardware(0, null, null);
    });
    Assertions.assertThrows(NullPointerException.class, () -> {
      HardwareSessionFactory.createHardware(0, TestField.HARDWARE_IP, null);
    });
    Assertions.assertThrows(NullPointerException.class, () -> {
      HardwareSessionFactory.convertToRecord(null);
    });
  }

  @Test
  @DisplayName("Testing LoggingFactory to-record")
  public void testHardwareFactoryToRecord() {
    // Create hardware instance
    final IHardwareSession logging = this.createLogging();
    this.checkLoggingIntegrity(logging);

    // Convert to record and check integrity
    final IHardwareSession recordLogging = HardwareSessionFactory.convertToRecord(logging);
    this.checkLoggingIntegrity(recordLogging);
  }

  private @NotNull IHardwareSession createLogging() {
    return new ImmutableHardwareSession(TestField.HARDWARE_ID,
        TestField.HARDWARE_IP,
        TestField.TIMESTAMP_1);
  }

  private void checkLoggingIntegrity(@Nullable final IHardwareSession hardware) {
    Assertions.assertNotNull(hardware, "");
    Assertions.assertEquals(hardware.id(), TestField.HARDWARE_ID, "");
    Assertions.assertEquals(hardware.ipAddress(), TestField.HARDWARE_IP, "");
    Assertions.assertEquals(hardware.timestamp(), TestField.TIMESTAMP_1, "");
  }

}
