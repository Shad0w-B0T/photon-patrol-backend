package test.de.hdm.photon.logic.hardware.session;

import de.hdm.photon.database.postgresql.hardware.HardwareRepository;
import de.hdm.photon.database.postgresql.hardware.session.HardwareSessionRepository;
import de.hdm.photon.logic.hardware.IHardwareAccess;
import de.hdm.photon.logic.hardware.session.IHardwareSessionAccess;
import de.hdm.photon.schema.exception.EntityNotPresentException;
import de.hdm.photon.schema.hardware.IHardware;
import de.hdm.photon.schema.hardware.session.IHardwareSession;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import test.de.hdm.photon.AbstractSpringTest;
import test.de.hdm.photon.TestNameSpace;


@Disabled
public abstract class TestAbstractHardwareSession<
    HARDWARE extends IHardware,
    HARDWARE_SESSION extends IHardwareSession> extends AbstractSpringTest {

  @Autowired
  private HardwareRepository hardwareRepository;
  @Autowired
  private HardwareSessionRepository hardwareSessionRepository;

  protected abstract @NotNull IHardwareSessionAccess<HARDWARE_SESSION> hardwareSessionAccess();

  protected abstract @NotNull IHardwareAccess<HARDWARE> hardwareAccess();


  @BeforeEach
  public void cleanUp() {
    this.hardwareSessionRepository.deleteAll();
    this.hardwareRepository.deleteAll();
  }

  @Test
  @DisplayName("Test create hardware session.")
  public void testCreateHardwareSession() throws EntityNotPresentException {
    final Pair<HARDWARE_SESSION, HARDWARE> sessionPair = TestHardwareSessionUtils
        .createHardwareSession(this.hardwareAccess(), this.hardwareSessionAccess());

    //positive
    Assertions.assertEquals(
        sessionPair.getFirst().id(),
        this.hardwareSessionAccess().getHardwareSession(sessionPair.getFirst().id()).id()
    );
  }


  @Test
  @DisplayName("Test retrieve hardware session.")
  public void testRetrieveHardwareSession() {
    final Pair<HARDWARE_SESSION, HARDWARE> sessionPair = TestHardwareSessionUtils
        .createHardwareSession(this.hardwareAccess(), this.hardwareSessionAccess());

    //positive
    Assertions.assertDoesNotThrow(() -> this.hardwareAccess().getHardware(sessionPair.getSecond().id()));

    //negative
    Assertions.assertThrowsExactly(NullPointerException.class,
        () -> this.hardwareAccess().getHardware(null));
    Assertions.assertThrowsExactly(EntityNotPresentException.class,
        () -> this.hardwareAccess().getHardware(TestNameSpace.HARDWARE_ID_NOT));
  }
}
