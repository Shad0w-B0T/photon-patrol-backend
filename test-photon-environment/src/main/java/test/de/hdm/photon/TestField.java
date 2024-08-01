package test.de.hdm.photon;

import org.jetbrains.annotations.NotNull;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * This interface defines a set of constants for testing purposes,
 * including timestamps, hardware details, user details, and time period values.
 */
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

  long USER_ID = 1;
  @NotNull
  String USERNAME = "username";
  @NotNull
  String PASSWORD = "password";
  @NotNull
  String SALT = "1234";

  int TIME_PERIOD_MIN = 5;
  int TIME_PERIOD_MAX = 10;
  int TIME_PERIOD_AVERAGE = 7;

  /**
   * Map representing time period values, where the key is a Long representing
   * a timestamp and the value is another map with String keys and Object values
   * representing various time period details.
   */
  @NotNull
  Map<Long, Map<String, Object>> TIME_PERIOD_VALUES = new HashMap<>();

  @NotNull String MEASUREMENT_FIELD = "a";
  @NotNull Map<String, Object> HARDWARE_FIELD = Map.of(MEASUREMENT_FIELD, 5L, "b", 10L);
}
