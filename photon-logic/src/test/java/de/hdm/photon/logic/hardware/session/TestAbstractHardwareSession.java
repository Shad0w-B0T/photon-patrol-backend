package de.hdm.photon.logic.hardware.session;

import de.hdm.photon.logic.hardware.IHardwareAccess;
import de.hdm.photon.schema.exception.EntityNotPresentException;
import de.hdm.photon.schema.hardware.IHardware;
import de.hdm.photon.schema.hardware.IHardwareSession;
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
public abstract class TestAbstractHardwareSession<
    HARDWARE extends IHardware,
    HARDWARE_SESSION extends IHardwareSession> extends AbstractSpringTest {

  protected abstract @NotNull IHardwareSessionAccess<HARDWARE_SESSION> hardwareSessionAccess();
  protected abstract @NotNull IHardwareAccess<HARDWARE> hardwareAccess();


  @BeforeEach
  void setUp() {
    this.createTestHardwareSession();
  }


  @AfterEach
  public void deleteHardwareSessionAfter() {
    try {
      this.hardwareSessionAccess().deleteHardwareSession(TestNameSpace.HARDWARE_SESSION_ID);
    } catch (Exception ignored) {
      //will be ignored
    }
  }


  /**
   * Create a test hardware session.
   */
  private void createTestHardwareSession() {
    TestHardwareSessionUtils.createHardwareSession(this.hardwareAccess(), this.hardwareSessionAccess());
  }


  @Test
  @DisplayName("Test create hardware session.")
  public void testCreateHardwareSession() throws EntityNotPresentException {

    //positive
    Assertions.assertEquals(
        TestNameSpace.HARDWARE_ID,
        this.hardwareSessionAccess()
            .getHardwareSession(TestNameSpace.HARDWARE_SESSION_ID)
            .id()
    );

    //negative
    Assertions.assertNotEquals(
        TestNameSpace.HARDWARE_SESSION_NOT,
        this.hardwareSessionAccess()
            .getHardwareSession(TestNameSpace.HARDWARE_SESSION_ID)
            .id()
    );
  }


  @Test
  @DisplayName("Test retrieve hardware session.")
  public void testRetrieveHardwareSession() {
/*
    //positive
    Assertions.assertDoesNotThrow(() -> this.hardwareAccess().getHardware(TestNameSpace.HARDWARE_ID));

    //negative
    Assertions.assertThrowsExactly(NullPointerException.class,
        () -> this.hardwareAccess().getHardware(null));
    Assertions.assertThrowsExactly(EntityNotPresentException.class,
        () -> this.hardwareAccess().getHardware(TestNameSpace.HARDWARE_ID_NOT)); */
  }
























}
