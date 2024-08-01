package test.de.hdm.photon.database.postgresql.hardware;

import de.hdm.photon.database.postgresql.hardware.HardwareEntity;
import de.hdm.photon.database.postgresql.hardware.HardwareRepository;
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
public final class TestHardware extends AbstractSpringTest {

  @Autowired
  private HardwareRepository hardwareRepository;

  private Date now;
  private String macAddress;
  private String token;
  private Date hardwareAdded;
  private Date macAdded;
  private boolean tokenIsActive;

  @BeforeEach
  public void setUp() {
    this.hardwareRepository.deleteAll();

    this.now = new Date();
    this.macAddress = "A1:B4:C5:C1:DD:3E";
    this.token = "a94a8fe5ccb19ba61c4c0873d391e987982fbbd3";
    this.hardwareAdded = this.now;
    this.macAdded = this.now;
    this.tokenIsActive = true;
  }

  @Test
  @DisplayName("Test if DB is empty.")
  public void testDBEmpty() {
    //Start -> empty DB
    Assertions.assertEquals(this.hardwareRepository.count(), 0);

    final HardwareEntity hardwareEntity
        = this.hardwareRepository.save(
        new HardwareEntity(
            this.token,
            this.macAddress,
            this.hardwareAdded,
            this.macAdded,
            this.tokenIsActive)
    );

    //positive
    Assertions.assertEquals(1, this.hardwareRepository.findAll().size());
    Assertions.assertNotNull(hardwareEntity);

    //negative
    Assertions.assertNotEquals(2, this.hardwareRepository.findAll().size());
  }


  @Test
  @DisplayName("Test integrity of hardware entity.")
  public void testIntegrityOfHardwareEntity() {

    final HardwareEntity hardwareEntity
        = this.hardwareRepository.save(
        new HardwareEntity(
            this.token,
            this.macAddress,
            this.hardwareAdded,
            this.macAdded,
            this.tokenIsActive)
    );

    //positive
    Assertions.assertEquals("a94a8fe5ccb19ba61c4c0873d391e987982fbbd3", hardwareEntity.token());
    Assertions.assertEquals("A1:B4:C5:C1:DD:3E", hardwareEntity.macAddress());
    Assertions.assertEquals(this.now, hardwareEntity.hardwareAdded());
    Assertions.assertEquals(this.now, hardwareEntity.macAdded());
    Assertions.assertTrue(hardwareEntity.tokenIsActive());

    //negative
    Assertions.assertNotEquals("acf6b7cbeebae8db9ced20bffaf9d52f3ad4cc7c", hardwareEntity.token());
    Assertions.assertNotEquals("B1:D1:F5:C3:AA:D3", hardwareEntity.macAddress());
    Assertions.assertNotEquals(new Date(), hardwareEntity.hardwareAdded());
    Assertions.assertNotEquals(new Date(), hardwareEntity.macAdded());
    Assertions.assertNotEquals(false, hardwareEntity.tokenIsActive());
  }


  @Test
  @DisplayName("Test token.")
  public void testToken() {

    final HardwareEntity hardwareEntity =
        this.hardwareRepository.save(
            new HardwareEntity(
                this.token,
                this.macAddress,
                this.hardwareAdded,
                this.macAdded,
                this.tokenIsActive)
        );


    //negative
    // token
    Assertions.assertNotEquals("ksdgjwze322kjjwe2kaadsmwe", hardwareEntity.token());
    Assertions.assertNotEquals(0, this.hardwareRepository.findByToken(
            hardwareEntity.token())
        .stream()
        .count()
    );

    // active token
    Assertions.assertNotEquals(false, hardwareEntity.token());
    Assertions.assertNotEquals(0, this.hardwareRepository.findByTokenIsActive(
        hardwareEntity.tokenIsActive())
    );

    //positive
    // token
    Assertions.assertNotNull(this.hardwareRepository.findByToken(hardwareEntity.token()));
    Assertions.assertTrue(this.hardwareRepository.existsByToken(hardwareEntity.token()));
    Assertions.assertEquals(1, this.hardwareRepository.findByToken(
            hardwareEntity.token())
        .stream()
        .count()
    );
    Assertions.assertEquals("a94a8fe5ccb19ba61c4c0873d391e987982fbbd3", hardwareEntity.token());

    // active token
    Assertions.assertNotNull(this.hardwareRepository.findByTokenIsActive(hardwareEntity.tokenIsActive()));
    Assertions.assertEquals(1, this.hardwareRepository.findByTokenIsActive(
            hardwareEntity.tokenIsActive())
        .stream()
        .count()
    );
    Assertions.assertTrue(hardwareEntity.tokenIsActive());

    this.hardwareRepository.deleteByToken(hardwareEntity.token());
    Assertions.assertEquals(0, this.hardwareRepository.findByToken(
            hardwareEntity.token())
        .stream()
        .count()
    );
  }


