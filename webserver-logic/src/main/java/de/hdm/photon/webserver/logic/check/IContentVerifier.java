package de.hdm.photon.webserver.logic.check;

public interface IContentVerifier {

  /**
   * Character length of the Addresses.
   */
  int IPv4_CHARACTER_LENGTH = 15;
  int MAC_ADDRESS_LENGTH = 17;

  /**
   * Regex pattern.
   */
  String IP_REGEX = "^(25[0-5]|2[0-4][0-9]|1[0-9]{2}|[1-9]?[0-9])\\." +
      "(25[0-5]|2[0-4][0-9]|1[0-9]{2}|[1-9]?[0-9])\\." +
      "(25[0-5]|2[0-4][0-9]|1[0-9]{2}|[1-9]?[0-9])\\." +
      "(25[0-5]|2[0-4][0-9]|1[0-9]{2}|[1-9]?[0-9])$";

  String MAC_REGEX = "^([0-9A-Fa-f]{2}[:-]){5}([0-9A-Fa-f]{2})$";

}
