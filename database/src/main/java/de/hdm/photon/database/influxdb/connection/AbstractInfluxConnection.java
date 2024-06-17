package de.hdm.photon.database.influxdb.connection;

import org.influxdb.InfluxDB;
import org.influxdb.dto.Point;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;


public abstract class AbstractInfluxConnection implements IInfluxConnection {

  private final @NotNull InfluxDB influxDB;

  protected AbstractInfluxConnection(@Nullable final InfluxDB influxDB) {
    //Null check
    Objects.requireNonNull(influxDB);

    this.influxDB = influxDB;
  }

  @Override
  public boolean addPoint(@Nullable String pointName,
                          @Nullable Map<String, Object> fields) {
    //Null check
    Objects.requireNonNull(pointName);
    Objects.requireNonNull(fields);

    final Point.Builder pointBuilder = Point.measurement("temperature")
        .time(System.currentTimeMillis(), TimeUnit.MILLISECONDS);

    /*fields.forEach((string, o) -> {
      pointBuilder.addField(string, o);
    });*/

    final Point point = pointBuilder.build();

    return false;
  }
}
