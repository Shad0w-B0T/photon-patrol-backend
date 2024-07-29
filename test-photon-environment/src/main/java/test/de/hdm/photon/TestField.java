package test.de.hdm.photon;

import org.jetbrains.annotations.NotNull;

import java.util.Date;


public interface TestField {

  @NotNull
  Date TIMESTAMP_1 = new Date(0);
  @NotNull
  Date TIMESTAMP_2 = new Date(5);

  long HARDWARE_ID = 1;
  @NotNull
  String HARDWARE_MAC = "A1:B4:C5:C1:DD:3E";
  @NotNull
  String HARDWARE_IP = "127.0.0.1";
  @NotNull
  String HARDWARE_TOKEN = "a94a8fe5ccb19ba61c4c0873d391e987982fbbd3";

}
