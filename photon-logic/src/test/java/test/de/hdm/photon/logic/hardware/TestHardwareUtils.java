package test.de.hdm.photon.logic.hardware;

import de.hdm.photon.logic.hardware.IHardwareAccess;
import de.hdm.photon.schema.hardware.IHardware;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Assertions;
import test.de.hdm.photon.TestNameSpace;

import java.util.concurrent.atomic.AtomicReference;


/**
 * Utility class for testing hardware-related operations.
 */
public final class TestHardwareUtils {
  /**
   * Creates and updates hardware using the provided {@link IHardwareAccess}.
   *
   * @param hardwareAccess The {@link IHardwareAccess} instance to use for creating and updating hardware.
   * @param <HARDWARE>     The type of hardware.
   * @return The updated hardware instance.
   */
  public static <HARDWARE extends IHardware> HARDWARE createHardware(@NotNull final IHardwareAccess<HARDWARE> hardwareAccess) {
    final AtomicReference<HARDWARE> reference = new AtomicReference<>(null);

    Assertions.assertDoesNotThrow(() -> {
      // Create hardware
      final HARDWARE hardware = hardwareAccess.createHardware();
      Assertions.assertNotNull(hardware);

      // Update hardware
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
