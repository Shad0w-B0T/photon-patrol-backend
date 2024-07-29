package test.de.hdm.photon;

import org.jetbrains.annotations.NotNull;

import java.util.Date;


public interface TestNameSpace {

  /*
  * Static values for hardware tests.
  */
  long HARDWARE_ID = 1L;
  long HARDWARE_ID_NOT = 42L;

  @NotNull String HARDWARE_TOKEN = "a94a8fe5ccb19ba61c4c0873d391e987982fbbd3";
  @NotNull String HARDWARE_TOKEN_NOT = "acf6b7cbeebae8db9ced20bffaf9d52f3ad4cc7c";

  @NotNull String HARDWARE_MAC_ADDRESS = "A1:B2:C3:D4:E5:F6";
  @NotNull String HARDWARE_MAC_ADDRESS_UPDATED = "AA:BB:CC:DD:EE:FF";
  @NotNull String HARDWARE_MAC_ADDRESS_NOT = "FF:FF:FF:FF:FF:FF";

  @NotNull Date HARDWARE_HW_ADDED_CREATED = new Date(5000);
  @NotNull Date HARDWARE_HW_ADDED_UPDATED = new Date(15000);
  @NotNull Date HARDWARE_HW_ADDED_EXPIRED = new Date(25000);

  @NotNull Date HARDWARE_MAC_ADDED_CREATED = new Date(5000);
  @NotNull Date HARDWARE_MAC_ADDED_UPDATED = new Date(15000);
  @NotNull Date HARDWARE_MAC_ADDED_EXPIRED = new Date(25000);

  boolean HARDWARE_TOKEN_ACTIVE = true;
  boolean HARDWARE_TOKEN_ACTIVE_NOT = false;


  /*
   * Static values for logging tests.
   */
  @NotNull String LOGGING_IP_ADDRESS = "192.168.0.1";
  @NotNull String LOGGING_IP_ADDRESS_UPDATED = "10.10.10.10";
  @NotNull String LOGGING_IP_ADDRESS_NOT = "133.7.42.69";

  @NotNull Date LOGGING_TIMESTAMP_OF_IP_CREATED = new Date(5000);
  @NotNull Date LOGGING_TIMESTAMP_OF_IP_UPDATED = new Date(15000);
  @NotNull Date LOGGING_TIMESTAMP_OF_IP_EXPIRED = new Date(25000);
}
