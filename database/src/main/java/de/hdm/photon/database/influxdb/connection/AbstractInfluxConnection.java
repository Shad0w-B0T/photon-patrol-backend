package de.hdm.photon.database.influxdb.connection;

import de.hdm.photon.database.influxdb.annoation.InfluxMeasurement;
import de.hdm.photon.database.influxdb.annoation.InfluxTime;
import de.hdm.photon.database.influxdb.annoation.InfluxValue;
import de.hdm.photon.database.influxdb.entity.InfluxHardwareEntity;
import de.hdm.photon.database.influxdb.mapper.IInfluxMapper;
import de.hdm.photon.database.influxdb.mapper.InfluxMapper;
import org.influxdb.InfluxDB;
import org.influxdb.dto.Point;
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;
import org.influxdb.dto.QueryResult.Series;
import org.influxdb.impl.InfluxDBMapper;
import org.influxdb.impl.InfluxDBResultMapper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;


public abstract class AbstractInfluxConnection implements IInfluxConnection {

  private final @NotNull InfluxDB influxDB;
  private final @NotNull String database;
  private final @NotNull IInfluxMapper influxMapper;

  protected AbstractInfluxConnection(@Nullable final InfluxDB influxDB,
                                     @Nullable final String database,
                                     @Nullable final IInfluxMapper influxMapper) {
    //Null check
    Objects.requireNonNull(influxDB);
    Objects.requireNonNull(database);
    Objects.requireNonNull(influxMapper);

    this.influxDB = influxDB;
    this.database = database;
    this.influxMapper = influxMapper;
  }


  /**
   * Retrieves all measurement names from InfluxDB.
   *
   * @return A list of measurement names.
   */
  @Override
  public @NotNull List<String> getMeasurements() {
    return this.query("SHOW MEASUREMENTS")
        .getResults()
        .stream()
        .flatMap(result -> Optional
            .ofNullable(result.getSeries())
            .stream()
            .flatMap(Collection::stream))
        .flatMap(series -> Optional
            .ofNullable(series.getValues())
            .stream()
            .flatMap(Collection::stream))
        .map(value -> (String) value.get(0))
        .collect(Collectors.toList());
  }

  /**
   * Retrieves measurement names from InfluxDB that have been updated after a specified Unix timestamp (in milliseconds).
   *
   * @param time Unix timestamp (in milliseconds) representing the time after which measurements were updated.
   * @return A list of measurement names updated after the specified time.
   */
  @Override
  public @NotNull List<String> getMeasurementsAfter(long time) {
    final List<String> measurements = this.getMeasurements();

    return measurements.stream()
        .filter(measurement ->
            this.query(String.format("SELECT * FROM \"%s\" WHERE time > %dms LIMIT 1", measurement, time))
                .getResults()
                .stream()
                .flatMap(result -> Optional
                    .ofNullable(result.getSeries())
                    .stream()
                    .flatMap(Collection::stream))
                .flatMap(series -> Optional
                    .ofNullable(series.getValues())
                    .stream()
                    .flatMap(Collection::stream))
                .findAny()
                .isPresent())
        .toList();
  }

  /**
   * Retrieves measurement names from InfluxDB that have been updated after a specified Date.
   *
   * @param date Date object representing the time after which measurements were updated.
   * @return A list of measurement names updated after the specified Date.
   * @throws NullPointerException if {@code date} is null.
   */
  @Override
  public @NotNull List<String> getMeasurementsAfter(@Nullable Date date) {
    Objects.requireNonNull(date, "Date must not be null.");

    return this.getMeasurementsAfter(date.getTime());
  }

  @Override
  public void getMeasurements(@Nullable String hardware) {
    new InfluxMapper().toObject(InfluxHardwareEntity.class, this.query(String.format("SELECT * FROM %s", hardware)))
        .forEach(influxHardwareEntity -> {
          System.out.println(influxHardwareEntity);
        });
  }

  public boolean addMeasurements(@Nullable String hardware,
                                 @Nullable Map<String, Object> fields) {
    //Null check
    Objects.requireNonNull(hardware);
    Objects.requireNonNull(fields);

    final Point.Builder pointBuilder = Point.measurement(hardware)
        .time(System.currentTimeMillis(), TimeUnit.MILLISECONDS);

    pointBuilder.fields(fields);

    final Point point = pointBuilder.build();

    this.influxDB.write(point);
    return true;
  }

  private @NotNull QueryResult query(@Nullable final String command) {
    final Query query = new Query(command, this.database);

    return this.influxDB.query(query);
  }
}
