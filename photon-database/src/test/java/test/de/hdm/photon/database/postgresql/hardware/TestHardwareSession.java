package test.de.hdm.photon.database.postgresql.hardware;

import de.hdm.photon.database.postgresql.hardware.HardwareEntity;
import de.hdm.photon.database.postgresql.hardware.HardwareRepository;
import de.hdm.photon.database.postgresql.hardware.HardwareSessionEntity;
import de.hdm.photon.database.postgresql.hardware.HardwareSessionRepository;
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

import java.util.Date;


@DataJpaTest // Only scans for @Entity and @Repository and store it to the context
@DirtiesContext(classMode=ClassMode.BEFORE_EACH_TEST_METHOD) //Build new context before each test
@AutoConfigureTestDatabase(replace=Replace.NONE)
public final class TestHardwareSession extends AbstractSpringTest {
  @Autowired
  private HardwareRepository hardwareRepository;
  @Autowired
  private HardwareSessionRepository hardwareSessionRepository;

  private Date now;
  private String ipAddress;
  private Date timestampOfIp;

  private String macAddress;
  private String token;
  private Date hardwareAdded;
  private Date macAdded;
  private boolean tokenIsActive;

  @BeforeEach
  public void setUp() {
    this.hardwareSessionRepository.deleteAll();
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
    Assertions.assertEquals(this.hardwareSessionRepository.count(), 0);

    final HardwareEntity hardwareEntity
        = this.hardwareRepository.save(
        new HardwareEntity(
            token,
            macAddress,
            hardwareAdded,
            macAdded,
            tokenIsActive)
    );

    final HardwareSessionEntity loggingEntity =
        this.hardwareSessionRepository.save(
            new HardwareSessionEntity(
                hardwareEntity,
                this.ipAddress,
                this.timestampOfIp
            )
        );

    //positive
    Assertions.assertEquals(1, this.hardwareSessionRepository.findAll().size());
    Assertions.assertNotNull(loggingEntity);

    //negative
    Assertions.assertNotEquals(2, this.hardwareSessionRepository.findAll().size());
  }


  @Test
  @DisplayName("Test integrity of logging entity.")
  public void testIntegrityOfLoggingEntity() {

    final HardwareEntity hardwareEntity
        = this.hardwareRepository.save(
        new HardwareEntity(
            token,
            macAddress,
            hardwareAdded,
            macAdded,
            tokenIsActive)
    );

    final HardwareSessionEntity loggingEntity =
        this.hardwareSessionRepository.save(
            new HardwareSessionEntity(
                hardwareEntity,
                this.ipAddress,
                this.timestampOfIp
            )
        );

    //positive
    Assertions.assertEquals("192.168.0.1", loggingEntity.ipAddress());
    Assertions.assertEquals(this.now, loggingEntity.timestamp());


    //negative
    Assertions.assertNotEquals("133.7.42.69", loggingEntity.ipAddress());
    Assertions.assertNotEquals(new Date(), loggingEntity.timestamp());
  }


  @Test
  @DisplayName("Test ip address.")
  public void testIpAddress() {

    final HardwareEntity hardwareEntity
        = this.hardwareRepository.save(
        new HardwareEntity(
            token,
            macAddress,
            hardwareAdded,
            macAdded,
            tokenIsActive)
    );

    final HardwareSessionEntity loggingEntity =
        this.hardwareSessionRepository.save(
            new HardwareSessionEntity(
                hardwareEntity,
                this.ipAddress,
                this.timestampOfIp
            )
        );

    //negative
    Assertions.assertNotEquals("133.7.42.69", loggingEntity.ipAddress());
    Assertions.assertNotEquals(0, this.hardwareSessionRepository.findByIpAddress(
            loggingEntity.ipAddress())
        .stream()
        .count()
    );

    //positive
    Assertions.assertNotNull(this.hardwareSessionRepository.findByIpAddress(loggingEntity.ipAddress()));
    Assertions.assertTrue(this.hardwareSessionRepository.existsByIpAddress(loggingEntity.ipAddress()));
    Assertions.assertEquals(1, this.hardwareSessionRepository.findByIpAddress(
            loggingEntity.ipAddress())
        .stream()
        .count()
    );
    Assertions.assertEquals("192.168.0.1", this.hardwareSessionRepository.findByIpAddress(
            loggingEntity.ipAddress())
        .stream()
        .findFirst()
        .get()
        .ipAddress()
    );

    this.hardwareSessionRepository.deleteByIpAddress(loggingEntity.ipAddress());
    Assertions.assertEquals(0, this.hardwareSessionRepository.findByIpAddress(
            loggingEntity.ipAddress())
        .stream()
        .count()
    );

  }


