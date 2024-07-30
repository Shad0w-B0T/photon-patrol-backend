package de.hdm.photon.schema.measurement;

import lombok.ToString;
import lombok.extern.log4j.Log4j2;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


/**
 * Abstract base class for time-based mappings.
 * Provides a structure to store and manage values associated with specific timestamps.
 */
@ToString
@Log4j2
public abstract class AbstractTimeMap implements ITimeMap {

  private final Map<Long, Map<String, Object>> valueMap;

  /**
   * Constructs an AbstractTimeMap instance with an empty value map.
   */
  protected AbstractTimeMap() {
    this.valueMap = new HashMap<>();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public @NotNull Map<Long, Map<String, Object>> asMap() {
    return this.valueMap;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void addValue(long time,
                       @Nullable String key,
                       @Nullable Object value) {
    // Null Check
    Objects.checkIndex(time, Long.MAX_VALUE);
    Objects.requireNonNull(key);
    Objects.requireNonNull(value);

    @Nullable Map<String, Object> timeMap = this.valueMap.get(time);

    if (timeMap == null) {
      timeMap = new HashMap<>();
    }

    timeMap.put(key, value);

    this.valueMap.put(time, timeMap);
  }
}
