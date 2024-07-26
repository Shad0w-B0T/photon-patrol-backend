package de.hdm.photon.database.influxdb.connection;

import de.hdm.photon.database.influxdb.mapper.IInfluxMapper;
import org.influxdb.InfluxDB;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
public final class InfluxConnection extends AbstractInfluxConnection {



  @Autowired
  public InfluxConnection(@Nullable InfluxDB influxDB,
                          @Value("${application.influxdb.database}") @Nullable String database,
                          @Nullable IInfluxMapper influxMapper) {
    super(influxDB, database, influxMapper);
  }
}
