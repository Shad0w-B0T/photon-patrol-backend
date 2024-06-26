package de.hdm.photon.logic.security.io;

import org.jetbrains.annotations.NotNull;

import java.util.regex.Pattern;


public final class SecurityFilters {

  private final static @NotNull Pattern MAC_PATTERN = Pattern.compile("^([0-9A-Fa-f]{2}[:-]){5}([0-9A-Fa-f]{2})$");
  private final static @NotNull Pattern IPV4_PATTERN = Pattern.compile("^(25[0-5]|2[0-4][0-9]|1[0-9]{2}|[1-9]?[0-9])\\."+
      "(25[0-5]|2[0-4][0-9]|1[0-9]{2}|[1-9]?[0-9])\\."+
      "(25[0-5]|2[0-4][0-9]|1[0-9]{2}|[1-9]?[0-9])\\."+
      "(25[0-5]|2[0-4][0-9]|1[0-9]{2}|[1-9]?[0-9])$");
  private final static @NotNull Pattern IPV6_PATTERN = Pattern.compile("^((([0-9A-Fa-f]{1,4}:){7}([0-9A-Fa-f]{1,4}|:)"+
      ")|(([0-9A-Fa-f]{1,4}:){1,7}:)|(([0-9A-Fa-f]{1,4}:){1,6}(:[0-9A-Fa-f]{1,4}){1,2})|(([0-9A-Fa-f]{1,4}:){1,5}"+
      "(:[0-9A-Fa-f]{1,4}){1,3})|(([0-9A-Fa-f]{1,4}:){1,4}(:[0-9A-Fa-f]{1,4}){1,4})|(([0-9A-Fa-f]{1,4}:){1,3}(:[0-9A-Fa-f]{1,4}){1,5})|(([0-9A-Fa-f]{1,4}:){1,2}(:[0-9A-Fa-f]{1,4}){1,6})|([0-9A-Fa-f]{1,4}:)((:[0-9A-Fa-f]{1,4}){1,7}|:)|(:((:[0-9A-Fa-f]{1,4}){1,7}|:)))(%.+)?$");

  public static @NotNull ISecurityFilter macStringFilter() {
    return new SecurityFilter()
        .rule(String.class, s -> MAC_PATTERN.matcher(s).find());
  }

  public static @NotNull ISecurityFilter ipVersion4StringFilter() {
    return new SecurityFilter()
        .rule(String.class, s -> IPV4_PATTERN.matcher(s).find());
  }

  public static @NotNull ISecurityFilter ipVersion6StringFilter() {
    return new SecurityFilter()
        .rule(String.class, s -> IPV6_PATTERN.matcher(s).find());
  }
}
