package de.hdm.photon.logic.hardware;

import de.hdm.photon.schema.exception.EntityAlreadyPresentException;
import de.hdm.photon.schema.exception.EntityNotPresentException;
import de.hdm.photon.schema.exception.FormatMismatchException;
import de.hdm.photon.schema.hardware.IHardware;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.annotation.DirtiesContext.MethodMode;
import test.de.hdm.photon.AbstractSpringTest;
import test.de.hdm.photon.TestNameSpace;


@Disabled
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD, methodMode = MethodMode.AFTER_METHOD)
@AutoConfigureTestDatabase(replace=Replace.NONE)
public abstract class TestAbstractHardware<HARDWARE extends IHardware> extends AbstractSpringTest {

  protected abstract @NotNull IHardwareAccess<HARDWARE> hardwareAccess();


  @BeforeEach
  void setUp() throws EntityNotPresentException, EntityAlreadyPresentException, FormatMismatchException {
    this.createTestHardware();
  }


  @AfterEach
  public void deleteHardwareAfter() {
    try {
      this.hardwareAccess().deleteHardware(TestNameSpace.HARDWARE_ID);
    } catch (Exception ignored) {
      //will be ignored
    }
  }


  /**
   * Create an empty test hardware and update it.
   *
   * @throws FormatMismatchException       {@inheritDoc}
   * @throws EntityNotPresentException     {@inheritDoc}
   * @throws EntityAlreadyPresentException {@inheritDoc}
   */
  private void createTestHardware() throws FormatMismatchException, EntityNotPresentException, EntityAlreadyPresentException {
    TestHardwareUtils.createHardware(this.hardwareAccess());
    this.hardwareAccess().updateHardware(
        TestNameSpace.HARDWARE_ID,
        TestNameSpace.HARDWARE_MAC_ADDRESS,
        TestNameSpace.HARDWARE_HW_ADDED_CREATED,
        TestNameSpace.HARDWARE_MAC_ADDED_CREATED,
        TestNameSpace.HARDWARE_TOKEN_ACTIVE
    );
  }


  @Test
  @DisplayName("Test create hardware.")
  public void testCreateHardware() throws EntityNotPresentException {

    //positive
    Assertions.assertEquals(TestNameSpace.HARDWARE_ID, this.hardwareAccess().getHardware(TestNameSpace.HARDWARE_ID).id());

    //negative
    Assertions.assertNotEquals(TestNameSpace.HARDWARE_ID_NOT, this.hardwareAccess().getHardware(TestNameSpace.HARDWARE_ID).id());
  }


  @Test
  @DisplayName("Test retrieve hardware.")
  public void testRetrieveHardware() {

    //positive
    Assertions.assertDoesNotThrow(() -> this.hardwareAccess().getHardware(TestNameSpace.HARDWARE_ID));

    //negative
    Assertions.assertThrowsExactly(NullPointerException.class,
        () -> this.hardwareAccess().getHardware(null));
    Assertions.assertThrowsExactly(EntityNotPresentException.class,
        () -> this.hardwareAccess().getHardware(TestNameSpace.HARDWARE_ID_NOT));
  }


  @Test
  @DisplayName("Test update hardware.")
  public void testUpdateHardware() throws FormatMismatchException, EntityNotPresentException, EntityAlreadyPresentException {

    //positive
    this.hardwareAccess().updateHardware(
        TestNameSpace.HARDWARE_ID,
        TestNameSpace.HARDWARE_MAC_ADDRESS_UPDATED,
        TestNameSpace.HARDWARE_HW_ADDED_UPDATED,
        TestNameSpace.HARDWARE_MAC_ADDED_UPDATED,
        TestNameSpace.HARDWARE_TOKEN_ACTIVE);

    Assertions.assertEquals(
        TestNameSpace.HARDWARE_ID,
        this.hardwareAccess().getHardware(TestNameSpace.HARDWARE_ID).id()
    );
    //TODO -> Virtuelle Hardware bauen, die MAC Adresse und MAC added übergeben kann
    /* Assertions.assertEquals(
        TestNameSpace.HARDWARE_MAC_ADDRESS_UPDATED,
        this.hardwareAccess().getHardware(TestNameSpace.HARDWARE_ID).macAddress()
    ); */
    Assertions.assertEquals(
        TestNameSpace.HARDWARE_HW_ADDED_UPDATED,
        this.hardwareAccess().getHardware(TestNameSpace.HARDWARE_ID).hardwareAdded()
    );
    //TODO siehe oben
    /* Assertions.assertEquals(
        TestNameSpace.HARDWARE_MAC_ADDED_UPDATED,
        this.hardwareAccess().getHardware(TestNameSpace.HARDWARE_ID).macAdded()
    ); */
    Assertions.assertEquals(
        TestNameSpace.HARDWARE_TOKEN_ACTIVE,
        this.hardwareAccess().getHardware(TestNameSpace.HARDWARE_ID).tokenIsActive()
    );

    //negative
    Assertions.assertNotEquals(
        TestNameSpace.HARDWARE_ID_NOT,
        this.hardwareAccess().getHardware(TestNameSpace.HARDWARE_ID).id()
    );
    Assertions.assertNotEquals(
        TestNameSpace.HARDWARE_MAC_ADDRESS_NOT,
        this.hardwareAccess().getHardware(TestNameSpace.HARDWARE_ID).macAddress()
    );
    Assertions.assertNotEquals(
        TestNameSpace.HARDWARE_HW_ADDED_EXPIRED,
        this.hardwareAccess().getHardware(TestNameSpace.HARDWARE_ID).hardwareAdded()
    );
    Assertions.assertNotEquals(
        TestNameSpace.HARDWARE_MAC_ADDED_EXPIRED,
        this.hardwareAccess().getHardware(TestNameSpace.HARDWARE_ID).macAdded()
    );
    Assertions.assertNotEquals(
        TestNameSpace.HARDWARE_TOKEN_ACTIVE_NOT,
        this.hardwareAccess().getHardware(TestNameSpace.HARDWARE_ID).tokenIsActive()
    );
    System.out.println("---".repeat(50));
  }


  @Test
  @DisplayName("Test delete hardware.")
  public void testDeleteHardware() throws EntityNotPresentException {

    //positive
    Assertions.assertTrue(this.hardwareAccess().deleteHardware(TestNameSpace.HARDWARE_ID));
    Assertions.assertTrue(this.hardwareAccess().getHardwareList().isEmpty());
    Assertions.assertThrows(
        EntityNotPresentException.class,
        () -> this.hardwareAccess().getHardware(TestNameSpace.HARDWARE_ID)
    );

    //negative
    Assertions.assertNotEquals(5, this.hardwareAccess().getHardwareList());
  }
}
