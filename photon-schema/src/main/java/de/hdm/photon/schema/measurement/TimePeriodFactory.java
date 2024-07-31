package de.hdm.photon.schema.measurement;

import lombok.extern.log4j.Log4j2;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.Objects;


/**
 * Factory class for creating and converting instances of {@link ITimePeriod}.
 */
@Log4j2
public final class TimePeriodFactory {
  /**
   * Creates a new instance of {@link ITimePeriod} with the specified values.
   *
   * @param min     the minimum value of the time period. Must not be null.
   * @param max     the maximum value of the time period. Must not be null.
   * @param average the average value of the time period. Must not be null.
   * @param values  a map of timestamped values within the time period. Must not be null.
   * @param <VALUE> the type of the min, max, and average values.
   * @return a new instance of {@link ITimePeriod}. Never null.
   * @throws NullPointerException if any of the arguments are null.
   */
  public static @NotNull <VALUE> ITimePeriod<VALUE> createTimePeriod(@Nullable final VALUE min,
                                                                     @Nullable final VALUE max,
                                                                     @Nullable final VALUE average,
                                                                     @Nullable final Map<Long, Map<String, Object>> values) {
    Objects.requireNonNull(min, "min must not be null");
    Objects.requireNonNull(max, "max must not be null");
    Objects.requireNonNull(average, "average must not be null");
    Objects.requireNonNull(values, "values must not be null");

    final ITimePeriod<VALUE> timePeriod = new ImmutableTimePeriod<>(min, max, average, values);
    log.debug("The timePeriod object was successfully created.");
    return timePeriod;
  }

  /**
   * Converts an existing {@link ITimePeriod} instance to a new record instance.
   *
   * @param timePeriod the existing time period to convert. Must not be null.
   * @param <VALUE>    the type of the min, max, and average values.
   * @return a new record instance of {@link ITimePeriod}. Never null.
   * @throws NullPointerException if the timePeriod is null.
   */
  public static @NotNull <VALUE> ITimePeriod<VALUE> convertToRecord(@Nullable final ITimePeriod<VALUE> timePeriod) {
    Objects.requireNonNull(timePeriod, "timePeriod must not be null");
    log.debug("The timePeriod object was successfully converted to a record.");
    return TimePeriodFactory.createTimePeriod(
        timePeriod.min(),
        timePeriod.max(),
        timePeriod.average(),
        Map.copyOf(timePeriod.values())
    );
  }
}
