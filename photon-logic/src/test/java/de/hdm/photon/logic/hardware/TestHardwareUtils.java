package de.hdm.photon.logic.hardware;

import de.hdm.photon.schema.hardware.IHardware;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Assertions;
import test.de.hdm.photon.TestNameSpace;

import java.util.concurrent.atomic.AtomicReference;


public final class TestHardwareUtils {

  public static <HARDWARE extends IHardware> HARDWARE createHardware(@NotNull final IHardwareAccess<HARDWARE> hardwareAccess) {
    final AtomicReference<HARDWARE> reference = new AtomicReference<>(null);

    Assertions.assertDoesNotThrow(() -> {
      final HARDWARE hardware = hardwareAccess.createHardware();
      Assertions.assertNotNull(hardware);

      final HARDWARE updated = hardwareAccess.updateHardware(
          hardware.id(),
          TestNameSpace.HARDWARE_MAC_ADDRESS,
          TestNameSpace.HARDWARE_HW_ADDED_CREATED,
          TestNameSpace.HARDWARE_MAC_ADDED_CREATED,
          TestNameSpace.HARDWARE_TOKEN_ACTIVE
      );

      reference.set(updated);
    });

    return reference.get();
  }
}
