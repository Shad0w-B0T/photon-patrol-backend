package de.hdm.photon.webserver.logic.check;


import de.hdm.photon.schema.exception.auth.FormatMismatchException;
import lombok.extern.log4j.Log4j2;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Log4j2
@Component
public class AddressVerifier implements IAddressVerifier {

  @Autowired
  private LengthVerifier lengthVerifier;

  @Override
  public boolean validateMacAddress(@NotNull String macAddress) throws FormatMismatchException, NullPointerException {
    log.debug("Validating Mac Address: {}", macAddress);
    return this.lengthVerifier.validateLength(macAddress, "MAC") && (this.validateContentOfMac(macAddress));
  }

  @Override
  public boolean validateIpAddress(@NotNull String ipAddress) throws FormatMismatchException, NullPointerException {
    log.debug("Validating IP Address: {}", ipAddress);
    return this.lengthVerifier.validateLength(ipAddress, "IPv4") && (this.validateContentOfIP(ipAddress));
  }

  @Override
  public boolean validateContentOfIP(@Nullable String ipAddress) throws FormatMismatchException {
    Objects.requireNonNull(ipAddress);

    final Pattern pattern = Pattern.compile(IP_REGEX);
    final Matcher matcher = pattern.matcher(ipAddress);

    if (matcher.matches()) {
      log.debug("Matched IP address: {}", ipAddress);
      return true;
    } else {
      throw new FormatMismatchException("Invalid IP address: "+ipAddress);
    }
  }

  @Override
  public boolean validateContentOfMac(@Nullable String macAddress) throws FormatMismatchException, NullPointerException {
    Objects.requireNonNull(macAddress);
    final Pattern pattern = Pattern.compile(MAC_REGEX);
    final Matcher matcher = pattern.matcher(macAddress);

    if (matcher.matches()) {
      log.debug("Matched IP address: "+macAddress);
      return true;
    } else {
      throw new FormatMismatchException("Invalid MAC address: "+macAddress);
    }
  }
}
