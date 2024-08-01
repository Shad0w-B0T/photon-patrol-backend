package test.de.hdm.photon.logic.hardware;

import de.hdm.photon.database.postgresql.hardware.HardwareRepository;
import de.hdm.photon.logic.hardware.IHardwareAccess;
import de.hdm.photon.schema.exception.EntityAlreadyPresentException;
import de.hdm.photon.schema.exception.EntityNotPresentException;
import de.hdm.photon.schema.exception.FormatMismatchException;
import de.hdm.photon.schema.hardware.IHardware;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import test.de.hdm.photon.AbstractSpringTest;
import test.de.hdm.photon.TestField;


/**
 * Abstract test class for {@link IHardware} implementations.
 *
 * @param <HARDWARE> the type of hardware to be tested, extending {@link IHardware}
 */
@Disabled
public abstract class TestAbstractHardware<HARDWARE extends IHardware> extends AbstractSpringTest {

  @Autowired
  private HardwareRepository hardwareRepository;

  /**
   * Provides the {@link IHardwareAccess} implementation to be tested.
   *
   * @return the hardware access instance
   */
  protected abstract @NotNull IHardwareAccess<HARDWARE> hardwareAccess();

  /**
   * Creates a hardware instance for testing.
   *
   * @return the created hardware instance
   */
  private @NotNull HARDWARE createHardware() {
    return TestHardwareUtils.createHardware(this.hardwareAccess());
  }

  /**
   * Cleans up the hardware repository before each test.
   */
  @BeforeEach
  public void cleanUp() {
    this.hardwareRepository.deleteAll();
  }

  @Test
  @DisplayName("Test create hardware.")
  public void testCreateHardware() throws EntityNotPresentException {
    final HARDWARE hardware = this.createHardware();
    //positive
    Assertions.assertEquals(hardware.id(), this.hardwareAccess().getHardware(hardware.id()).id());
  }

  @Test
  @DisplayName("Test retrieve hardware.")
  public void testRetrieveHardware() {
    final HARDWARE hardware = this.createHardware();

    //positive
    Assertions.assertDoesNotThrow(() -> this.hardwareAccess().getHardware(hardware.id()));

    //negative
    Assertions.assertThrowsExactly(NullPointerException.class,
        () -> this.hardwareAccess().getHardware(null));
    Assertions.assertThrowsExactly(EntityNotPresentException.class,
        () -> this.hardwareAccess().getHardware(TestField.HARDWARE_ID_NOT));
  }


  @Test
  @DisplayName("Test update hardware.")
  public void testUpdateHardware() throws FormatMismatchException, EntityNotPresentException, EntityAlreadyPresentException {
    final HARDWARE hardware = this.createHardware();

    //positive
    this.hardwareAccess().updateHardware(
        hardware.id(),
        TestField.HARDWARE_MAC_UPDATED,
        TestField.HARDWARE_ADDED_UPDATED,
        TestField.HARDWARE_MAC_ADDED_UPDATED,
        TestField.HARDWARE_TOKEN_ACTIVE);

    Assertions.assertEquals(hardware.id(), this.hardwareAccess().getHardware(hardware.id()).id());

    Assertions.assertEquals(
        TestField.HARDWARE_MAC_UPDATED,
        this.hardwareAccess().getHardware(hardware.id()).macAddress()
    );
    Assertions.assertEquals(
        TestField.HARDWARE_ADDED_UPDATED,
        this.hardwareAccess().getHardware(hardware.id()).hardwareAdded()
    );
    Assertions.assertEquals(
        TestField.HARDWARE_MAC_ADDED_UPDATED,
        this.hardwareAccess().getHardware(hardware.id()).macAdded()
    );
    Assertions.assertEquals(
        TestField.HARDWARE_TOKEN_ACTIVE,
        this.hardwareAccess().getHardware(hardware.id()).tokenIsActive()
    );

    //negative
    Assertions.assertNotEquals(
        TestField.HARDWARE_ID_NOT,
        this.hardwareAccess().getHardware(hardware.id()).id()
    );
    Assertions.assertNotEquals(
        TestField.HARDWARE_MAC_NOT,
        this.hardwareAccess().getHardware(hardware.id()).macAddress()
    );
    Assertions.assertNotEquals(
        TestField.HARDWARE_ADDED_EXPIRED,
        this.hardwareAccess().getHardware(hardware.id()).hardwareAdded()
    );
    Assertions.assertNotEquals(
        TestField.HARDWARE_MAC_ADDED_EXPIRED,
        this.hardwareAccess().getHardware(hardware.id()).macAdded()
    );
    Assertions.assertNotEquals(
        TestField.HARDWARE_TOKEN_ACTIVE_NOT,
        this.hardwareAccess().getHardware(hardware.id()).tokenIsActive()
    );
  }


  @Test
  @DisplayName("Test delete hardware.")
  public void testDeleteHardware() throws EntityNotPresentException {
    final HARDWARE hardware = this.createHardware();

    //positive
    Assertions.assertTrue(this.hardwareAccess().deleteHardware(hardware.id()));
    Assertions.assertTrue(this.hardwareAccess().getHardwareList().isEmpty());

    //negative
    Assertions.assertThrows(EntityNotPresentException.class,
        () -> this.hardwareAccess().getHardware(hardware.id())
    );
    Assertions.assertTrue(this.hardwareAccess().getHardwareList().isEmpty());
  }
}
