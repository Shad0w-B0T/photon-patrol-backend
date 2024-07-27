package test.de.hdm.photon.database.postgresql.Hardware;

import de.hdm.photon.database.postgresql.hardware.HardwareEntity;
import de.hdm.photon.database.postgresql.hardware.HardwareRepository;
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
public class TestHardware {

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
    hardwareRepository.deleteAll();

    this.now = new Date();
    this.macAddress = "192.168.0.1";
    this.token = "a94a8fe5ccb19ba61c4c0873d391e987982fbbd3";
    this.hardwareAdded = now;
    this.macAdded = now;
    this.tokenIsActive = true;
  }

  //TODO
  @Test
  @DisplayName("Test if DB is empty.")
  public void testDBEmpty() {

    //Start -> empty DB
    Assertions.assertEquals(this.hardwareRepository.count(), 0);

    final HardwareEntity hardwareEntity
        = this.hardwareRepository.save(
            new HardwareEntity(
                token,
                macAddress,
                hardwareAdded,
                macAdded,
                tokenIsActive)
    );

    //positive
    Assertions.assertEquals(1, this.hardwareRepository.findAll().size());


    //negative
    Assertions.assertNotNull(hardwareEntity);
    Assertions.assertNotEquals(2, this.hardwareRepository.findAll().size());
  }


  @Test
  @DisplayName("Test integrity of entity.")
  public void testIntegrityOfHardwareEntity() {

    final HardwareEntity hardwareEntity
        = this.hardwareRepository.save(
        new HardwareEntity(
            token,
            macAddress,
            hardwareAdded,
            macAdded,
            tokenIsActive)
    );

    //positive
    Assertions.assertEquals("a94a8fe5ccb19ba61c4c0873d391e987982fbbd3", hardwareEntity.token());
    Assertions.assertEquals("192.168.0.1", hardwareEntity.macAddress());
    Assertions.assertEquals(this.now, hardwareEntity.hardwareAdded());
    Assertions.assertEquals(this.now, hardwareEntity.macAdded());
    Assertions.assertTrue(hardwareEntity.tokenIsActive());

    //negative
    Assertions.assertNotEquals("acf6b7cbeebae8db9ced20bffaf9d52f3ad4cc7c", hardwareEntity.token());
    Assertions.assertNotEquals("69.42.133.7", hardwareEntity.macAddress());
    Assertions.assertNotEquals(new Date(), hardwareEntity.hardwareAdded());
    Assertions.assertNotEquals(new Date(), hardwareEntity.macAdded());
    Assertions.assertNotEquals(false, hardwareEntity.tokenIsActive());
  }

  @Test
  @DisplayName("Test MAC address.")
  public void testFindByMacAddress() {

    final HardwareEntity hardwareEntity =
        this.hardwareRepository.save(
            new HardwareEntity(
                token,
                macAddress,
                hardwareAdded,
                macAdded,
                tokenIsActive)
        );

    //positive
    Assertions.assertNotNull(this.hardwareRepository.findByMacAddress(hardwareEntity.macAddress()));
    Assertions.assertTrue(this.hardwareRepository.existsByMacAddress(hardwareEntity.macAddress()));
    Assertions.assertEquals(1, this.hardwareRepository.findByMacAddress(macAddress).stream().count());
    Assertions.assertEquals("192.168.0.1", hardwareEntity.macAddress());

    //negative
    Assertions.assertNotEquals("69.42.133.7", hardwareEntity.macAddress());
    Assertions.assertNotEquals(0, this.hardwareRepository.findByMacAddress(macAddress).stream().count());

  }

  //TODO
  @Test
  @DisplayName("Test find by token.")
  public void testFindByToken() {
    hardwareRepository.findByToken(token);

    //positive


    //negative


  }

  //TODO
  @Test
  @DisplayName("Test find when hardware was added.")
  public void testFindByHardwareAdded() {
    hardwareRepository.findByHardwareAdded(hardwareAdded);

    //positive


    //negative


  }

  //TODO
  @Test
  @DisplayName("Test find.")
  public void testFindByMacAdded() {
    hardwareRepository.findByMacAdded(macAdded);

    //positive


    //negative


  }

  //TODO
  @Test
  @DisplayName("Test")
  public void testFindAll() {

    //positive


    //negative


  }


}
