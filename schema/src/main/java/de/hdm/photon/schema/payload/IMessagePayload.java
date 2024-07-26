package de.hdm.photon.schema.payload;

import de.hdm.photon.schema.hardware.IHardwareAddress;
import de.hdm.photon.schema.hardware.IHardwareToken;
import jakarta.validation.constraints.NotNull;

import java.util.Map;

/**
 * Represents a message payload that includes hardware address and token information.
 * This interface extends both {@link IHardwareAddress} and {@link IHardwareToken}.
 * It also provides a method to retrieve a map of values associated with the message payload.
 */
public interface IMessagePayload extends IHardwareAddress, IHardwareToken {

  @NotNull String address();

  int bitResolution();


  /**
   * Retrieves a map of values associated with this message payload.
   * The map contains key-value pairs where the key is a {@link String} and the value is an {@link Object}.
   *
   * @return a {@link Map} containing the values of the message payload, never null.
   */
  @NotNull Map<String, Object> values();
}