package de.hdm.photon.logic.hardware.session;

import de.hdm.photon.logic.hardware.IHardwareAccess;
import de.hdm.photon.schema.hardware.IHardware;
import de.hdm.photon.schema.hardware.IHardwareSession;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Assertions;
import test.de.hdm.photon.TestNameSpace;


public final class TestHardwareSessionUtils {

  public static <
      HARDWARE_SESSION extends IHardwareSession,
      HARDWARE extends IHardware
      >
  void createHardwareSession(@NotNull final IHardwareAccess<HARDWARE> hardwareAccess,
                             @NotNull final IHardwareSessionAccess<HARDWARE_SESSION> hardwareSessionAccess) {

    Assertions.assertDoesNotThrow(() -> {

      final HARDWARE hardware = hardwareAccess.createHardware();
      hardwareAccess.updateHardware(
          TestNameSpace.HARDWARE_ID,
          TestNameSpace.HARDWARE_MAC_ADDRESS,
          TestNameSpace.HARDWARE_HW_ADDED_CREATED,
          TestNameSpace.HARDWARE_MAC_ADDED_CREATED,
          TestNameSpace.HARDWARE_TOKEN_ACTIVE
      );
      final HARDWARE_SESSION hardwareSession =
          hardwareSessionAccess.createHardwareSession(
              TestNameSpace.LOGGING_IP_ADDRESS,
              TestNameSpace.HARDWARE_ID
          );

      //TODO remove print statements

      //                HARDWARE
      System.out.println(" - HARDWARE - ".repeat(5));
      System.out.println("*".repeat(100));
      System.out.println("ID: "+hardware.id());
      System.out.println("MAC address: "+hardware.macAddress());
      System.out.println("HW added: "+hardware.hardwareAdded());
      System.out.println("MAC added: "+hardware.macAdded());
      System.out.println("Token active: "+hardware.tokenIsActive());
      System.out.println("*".repeat(100));

      //                HARDWARE SESSION
      System.out.println(" - HARDWARE SESSION - ".repeat(5));
      System.out.println("*".repeat(100));
      System.out.println("ID: "+hardwareSession.id());
      System.out.println("IP address: "+hardwareSession.ipAddress());
      System.out.println("Timestamp of IP: "+hardwareSession.timestamp());
      System.out.println("*".repeat(100));

      Assertions.assertNotNull(hardware);
      Assertions.assertNotNull(hardwareSession);
    });
  }
}
