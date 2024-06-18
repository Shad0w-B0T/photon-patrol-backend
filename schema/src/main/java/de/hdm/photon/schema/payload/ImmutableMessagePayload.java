package de.hdm.photon.schema.payload;

import jakarta.validation.constraints.NotNull;

import java.util.Map;


/**
 * Represents an immutable message payload that includes a token, a MAC address, and a map of values.
 * This class is a record, providing a compact syntax for immutable data carriers.
 *
 * @param token      the token associated with the message payload, must not be null
 * @param macAddress the MAC address associated with the hardware, must not be null
 * @param values     a map of additional values associated with the message payload, must not be null
 */
public record ImmutableMessagePayload(@NotNull String token,
                                      @NotNull String macAddress,
                                      @NotNull Map<String, Object> values
) implements IMessagePayload {
}
