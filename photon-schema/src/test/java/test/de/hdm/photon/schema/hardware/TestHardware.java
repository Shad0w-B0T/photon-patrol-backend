package test.de.hdm.photon.schema.hardware;

import de.hdm.photon.schema.hardware.HardwareFactory;
import de.hdm.photon.schema.hardware.ImmutableHardware;
import de.hdm.photon.schema.hardware.IHardware;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import test.de.hdm.photon.TestField;

import java.util.Date;


/**
 * Unit tests for the {@link IHardware} and {@link HardwareFactory} classes.
 * <p>
 * This class contains various test methods to verify the functionality and behavior of hardware-related
 * operations, including creation and conversion to records. It ensures that hardware creation handles
 * null inputs properly and that the integrity of hardware records is maintained.
 */
public final class TestHardware {

  /**
   * Tests the creation and integrity of hardware.
   * <p>
   * This test creates an instance of hardware using the {@link #createHardware()} method and then
   * verifies its integrity using {@link #checkHardwareIntegrity(IHardware)}.
   */
  @Test
  @DisplayName("Testing Hardware")
  public void testHardware() {
    final IHardware hardware = this.createHardware();
    this.checkHardwareIntegrity(hardware);
  }

  /**
   * Tests the negative scenarios for the {@link HardwareFactory#createHardware(long, String, String, Date, Date, boolean)}
   * method by passing null values where appropriate.
   * <p>
   * This test ensures that {@link HardwareFactory#createHardware(long, String, String, Date, Date, boolean)}
   * properly throws {@link NullPointerException} when any required argument is null.
   */
  @Test
  @DisplayName("Testing HardwareFactory Negative")
  public void testHardwareFactoryNegative() {
    Assertions.assertThrows(NullPointerException.class, () -> {
      HardwareFactory.createHardware(0, null, null, null, null, true);
    });

    Assertions.assertThrows(NullPointerException.class, () -> {
      HardwareFactory.createHardware(0, TestField.HARDWARE_TOKEN, null, null, null, true);
    });

    Assertions.assertThrows(NullPointerException.class, () -> {
      HardwareFactory.createHardware(0, TestField.HARDWARE_TOKEN, TestField.HARDWARE_MAC, null, null, true);
    });

    Assertions.assertThrows(NullPointerException.class, () -> {
      HardwareFactory.convertToRecord(null);
    });
  }

  /**
   * Tests the conversion of a hardware instance to a record.
   * <p>
   * This test creates a hardware instance using the {@link #createHardware()} method and verifies its
   * integrity. It then converts the hardware instance to a record using {@link HardwareFactory#convertToRecord(IHardware)}
   * and checks the integrity of the resulting record.
   */
  @Test
  @DisplayName("Testing HardwareFactory Record")
  public void testHardwareFactoryToRecord() {
    // Create hardware instance
    final IHardware hardware = this.createHardware();
    this.checkHardwareIntegrity(hardware);

    // Convert to record and check integrity
    final IHardware recordHardware = HardwareFactory.convertToRecord(hardware);
    this.checkHardwareIntegrity(recordHardware);
  }

  /**
   * Creates a hardware instance with predefined test data.
   * <p>
   * This method returns an instance of {@link ImmutableHardware} initialized with test fields.
   *
   * @return An instance of {@link ImmutableHardware} with predefined test data.
   */
  private @NotNull IHardware createHardware() {
    return new ImmutableHardware(TestField.HARDWARE_ID,
        TestField.HARDWARE_TOKEN,
        TestField.HARDWARE_MAC,
        TestField.TIMESTAMP_1,
        TestField.TIMESTAMP_2,
        true);
  }

  /**
   * Verifies the integrity of a hardware instance.
   * <p>
   * This method checks that the hardware instance is not null and verifies that all its fields match
   * the expected values. It ensures that the hardware instance is correctly initialized and consistent.
   *
   * @param hardware The hardware instance to check. Should not be null.
   */
  private void checkHardwareIntegrity(@Nullable final IHardware hardware) {
    Assertions.assertNotNull(hardware, "Hardware should not be null");
    Assertions.assertEquals(hardware.id(), TestField.HARDWARE_ID, "Hardware ID does not match");
    Assertions.assertEquals(hardware.token(), TestField.HARDWARE_TOKEN, "Hardware token does not match");
    Assertions.assertEquals(hardware.macAddress(), TestField.HARDWARE_MAC, "Hardware MAC address does not match");
    Assertions.assertEquals(hardware.hardwareAdded(), TestField.TIMESTAMP_1, "Hardware added timestamp does not match");
    Assertions.assertEquals(hardware.macAdded(), TestField.TIMESTAMP_2, "MAC added timestamp does not match");
    Assertions.assertTrue(hardware.tokenIsActive(), "Hardware token should be active");
  }
}