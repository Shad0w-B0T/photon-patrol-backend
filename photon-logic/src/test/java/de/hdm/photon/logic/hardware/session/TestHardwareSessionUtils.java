package de.hdm.photon.logic.hardware.session;

import de.hdm.photon.logic.hardware.IHardwareAccess;
import de.hdm.photon.logic.hardware.TestHardwareUtils;
import de.hdm.photon.schema.hardware.IHardware;
import de.hdm.photon.schema.hardware.IHardwareSession;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Assertions;
import test.de.hdm.photon.TestNameSpace;

import java.util.concurrent.atomic.AtomicReference;


public final class TestHardwareSessionUtils {

  public static <
      HARDWARE_SESSION extends IHardwareSession,
      HARDWARE extends IHardware
      >
  HARDWARE_SESSION createHardwareSession(@NotNull final IHardwareAccess<HARDWARE> hardwareAccess,
                                         @NotNull final IHardwareSessionAccess<HARDWARE_SESSION> hardwareSessionAccess) {
    final AtomicReference<HARDWARE_SESSION> reference = new AtomicReference<>();

    Assertions.assertDoesNotThrow(() -> {

      final HARDWARE hardware = TestHardwareUtils.createHardware(hardwareAccess);
      Assertions.assertNotNull(hardware);

      final HARDWARE_SESSION hardwareSession =
          hardwareSessionAccess.createHardwareSession(TestNameSpace.LOGGING_IP_ADDRESS, hardware.id());
      Assertions.assertNotNull(hardwareSession);
      reference.set(hardwareSession);
    });

    return reference.get();
  }
}
