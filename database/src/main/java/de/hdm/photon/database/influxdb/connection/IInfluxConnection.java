package de.hdm.photon.database.influxdb.connection;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Date;
import java.util.List;
import java.util.Map;


public interface IInfluxConnection {
  /**
   * Retrieves all measurement names from InfluxDB.
   *
   * @return A list of measurement names.
   */
  @NotNull List<String> getMeasurements();

  /**
   * Retrieves measurement names from InfluxDB that have been updated after a specified Unix timestamp (in milliseconds).
   *
   * @param time Unix timestamp (in milliseconds) representing the time after which measurements were updated.
   * @return A list of measurement names updated after the specified time.
   */
  @NotNull List<String> getMeasurementsAfter(final long time);

  /**
   * Retrieves measurement names from InfluxDB that have been updated after a specified Date.
   *
   * @param date Date object representing the time after which measurements were updated.
   * @return A list of measurement names updated after the specified Date.
   */
  @NotNull List<String> getMeasurementsAfter(@Nullable final Date date);

  void getMeasurements(@Nullable final String hardware);

  boolean addMeasurements(@Nullable final String hardware,
                          @Nullable final Map<String, Object> fields);

 /* List<Object> getFields(@Nullable final String pointName,
                        @Nullable final Map<String, Object> fields);

  */
}
