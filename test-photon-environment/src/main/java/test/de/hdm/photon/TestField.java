package test.de.hdm.photon;

import org.jetbrains.annotations.NotNull;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * This interface defines a set of constants for testing purposes.
 * <p>
 * It includes constants for timestamps, hardware details, user credentials, and time period values.
 * </p>
 */
public interface TestField {

  /**
   * A timestamp representing the epoch start.
   */
  @NotNull
  Date TIMESTAMP_1 = new Date(0);

  /**
   * A timestamp representing a small increment from the epoch start.
   */
  @NotNull
  Date TIMESTAMP_2 = new Date(5);

  /**
   * The ID of the hardware.
   */
  long HARDWARE_ID = 1;

  /**
   * The MAC address of the hardware.
   */
  @NotNull
  String HARDWARE_MAC = "A1:B4:C5:C1:DD:3E";

  /**
   * The IP address of the hardware.
   */
  @NotNull
  String HARDWARE_IP = "127.0.0.1";

  /**
   * The token associated with the hardware.
   */
  @NotNull
  String HARDWARE_TOKEN = "a94a8fe5ccb19ba61c4c0873d391e987982fbbd3";

  /**
   * The ID of the user.
   */
  long USER_ID = 1;

  /**
   * The username of the user.
   */
  @NotNull
  String USERNAME = "username";

  /**
   * The password of the user.
   */
  @NotNull
  String PASSWORD = "password";

  /**
   * The salt used for password hashing.
   */
  @NotNull
  String SALT = "1234";

  /**
   * Minimum value for the time period.
   */
  int TIME_PERIOD_MIN = 5;

  /**
   * Maximum value for the time period.
   */
  int TIME_PERIOD_MAX = 10;

  /**
   * Average value for the time period.
   */
  int TIME_PERIOD_AVERAGE = 7;

  /**
   * A map representing time period values.
   * <p>
   * The key is a {@code Long} representing a timestamp, and the value is a {@code Map}
   * where the key is a {@code String} representing the time period detail, and the value is an {@code Object}.
   * </p>
   */
  @NotNull
  Map<Long, Map<String, Object>> TIME_PERIOD_VALUES = new HashMap<>();
}
