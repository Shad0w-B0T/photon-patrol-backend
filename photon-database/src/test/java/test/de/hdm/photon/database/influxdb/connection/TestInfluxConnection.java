package test.de.hdm.photon.database.influxdb.connection;

import de.hdm.photon.database.influxdb.connection.IInfluxConnection;
import de.hdm.photon.schema.exception.EntityNotPresentException;
import jakarta.validation.constraints.Null;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import test.de.hdm.photon.AbstractSpringTest;
import test.de.hdm.photon.TestField;


@DataJpaTest // Only scans for @Entity and @Repository and store it to the context
@DirtiesContext(classMode=ClassMode.BEFORE_EACH_TEST_METHOD) //Build new context before each test
@AutoConfigureTestDatabase(replace=Replace.NONE)
public final class TestInfluxConnection extends AbstractSpringTest {

  @Autowired
  private IInfluxConnection influxConnection;

  @BeforeEach
  public void before() {
    this.influxConnection.getMeasurements().forEach(this.influxConnection::deleteMeasurements);
    Assertions.assertTrue(this.influxConnection.getMeasurements().isEmpty());
  }

  private void getAndSetTest() {
    this.influxConnection.addMeasurements(TestField.HARDWARE_TOKEN, TestField.HARDWARE_FIELD);
    Assertions.assertFalse(this.influxConnection.getMeasurements().isEmpty());
  }

  @Test
  @DisplayName("Test get Measurements")
  public void testGetMeasurements() {
    this.getAndSetTest();
  }

  @Test
  @DisplayName("Test get Measurements after")
  public void testGetMeasurementsAfter() {
    final long timestamp = System.currentTimeMillis()-1000;

    this.influxConnection.addMeasurements(TestField.HARDWARE_TOKEN, TestField.HARDWARE_FIELD);

    Assertions.assertFalse(this.influxConnection.getMeasurements().isEmpty());
    Assertions.assertTrue(this.influxConnection.getMeasurementsAfter(System.currentTimeMillis()+1000).isEmpty());
    Assertions.assertFalse(this.influxConnection.getMeasurementsAfter(timestamp).isEmpty());
  }


  @Test
  @DisplayName("Test get measurements name list ")
  public void testGetMeasurementsNameList() {
    final long timestamp = System.currentTimeMillis()-1000;

    this.influxConnection.addMeasurements(TestField.HARDWARE_TOKEN, TestField.HARDWARE_FIELD);

    Assertions.assertFalse(this.influxConnection.getMeasurements().isEmpty());
    Assertions.assertThrows(EntityNotPresentException.class, () -> {
      this.influxConnection.getMeasurementsNameList(TestField.HARDWARE_TOKEN,
          System.currentTimeMillis()+1000);
    });
    Assertions.assertDoesNotThrow(() -> {
      this.influxConnection.getMeasurementsNameList(TestField.HARDWARE_TOKEN, timestamp);
    });
  }

  @Test
  @DisplayName("Test get measurements of key")
  public void testGetMeasurementsOfKey() {
    final long timestamp = System.currentTimeMillis()-1000;

    this.influxConnection.addMeasurements(TestField.HARDWARE_TOKEN, TestField.HARDWARE_FIELD);

    Assertions.assertFalse(this.influxConnection.getMeasurements().isEmpty());
    Assertions.assertThrows(EntityNotPresentException.class, () -> {
      this.influxConnection.getMeasurementsOfKey(TestField.HARDWARE_TOKEN,
          TestField.MEASUREMENT_FIELD,
          1,
          System.currentTimeMillis()+1000);
    });
    Assertions.assertDoesNotThrow(() -> {
      this.influxConnection.getMeasurementsOfKey(TestField.HARDWARE_TOKEN,
          TestField.MEASUREMENT_FIELD,
          1,
          timestamp);
    });
  }

  @Test
  @DisplayName("Test delete measurements")
  public void testDeleteMeasurements() {
    //Negative
    Assertions.assertThrows(NullPointerException.class, () -> {
      this.influxConnection.deleteMeasurements(null);
    });

    //positive
    this.getAndSetTest();

    this.influxConnection.deleteMeasurements(TestField.HARDWARE_TOKEN);
    Assertions.assertTrue(this.influxConnection.getMeasurements().isEmpty());
  }

  @Test
  @DisplayName("Test delete measurements")
  public void testDeleteMeasurementsKey() {
    //Negative
    Assertions.assertThrows(NullPointerException.class, () -> {
      this.influxConnection.deleteMeasurements(null,null);
    });

    this.getAndSetTest();
    //Positive
    this.influxConnection.deleteMeasurements(TestField.HARDWARE_TOKEN, TestField.MEASUREMENT_FIELD);
    //Still one
    Assertions.assertFalse(this.influxConnection.getMeasurements().isEmpty());
  }

  @Test
  @DisplayName("Test addMeasurements")
  public void testAddMeasurements() {
    //negative
    Assertions.assertThrows(NullPointerException.class, () -> {
      this.influxConnection.addMeasurements(null, null);
    });

    //positive
    this.getAndSetTest();
  }
}