  @Test
  @DisplayName("Test timestamp of ip.")
  public void testTimestampOfIp() {

    final HardwareEntity hardwareEntity
        = this.hardwareRepository.save(
        new HardwareEntity(
            token,
            macAddress,
            hardwareAdded,
            macAdded,
            tokenIsActive)
    );

    final HardwareSessionEntity loggingEntity =
        this.hardwareSessionRepository.save(
            new HardwareSessionEntity(
                hardwareEntity,
                this.ipAddress,
                this.timestampOfIp
            )
        );


    //negative
    Assertions.assertNotEquals(new Date(), loggingEntity.timestamp());
    Assertions.assertNotEquals(0, this.hardwareSessionRepository.findByTimestamp(
            loggingEntity.timestamp())
        .stream()
        .count()
    );

    //positive
    Assertions.assertNotNull(
        this.hardwareSessionRepository.findByTimestamp(
            loggingEntity.timestamp())
    );
    Assertions.assertTrue(
        this.hardwareSessionRepository.existsByTimestamp(
            loggingEntity.timestamp())
    );
    Assertions.assertEquals(1,
        this.hardwareSessionRepository.findByTimestamp(
                loggingEntity.timestamp())
            .stream()
            .count())
    ;
    Assertions.assertEquals(
        this.now,
        loggingEntity.timestamp()
    );

    this.hardwareSessionRepository.deleteByTimestamp(
        loggingEntity.timestamp()
    );
    Assertions.assertEquals(0, this.hardwareSessionRepository.findByTimestamp(
            loggingEntity.timestamp())
        .stream()
        .count()
    );
  }


  @Test
  @DisplayName("Test combination of ip and timestamp.")
  public void testCombinationOfIpAndTimestamp() {

    final HardwareEntity hardwareEntity
        = this.hardwareRepository.save(
        new HardwareEntity(
            token,
            macAddress,
            hardwareAdded,
            macAdded,
            tokenIsActive)
    );

    final HardwareSessionEntity loggingEntity =
        this.hardwareSessionRepository.save(
            new HardwareSessionEntity(
                hardwareEntity,
                this.ipAddress,
                this.timestampOfIp
            )
        );

    //negative
    Assertions.assertNotEquals(new Date(), loggingEntity.timestamp());
    Assertions.assertNotEquals(0,
        this.hardwareSessionRepository.findByTimestamp(
                loggingEntity.timestamp())
            .stream()
            .count()
    );

    //positive
    Assertions.assertNotNull(
        this.hardwareSessionRepository.findByIpAddressAndTimestamp(
            loggingEntity.ipAddress(),
            loggingEntity.timestamp())
    );
    Assertions.assertTrue(
        this.hardwareSessionRepository.existsByHardwareEntityAndIpAddressAndTimestamp(
            loggingEntity.hardwareEntity(),
            loggingEntity.ipAddress(),
            loggingEntity.timestamp())
    );
    Assertions.assertTrue(
        this.hardwareSessionRepository.existsByIpAddressAndTimestamp(
            loggingEntity.ipAddress(),
            loggingEntity.timestamp())
    );
    Assertions.assertEquals(1,
        this.hardwareSessionRepository.findByIpAddressAndTimestamp(
                loggingEntity.ipAddress(),
                loggingEntity.timestamp())
            .stream()
            .count()
    );


    //delete
    this.hardwareSessionRepository.deleteByHardwareEntityAndIpAddressAndTimestamp(
        loggingEntity.hardwareEntity(),
        loggingEntity.ipAddress(),
        loggingEntity.timestamp()
    );
    Assertions.assertEquals(0, this.hardwareSessionRepository.findByIpAddressAndTimestamp(
            loggingEntity.ipAddress(),
            loggingEntity.timestamp())
        .stream()
        .count()
    );
  }
}
