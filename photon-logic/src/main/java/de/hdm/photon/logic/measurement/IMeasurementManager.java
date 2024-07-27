package de.hdm.photon.logic.measurement;

import de.hdm.photon.schema.exception.EntityNotPresentException;
import de.hdm.photon.schema.measurement.ITimePeriod;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;


/**
 * Interface for managing measurements. Provides methods for retrieving, adding, and deleting measurements.
 *
 * @since 1.0
 */
public interface IMeasurementManager {
  /**
   * Retrieves a list of all measurements.
   *
   * @return a list of measurement identifiers, never null.
   */
  @NotNull
  List<String> getMeasurements();

  /**
   * Retrieves a list of measurements taken after the specified time.
   *
   * @param time the timestamp after which measurements should be retrieved.
   * @return a list of measurement identifiers, never null.
   */
  @NotNull
  List<String> getMeasurements(final long time);

  /**
   * Retrieves a list of measurement names for a specific hardware taken after a specified time.
   *
   * @param hardware the hardware identifier, can be null.
   * @param after    the timestamp after which measurements should be retrieved.
   * @return a list of measurement names, never null.
   * @throws EntityNotPresentException if the specified hardware is not present.
   */
  @NotNull
  List<String> getMeasurementsNameList(@Nullable final String hardware,
                                       final long after) throws EntityNotPresentException;

  /**
   * Retrieves a time period of measurements for a specific key within a specific hardware.
   *
   * @param hardware the hardware identifier, can be null.
   * @param value    the key for the measurements, can be null.
   * @param groupBy  the grouping interval for the measurements.
   * @param after    the timestamp after which measurements should be retrieved.
   * @return a time period object containing the measurements, never null.
   * @throws EntityNotPresentException if the specified hardware or key is not present.
   */
  @NotNull
  ITimePeriod<?> getMeasurementsOfKey(@Nullable final String hardware,
                                      @Nullable final String value,
                                      final long groupBy,
                                      final long after) throws EntityNotPresentException;

  /**
   * Retrieves a time period of measurements for a specific key within a specific hardware.
   * This is a default method that calls {@link #getMeasurementsOfKey(String, String, long, long)}
   * with groupBy and after set to -1.
   *
   * @param hardware the hardware identifier, can be null.
   * @param value    the key for the measurements, can be null.
   * @return a time period object containing the measurements, never null.
   * @throws EntityNotPresentException if the specified hardware or key is not present.
   */
  default @NotNull ITimePeriod<?> getMeasurementsOfKey(@Nullable final String hardware,
                                                       @Nullable final String value) throws EntityNotPresentException {
    return this.getMeasurementsOfKey(hardware, value, -1, -1);
  }

  /**
   * Adds measurements for a specific hardware.
   *
   * @param hardware the hardware identifier, can be null.
   * @param fields   a map of measurement fields to be added, can be null.
   * @return true if the measurements were successfully added, false otherwise.
   */
  boolean addMeasurements(@Nullable final String hardware,
                          @Nullable final Map<String, Object> fields);

  /**
   * Deletes measurements for a specific hardware.
   *
   * @param hardware the hardware identifier, can be null.
   * @return true if the measurements were successfully deleted, false otherwise.
   */
  boolean deleteMeasurements(@Nullable final String hardware);

  /**
   * Deletes measurements for a specific hardware and key.
   *
   * @param hardware the hardware identifier, can be null.
   * @param key      the key for the measurements, can be null.
   * @return true if the measurements were successfully deleted, false otherwise.
   */
  boolean deleteMeasurements(@Nullable final String hardware,
                             @Nullable final String key);
}
