package test.de.hdm.photon.schema.measurement;

import de.hdm.photon.schema.hardware.HardwareFactory;
import de.hdm.photon.schema.hardware.IHardware;
import de.hdm.photon.schema.measurement.ITimePeriod;
import de.hdm.photon.schema.measurement.ImmutableTimePeriod;
import de.hdm.photon.schema.measurement.TimePeriodFactory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import test.de.hdm.photon.TestField;


/**
 * Test class for verifying the functionality of the {@link ITimePeriod} and {@link TimePeriodFactory}.
 */
public final class TestTimePeriod {
  /**
   * Tests the creation and integrity of an {@link ITimePeriod} instance.
   */
  @Test
  @DisplayName("Testing TimePeriod")
  public void testTimePeriod() {
    final ITimePeriod<Integer> timePeriod = this.createTimePeriod();
    this.checkTimePeriodIntegrity(timePeriod);
  }

  /**
   * Tests negative scenarios for the {@link TimePeriodFactory} to ensure proper handling of null values.
   */
  @Test
  @DisplayName("Testing TimePeriodFactory negative")
  public void testTimePeriodFactoryNegative() {
    Assertions.assertThrows(NullPointerException.class, () -> {
      TimePeriodFactory.createTimePeriod(null, null, null, null);
    });
    Assertions.assertThrows(NullPointerException.class, () -> {
      TimePeriodFactory.createTimePeriod(TestField.TIME_PERIOD_MIN, null, null, null);
    });
    Assertions.assertThrows(NullPointerException.class, () -> {
      TimePeriodFactory.createTimePeriod(TestField.TIME_PERIOD_MIN, TestField.TIME_PERIOD_MAX, null, null);
    });
    Assertions.assertThrows(NullPointerException.class, () -> {
      TimePeriodFactory.createTimePeriod(TestField.TIME_PERIOD_MIN, TestField.TIME_PERIOD_MAX, TestField.TIME_PERIOD_AVERAGE, null);
    });
    Assertions.assertThrows(NullPointerException.class, () -> {
      TimePeriodFactory.convertToRecord(null);
    });
  }

  /**
   * Tests the conversion of an {@link ITimePeriod} instance to a record and verifies its integrity.
   */
  @Test
  @DisplayName("Testing TimePeriodFactory to-record")
  public void testTimePeriodFactoryToRecord() {
    // Create time period instance
    final ITimePeriod<Integer> timePeriod = this.createTimePeriod();
    this.checkTimePeriodIntegrity(timePeriod);

    // Convert to record and check integrity
    final ITimePeriod<Integer> recordTimePeriod = TimePeriodFactory.convertToRecord(timePeriod);
    this.checkTimePeriodIntegrity(recordTimePeriod);
  }

  /**
   * Creates a new instance of {@link ITimePeriod} for testing purposes.
   *
   * @return a new instance of {@link ITimePeriod}. Never null.
   */
  private @NotNull ITimePeriod<Integer> createTimePeriod() {
    return new ImmutableTimePeriod<>(TestField.TIME_PERIOD_MIN,
        TestField.TIME_PERIOD_MAX,
        TestField.TIME_PERIOD_AVERAGE,
        TestField.TIME_PERIOD_VALUES);
  }

  /**
   * Verifies the integrity of the given {@link ITimePeriod} instance by checking its values against expected values.
   *
   * @param timePeriod the {@link ITimePeriod} instance to check. Must not be null.
   */
  private void checkTimePeriodIntegrity(@Nullable final ITimePeriod<Integer> timePeriod) {
    Assertions.assertNotNull(timePeriod);
    Assertions.assertEquals(timePeriod.min(), TestField.TIME_PERIOD_MIN);
    Assertions.assertEquals(timePeriod.max(), TestField.TIME_PERIOD_MAX);
    Assertions.assertEquals(timePeriod.average(), TestField.TIME_PERIOD_AVERAGE);
    Assertions.assertEquals(timePeriod.values(), TestField.TIME_PERIOD_VALUES);
  }
}
