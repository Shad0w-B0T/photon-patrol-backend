package de.hdm.photon.database.influxdb.connection;

import de.hdm.photon.database.influxdb.entity.IInfluxHardwareEntity;
import de.hdm.photon.schema.exception.entity.EntityNotPresentException;
import de.hdm.photon.schema.measurement.ITimePeriod;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;


public interface IInfluxConnection {
  @NotNull List<String> getMeasurements();

  @NotNull List<String> getMeasurementsAfter(final long time);

  default @NotNull List<IInfluxHardwareEntity> getMeasurements(@Nullable final String hardware) {
    return this.getMeasurements(hardware, -1);
  }

  @NotNull List<IInfluxHardwareEntity> getMeasurements(@Nullable final String hardware,
                                                       final long groupBy);

  default @NotNull List<IInfluxHardwareEntity> getMeasurementsAfter(@Nullable final String hardware,
                                                                    final long time) {
    return this.getMeasurementsAfter(hardware, time, -1);
  }

  @NotNull List<IInfluxHardwareEntity> getMeasurementsAfter(@Nullable final String hardware,
                                                            final long time,
                                                            final long groupBy);

  boolean addMeasurements(@Nullable final String hardware,
                          @Nullable final Map<String, Object> fields);


  @NotNull List<String> getMeasurementsNameList(@Nullable final String hardware,
                                                final long after) throws EntityNotPresentException;

  @NotNull ITimePeriod<?> getMeasurementsOfKey(@Nullable final String hardware,
                                               @Nullable final String key,
                                               final long groupBy,
                                               final long after) throws EntityNotPresentException;

  default @NotNull ITimePeriod<?> getMeasurementsOfKey(@Nullable final String hardware,
                                                       final long groupBy,
                                                       final long after) throws EntityNotPresentException {
    return this.getMeasurementsOfKey(hardware, "*", groupBy, after);
  }

  default @NotNull ITimePeriod<?> getMeasurementsOfKey(@Nullable final String hardware,
                                                       @Nullable final String key,
                                                       final long groupBy) throws EntityNotPresentException {
    return this.getMeasurementsOfKey(hardware, key, groupBy, -1);
  }

  default @NotNull ITimePeriod<?> getMeasurementsOfKey(@Nullable final String hardware,
                                                       @Nullable final String key) throws EntityNotPresentException {
    return this.getMeasurementsOfKey(hardware, key, -1);
  }
}
