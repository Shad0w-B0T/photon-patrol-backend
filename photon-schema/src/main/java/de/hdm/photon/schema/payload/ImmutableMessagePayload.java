package de.hdm.photon.schema.payload;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

/**
 * Represents an immutable message payload that includes a token, a MAC address, and a map of values.
 * This class is a record, providing a compact syntax for immutable data carriers.
 *
 * <p>The {@code ImmutableMessagePayload} record implements the {@link IMessagePayload} interface,
 * ensuring that it provides the required methods to access the hardware address, token,
 * bit resolution, and associated values.</p>
 *
 * <p>This record is designed to be used in contexts where immutability and thread-safety are required.
 * The use of {@code @JsonProperty} annotations facilitates JSON serialization and deserialization.</p>
 *
 * @param token        the token associated with the message payload, must not be null
 * @param macAddress   the MAC address associated with the hardware, must not be null
 * @param address      the hardware address associated with the message payload, must not be null
 * @param bitResolution the bit resolution associated with the message payload
 * @param values       a map of additional values associated with the message payload, must not be null
 */
public record ImmutableMessagePayload(
    @JsonProperty("ma") @NotNull String macAddress,
    @JsonProperty("t") @NotNull String token,
    @JsonProperty("ia") @NotNull String address,
    @JsonProperty("b") int bitResolution,
    @JsonProperty("vv") @NotNull Map<String, Object> values
) implements IMessagePayload {
}
