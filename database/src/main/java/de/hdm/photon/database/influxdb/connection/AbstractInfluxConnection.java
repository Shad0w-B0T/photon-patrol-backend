package de.hdm.photon.database.influxdb.connection;

import de.hdm.photon.database.influxdb.entity.IInfluxHardwareEntity;
import de.hdm.photon.database.influxdb.entity.InfluxHardwareEntity;
import de.hdm.photon.database.influxdb.mapper.IInfluxMapper;
import org.influxdb.InfluxDB;
import org.influxdb.dto.Point;
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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
   * {@inheritDoc}
   */
  @Override
  public @NotNull List<IInfluxHardwareEntity> getMeasurements(@Nullable String hardware,
                                                              long groupBy) {
    //Null check
    Objects.requireNonNull(hardware);

    final List<InfluxHardwareEntity> entities;

    if (groupBy>0) {
      entities = this.influxMapper.toObject(InfluxHardwareEntity.class,
          this.query(String.format("SELECT mean(*) FROM \"%s\" GROUP BY time(%dms)", hardware, groupBy)));
    } else {
      entities = this.influxMapper
          .toObject(InfluxHardwareEntity.class, this.query(String.format("SELECT * FROM \"%s\"", hardware)));
    }

    return entities
        .stream()
        .map(influxHardwareEntity -> (IInfluxHardwareEntity) influxHardwareEntity)
        .toList();
  }

  @Override
  public @NotNull List<IInfluxHardwareEntity> getMeasurementsAfter(@Nullable String hardware,
                                                                   long time,
                                                                   long groupBy) {
    //Null check
    Objects.requireNonNull(hardware);
    Objects.checkIndex(time, Long.MAX_VALUE);

    final List<InfluxHardwareEntity> entities;

    if (groupBy>0) {
      entities = this.influxMapper.toObject(InfluxHardwareEntity.class,
          this.query(String.format("SELECT mean(*) FROM \"%s\" WHERE time > %dms GROUP BY time(%dms)", hardware, time, groupBy)));
    } else {
      entities = this.influxMapper.toObject(InfluxHardwareEntity.class,
          this.query(String.format("SELECT * FROM \"%s\" WHERE time > %dms", hardware, time)));
    }

    return entities
        .stream()
        .map(influxHardwareEntity -> (IInfluxHardwareEntity) influxHardwareEntity)
        .toList();
  }

  /**
   * {@inheritDoc}
   */
  @Override
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

  @Override
  public void t() {
    System.out.println(this.influxMapper.toMap(this.query(String.format("SELECT mean(\"engineX\") FROM \"%s\" GROUP BY time"+
        "(%ds)", "Test", 20))));

    System.out.println(this.query(String.format("SELECT mean(\"engineX\"), max(\"engineX\"), min(\"engineX\")  "+
        "FROM \"%s\"", "Test")));
    System.out.println(this.influxMapper.toMap(this.query(String.format("SELECT mean(\"engineX\"), max(\"engineX\"), min(\"engineX\")  "+
        "FROM \"%s\"", "Test"))).toString());

  }

  private @NotNull QueryResult query(@Nullable final String command) {
    final Query query = new Query(command, this.database);

    return this.influxDB.query(query);
  }
}
