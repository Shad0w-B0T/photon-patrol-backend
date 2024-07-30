package de.hdm.photon.schema.payload;

import de.hdm.photon.schema.hardware.IHardwareAddress;
import de.hdm.photon.schema.hardware.IHardwareToken;
import jakarta.validation.constraints.NotNull;

import java.util.Map;


/**
 * Represents a message payload that includes hardware address and token information.
 * This interface extends both {@link IHardwareAddress} and {@link IHardwareToken}.
 * It also provides a method to retrieve a map of values associated with the message payload.
 *
 * <p>The {@code IMessagePayload} interface is intended to be implemented by classes
 * that need to encapsulate hardware-related data and provide a standardized way
 * of accessing this data through defined methods.</p>
 */
public interface IMessagePayload extends IHardwareAddress, IHardwareToken {
  /**
   * Retrieves the hardware address associated with this message payload.
   *
   * <p>The {@code address} method should return a non-null {@link String} representing
   * the hardware address.</p>
   *
   * @return the hardware address as a non-null {@link String}.
   */
  @NotNull
  String address();

  /**
   * Retrieves the bit resolution associated with this message payload.
   *
   * <p>The {@code bitResolution} method should return an integer value representing
   * the bit resolution of the hardware data.</p>
   *
   * @return the bit resolution as an integer.
   */
  int bitResolution();

  /**
   * Retrieves a map of values associated with this message payload.
   * The map contains key-value pairs where the key is a {@link String} and the value is an {@link Object}.
   *
   * <p>The {@code values} method should return a non-null map containing the values of the message payload.
   * Each key in the map represents a specific attribute or piece of data, and the corresponding value
   * is the data associated with that key.</p>
   *
   * @return a {@link Map} containing the values of the message payload, never null.
   */
  @NotNull
  Map<String, Object> values();
}