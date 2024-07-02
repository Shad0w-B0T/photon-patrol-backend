package de.hdm.photon.logic.measurement;

import de.hdm.photon.schema.exception.entity.EntityNotPresentException;
import de.hdm.photon.schema.measurement.ITimePeriod;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;


public interface IMeasurementManager {

  @NotNull List<String> getMeasurements();

  @NotNull List<String> getMeasurements(final long time);

  @NotNull List<String> getMeasurementsNameList(@Nullable final String hardware,
                                                final long after) throws EntityNotPresentException;

  @NotNull ITimePeriod<?> getMeasurementsOfKey(@Nullable final String hardware,
                                               @Nullable final String value,
                                               final long groupBy,
                                               final long after) throws EntityNotPresentException;

  default @NotNull ITimePeriod<?> getMeasurementsOfKey(@Nullable final String hardware,
                                                       @Nullable final String value) throws EntityNotPresentException {
    return this.getMeasurementsOfKey(hardware, value, -1, -1);
  }

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
   * @param key the key for the measurements, can be null.
   * @return true if the measurements were successfully deleted, false otherwise.
   */
  boolean deleteMeasurements(@Nullable final String hardware,
                             @Nullable final String key);
}
