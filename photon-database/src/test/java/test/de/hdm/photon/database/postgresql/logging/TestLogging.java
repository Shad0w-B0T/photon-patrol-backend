package test.de.hdm.photon.database.postgresql.logging;

import de.hdm.photon.database.postgresql.hardware.HardwareEntity;
import de.hdm.photon.database.postgresql.hardware.HardwareRepository;
import de.hdm.photon.database.postgresql.logging.LoggingRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;

import java.util.Date;


@DataJpaTest // Only scans for @Entity and @Repository and store it to the context
@DirtiesContext(classMode=ClassMode.BEFORE_EACH_TEST_METHOD) //Build new context before each test
public class TestLogging {

  @Autowired
  private LoggingRepository loggingRepository;

  private Date now;
  private String ipAddress;
  private Date timestampOfIp;


  @Autowired
  private HardwareRepository hardwareRepository;

  private String macAddress;
  private String token;
  private Date hardwareAdded;
  private Date macAdded;
  private boolean tokenIsActive;


  @BeforeEach
  public void setUp() {
    this.loggingRepository.deleteAll();
    this.hardwareRepository.deleteAll();

    this.now = new Date();

    // hardware
    this.macAddress = "A1:B4:C5:C1:DD:3E";
    this.token = "a94a8fe5ccb19ba61c4c0873d391e987982fbbd3";
    this.hardwareAdded = this.now;
    this.macAdded = this.now;
    this.tokenIsActive = true;

    // logging
    this.ipAddress = "192.168.0.1";
    this.timestampOfIp = this.now;
  }

  @Test
  @DisplayName("Test if DB is empty.")
  public void testDBEmpty() {

    //Start -> empty DB
    Assertions.assertEquals(this.loggingRepository.count(), 0);

    final HardwareEntity hardwareEntity
        = this.hardwareRepository.save(
        new HardwareEntity(
            token,
            macAddress,
            hardwareAdded,
            macAdded,
            tokenIsActive)
    );

 /*    final LoggingEntity loggingEntity =
        this.loggingRepository.save(
            new LoggingEntity(
                ipAddress,
                timestampOfIp
            )
        );

    //positive
    Assertions.assertEquals(1, this.loggingRepository.findAll().size());
    Assertions.assertNotNull(loggingEntity);

    //negative
    Assertions.assertNotEquals(2, this.loggingRepository.findAll().size()); */
  }


  @Test
  @DisplayName("Test integrity of logging entity.")
  public void testIntegrityOfLoggingEntity() {

  }


  @Test
  @DisplayName("Test ip address.")
  public void testIpAddress() {

  }


  @Test
  @DisplayName("Test timestamp of ip.")
  public void testTimestampOfIp() {

  }
}
