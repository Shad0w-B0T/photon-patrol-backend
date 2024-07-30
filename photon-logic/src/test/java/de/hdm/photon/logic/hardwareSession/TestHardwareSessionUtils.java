package de.hdm.photon.logic.hardwareSession;

import de.hdm.photon.schema.hardware.IHardwareSession;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Assertions;
import test.de.hdm.photon.TestNameSpace;


public final class TestHardwareSessionUtils {

  public static <HARDWARE_SESSION extends IHardwareSession> void createHardwareSession(@NotNull final IHardwareSessionAccess<HARDWARE_SESSION> hardwareSessionAccess) {
    Assertions.assertDoesNotThrow(() -> {
      final HARDWARE_SESSION hardwareSession =
          hardwareSessionAccess.createHardwareSession(
              TestNameSpace.LOGGING_IP_ADDRESS,
              TestNameSpace.HARDWARE_ID
          );
      //TODO remove print statements
      System.out.println("*".repeat(100));
      System.out.println("ID: " + hardwareSession.id());
      System.out.println("IP address: " + hardwareSession.ipAddress());
      System.out.println("Timestamp of IP: " + hardwareSession.timestamp());
      System.out.println("*".repeat(100));
      Assertions.assertNotNull(hardwareSession);
    });
  }
}
