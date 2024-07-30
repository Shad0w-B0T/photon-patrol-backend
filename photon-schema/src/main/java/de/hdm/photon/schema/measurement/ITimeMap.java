package de.hdm.photon.schema.measurement;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;


/**
 * Interface for managing a time-based map of values.
 */
public interface ITimeMap {

  /**
   * Returns the underlying map of timestamped values.
   *
   * @return A map where the key is a timestamp and the value is another map containing key-value pairs.
   * The returned map is never null.
   */
  @NotNull
  Map<Long, Map<String, Object>> asMap();

  /**
   * Adds a value to the map at the specified time with the given key.
   *
   * @param time  The timestamp at which to add the value. Must be a valid long index.
   * @param key   The key associated with the value. Must not be null.
   * @param value The value to be added. Must not be null.
   * @throws IndexOutOfBoundsException if the time is not a valid long index.
   * @throws NullPointerException      if the key or value is null.
   */
  void addValue(long time,
                @Nullable final String key,
                @Nullable final Object value);

}
