package test.de.hdm.photon.database.influxdb.annotations;

import de.hdm.photon.database.influxdb.annoation.InfluxValue;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;


public final class InfluxValueTest {

  /**
   * Tests the positive scenarios of {@code PasswordRequirementFactory}.
   */
  @Test
  @DisplayName("Test InfluxTime Annotation")
  public void testPasswordRequirementFactoryPositive() {
    //Tests

    final Class<?> clazz = TestClass.class;

    Assertions.assertDoesNotThrow(() -> {
      final Field field = clazz.getDeclaredField("testField");
      final InfluxValue value = field.getAnnotation(InfluxValue.class);
      Assertions.assertEquals(value.name(), "test");
    });

  }

  private final class TestClass {

    @InfluxValue(name="test")
    private String testField;

  }

}


