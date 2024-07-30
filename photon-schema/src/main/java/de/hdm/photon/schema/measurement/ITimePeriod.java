package de.hdm.photon.schema.measurement;

import org.jetbrains.annotations.NotNull;

import java.util.Map;


/**
 * Interface representing a period of time with associated values and computed statistics.
 *
 * @param <VALUE> the type of the min, max, and average values.
 */
public interface ITimePeriod<VALUE> {
  /**
   * Returns the minimum value within the time period.
   *
   * @return the minimum value. Never null.
   */
  @NotNull VALUE min();

  /**
   * Returns the maximum value within the time period.
   *
   * @return the maximum value. Never null.
   */
  @NotNull VALUE max();

  /**
   * Returns the average value within the time period.
   *
   * @return the average value. Never null.
   */
  @NotNull VALUE average();

  /**
   * Returns the map of timestamped values within the time period.
   *
   * @return a map where the key is a timestamp and the value is another map containing key-value pairs. Never null.
   */
  @NotNull Map<Long, Map<String, Object>> values();
}