  @Test
  @DisplayName("Test MAC address.")
  public void testMacAddress() {

    final HardwareEntity hardwareEntity =
        this.hardwareRepository.save(
            new HardwareEntity(
                this.token,
                this.macAddress,
                this.hardwareAdded,
                this.macAdded,
                this.tokenIsActive)
        );


    //negative
    // mac
    Assertions.assertNotEquals("B1:D1:F5:C3:AA:D3", hardwareEntity.macAddress());
    Assertions.assertNotEquals(0, this.hardwareRepository.findByMacAddress(hardwareEntity.macAddress()).stream().count());

    // mac added
    Assertions.assertNotEquals(new Date(), hardwareEntity.macAdded());
    Assertions.assertNotEquals(0, this.hardwareRepository.findByMacAdded(hardwareEntity.macAdded()).stream().count());


    //positive
    // mac
    Assertions.assertNotNull(this.hardwareRepository.findByMacAddress(hardwareEntity.macAddress()));
    Assertions.assertTrue(this.hardwareRepository.existsByMacAddress(hardwareEntity.macAddress()));
    Assertions.assertEquals(1, this.hardwareRepository.findByMacAddress(
            hardwareEntity.macAddress())
        .stream()
        .count()
    );

    // mac added
    Assertions.assertNotNull(this.hardwareRepository.findByMacAdded(hardwareEntity.macAdded()));
    Assertions.assertTrue(this.hardwareRepository.existsByHardwareAdded(hardwareEntity.macAdded()));
    Assertions.assertEquals(1, this.hardwareRepository.findByMacAdded(
            hardwareEntity.macAdded())
        .stream()
        .count()
    );


    this.hardwareRepository.deleteByMacAddress(hardwareEntity.macAddress());
    Assertions.assertEquals(0, this.hardwareRepository.findByMacAddress(
            hardwareEntity.macAddress())
        .stream()
        .count()
    );
    Assertions.assertEquals(0, this.hardwareRepository.findByHardwareAdded(
            hardwareEntity.macAdded())
        .stream()
        .count()
    );
  }


  @Test
  @DisplayName("Test hardware.")
  public void test() {

    final HardwareEntity hardwareEntity =
        this.hardwareRepository.save(
            new HardwareEntity(
                this.token,
                this.macAddress,
                this.hardwareAdded,
                this.macAdded,
                this.tokenIsActive)
        );

    //negative
    Assertions.assertNotNull(hardwareEntity.hardwareAdded());
    Assertions.assertNotEquals(new Date(), hardwareEntity.hardwareAdded());
    Assertions.assertNotEquals(0, this.hardwareRepository.findByHardwareAdded(
            hardwareEntity.hardwareAdded())
        .stream()
        .count()
    );

    //positive
    Assertions.assertNotNull(this.hardwareRepository.findByHardwareAdded(hardwareEntity.hardwareAdded()));
    Assertions.assertTrue(this.hardwareRepository.existsByHardwareAdded(hardwareEntity.hardwareAdded()));
    Assertions.assertEquals(1, this.hardwareRepository.findByHardwareAdded
            (hardwareEntity.hardwareAdded())
        .stream()
        .count()
    );


    this.hardwareRepository.deleteAllByHardwareAdded(hardwareEntity.hardwareAdded());
    Assertions.assertEquals(0, this.hardwareRepository.findByHardwareAdded(
            hardwareEntity.hardwareAdded())
        .stream()
        .count()
    );

  }
}
