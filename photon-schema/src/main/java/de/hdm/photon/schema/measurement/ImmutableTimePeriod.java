package de.hdm.photon.schema.measurement;

import org.jetbrains.annotations.NotNull;

import java.util.Map;


/**
 * Immutable representation of a time period with associated values.
 *
 * @param <VALUE> The type of the values representing the minimum, maximum, and average.
 * @param min     The minimum value over the time period. Must not be null.
 * @param max     The maximum value over the time period. Must not be null.
 * @param average The average value over the time period. Must not be null.
 * @param values  A map of timestamped values where the key is the timestamp and the value is a map of key-value pairs.
 *                Must not be null.
 */
public record ImmutableTimePeriod<VALUE>(@NotNull VALUE min,
                                         @NotNull VALUE max,
                                         @NotNull VALUE average,
                                         @NotNull Map<Long, Map<String, Object>> values
) implements ITimePeriod<VALUE> {
}
