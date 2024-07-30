package de.hdm.photon.logic.hardware;

import de.hdm.photon.schema.hardware.IHardware;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Assertions;
import test.de.hdm.photon.TestNameSpace;


public final class TestHardwareUtils {

  public static <HARDWARE extends IHardware> void createHardware(@NotNull final IHardwareAccess<HARDWARE> hardwareAccess) {
    Assertions.assertDoesNotThrow(() -> {
      final HARDWARE hardware = hardwareAccess.createHardware();
      hardwareAccess.updateHardware(
          TestNameSpace.HARDWARE_ID,
          TestNameSpace.HARDWARE_MAC_ADDRESS,
          TestNameSpace.HARDWARE_HW_ADDED_CREATED,
          TestNameSpace.HARDWARE_MAC_ADDED_CREATED,
          TestNameSpace.HARDWARE_TOKEN_ACTIVE
      );
      //TODO remove print statements
      System.out.println("*".repeat(100));
      System.out.println("ID: " + hardware.id());
      System.out.println("MAC address: " + hardware.macAddress());
      System.out.println("HW added: " + hardware.hardwareAdded());
      System.out.println("MAC added: " + hardware.macAdded());
      System.out.println("Token active: " + hardware.tokenIsActive());
      System.out.println("*".repeat(100));
      Assertions.assertNotNull(hardware);
    });
  }

}
