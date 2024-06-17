package de.hdm.photon.database.influxdb.connection;

import org.influxdb.InfluxDB;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;

public final class InfluxConnection extends AbstractInfluxConnection {

  @Autowired
  public InfluxConnection(@Nullable InfluxDB influxDB) {
    super(influxDB);
  }
}
