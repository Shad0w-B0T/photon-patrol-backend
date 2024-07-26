package test.de.hdm.photon.database.postgresql.Hardware;

import de.hdm.photon.database.postgresql.hardware.HardwareEntity;
import de.hdm.photon.database.postgresql.hardware.HardwareRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;

import java.util.Date;


@DataJpaTest // Only scans for @Entity and @Repository and store it to the context
@DirtiesContext(classMode=ClassMode.BEFORE_EACH_TEST_METHOD) //Build new context before each test
public class TestHardware  {

  @Autowired
  private HardwareRepository hardwareRepository;

  private String macAddress;
  private String token;
  private Date hardwareAdded;
  private Date macAdded;
  private boolean tokenIsActive;

  @BeforeEach
  public void setUp() {
    hardwareRepository.deleteAll();

    macAddress = "192.168.0.1";
    String token = "01234567899876543210";
    hardwareAdded = new Date();
    macAdded = new Date();
    tokenIsActive = true;
  }

  @Test
  @DisplayName("Test if id is 0")
  public void testSaveHardware() {

    final HardwareEntity hardwareEntity = new HardwareEntity(token, macAddress, hardwareAdded, macAdded, tokenIsActive);

    hardwareRepository.save(hardwareEntity);

    int expected = 0;

    Assertions.assertEquals(expected, hardwareEntity.id());
  }


  @Test
  @DisplayName("Test find by MAC address.")
  public void testFindByMacAddress() {
    hardwareRepository.findByMacAddress(macAddress);

    //positive
    Assertions.assertNotNull(macAddress);


    //negative
    //Assertions.assertNull();


  }

  @Test
  @DisplayName("Test find by token.")
  public void testFindByToken() {
    hardwareRepository.findByToken(token);

    //positive


    //negative


  }

  @Test
  @DisplayName("Test find when hardware was added.")
  public void testFindByHardwareAdded() {
    hardwareRepository.findByHardwareAdded(hardwareAdded);

    //positive


    //negative


  }

  @Test
  @DisplayName("Test find.")
  public void testFindByMacAdded() {
    hardwareRepository.findByMacAdded(macAdded);

    //positive


    //negative


  }

  @Test
  @DisplayName("Test")
  public void testFindAll() {

    //positive


    //negative



  }



}
