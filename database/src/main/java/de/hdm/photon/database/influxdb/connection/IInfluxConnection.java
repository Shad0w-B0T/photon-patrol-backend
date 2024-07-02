package de.hdm.photon.database.influxdb.connection;

import de.hdm.photon.schema.exception.entity.EntityNotPresentException;
import de.hdm.photon.schema.measurement.ITimePeriod;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;


/**
 * Interface for managing InfluxDB connections and operations.
 */
public interface IInfluxConnection {
  /**
   * Retrieves a list of all measurements.
   *
   * @return a list of measurement names.
   */
  @NotNull
  List<String> getMeasurements();

  /**
   * Retrieves a list of all measurements taken after a specific time.
   *
   * @param time the timestamp after which measurements should be retrieved.
   * @return a list of measurement names.
   */
  @NotNull
  List<String> getMeasurementsAfter(final long time);

  /**
   * Retrieves a list of measurement names for a specific hardware taken after a specific time.
   *
   * @param hardware the hardware identifier, can be null.
   * @param after the timestamp after which measurements should be retrieved.
   * @return a list of measurement names.
   * @throws EntityNotPresentException if the entity is not present.
   */
  @NotNull
  List<String> getMeasurementsNameList(@Nullable final String hardware,
                                       final long after) throws EntityNotPresentException;

  /**
   * Retrieves a time period of measurements for a specific hardware and key, grouped by interval and taken after a specific time.
   *
   * @param hardware the hardware identifier, can be null.
   * @param key the key for the measurements, can be null.
   * @param groupBy the interval to group the measurements by.
   * @param after the timestamp after which measurements should be retrieved.
   * @return a time period of measurements.
   * @throws EntityNotPresentException if the entity is not present.
   */
  @NotNull
  ITimePeriod<?> getMeasurementsOfKey(@Nullable final String hardware,
                                      @Nullable final String key,
                                      final long groupBy,
                                      final long after) throws EntityNotPresentException;

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
   * @param key the key for the measurements, can be null.
   * @return true if the measurements were successfully deleted, false otherwise.
   */
  boolean deleteMeasurements(@Nullable final String hardware,
                             @Nullable final String key);

  /**
   * Adds measurements for a specific hardware.
   *
   * @param hardware the hardware identifier, can be null.
   * @param fields the fields containing the measurements.
   * @return true if the measurements were successfully added, false otherwise.
   */
  boolean addMeasurements(@Nullable final String hardware,
                          @Nullable final Map<String, Object> fields);
}
