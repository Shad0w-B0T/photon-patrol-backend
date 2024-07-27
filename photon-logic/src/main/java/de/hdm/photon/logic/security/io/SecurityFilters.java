package de.hdm.photon.logic.security.io;

import org.jetbrains.annotations.NotNull;

import java.util.regex.Pattern;


/**
 * Provides static methods to create security filters for validating MAC addresses, IPv4 addresses, and IPv6 addresses.
 * The filters use regular expressions to match the respective patterns.
 *
 * <p>This class is designed to be used as a utility class and cannot be instantiated.</p>
 */
public final class SecurityFilters {
  /**
   * Pattern for validating MAC addresses.
   */
  private final static @NotNull Pattern MAC_PATTERN = Pattern.compile("^([0-9A-Fa-f]{2}[:-]){5}([0-9A-Fa-f]{2})$");

  /**
   * Pattern for validating IPv4 addresses.
   */
  private final static @NotNull Pattern IPV4_PATTERN = Pattern.compile("^(25[0-5]|2[0-4][0-9]|1[0-9]{2}|[1-9]?[0-9])\\."+
      "(25[0-5]|2[0-4][0-9]|1[0-9]{2}|[1-9]?[0-9])\\."+
      "(25[0-5]|2[0-4][0-9]|1[0-9]{2}|[1-9]?[0-9])\\."+
      "(25[0-5]|2[0-4][0-9]|1[0-9]{2}|[1-9]?[0-9])$");

  /**
   * Pattern for validating IPv6 addresses.
   */
  private final static @NotNull Pattern IPV6_PATTERN = Pattern.compile("^((([0-9A-Fa-f]{1,4}:){7}([0-9A-Fa-f]{1,4}|:)"+
      ")|(([0-9A-Fa-f]{1,4}:){1,7}:)|(([0-9A-Fa-f]{1,4}:){1,6}(:[0-9A-Fa-f]{1,4}){1,2})|(([0-9A-Fa-f]{1,4}:){1,5}"+
      "(:[0-9A-Fa-f]{1,4}){1,3})|(([0-9A-Fa-f]{1,4}:){1,4}(:[0-9A-Fa-f]{1,4}){1,4})|(([0-9A-Fa-f]{1,4}:){1,3}(:[0-9A-Fa-f]{1,4}){1,5})|(([0-9A-Fa-f]{1,4}:){1,2}(:[0-9A-Fa-f]{1,4}){1,6})|([0-9A-Fa-f]{1,4}:)((:[0-9A-Fa-f]{1,4}){1,7}|:)|(:((:[0-9A-Fa-f]{1,4}){1,7}|:)))(%.+)?$");

  /**
   * Creates a security filter for validating MAC address strings.
   *
   * @return an {@link ISecurityFilter} that validates MAC address strings
   */
  public static @NotNull ISecurityFilter macStringFilter() {
    return new SecurityFilter()
        .rule(String.class, s -> MAC_PATTERN.matcher(s).find());
  }

  /**
   * Creates a security filter for validating IPv4 address strings.
   *
   * @return an {@link ISecurityFilter} that validates IPv4 address strings
   */
  public static @NotNull ISecurityFilter ipVersion4StringFilter() {
    return new SecurityFilter()
        .rule(String.class, s -> IPV4_PATTERN.matcher(s).find());
  }

  /**
   * Creates a security filter for validating IPv6 address strings.
   *
   * @return an {@link ISecurityFilter} that validates IPv6 address strings
   */
  public static @NotNull ISecurityFilter ipVersion6StringFilter() {
    return new SecurityFilter()
        .rule(String.class, s -> IPV6_PATTERN.matcher(s).find());
  }
}
