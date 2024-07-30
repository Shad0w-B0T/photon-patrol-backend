package de.hdm.photon.database.influxdb.connection;

import de.hdm.photon.database.influxdb.mapper.IInfluxMapper;
import org.influxdb.InfluxDB;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


/**
 * Concrete implementation of {@link AbstractInfluxConnection}.
 * This class provides a Spring-managed component for connecting to an InfluxDB database.
 */
@Component
public final class InfluxConnection extends AbstractInfluxConnection {
  /**
   * Constructs an instance of {@code InfluxConnection}.
   *
   * @param influxDB     The {@link InfluxDB} instance representing the connection to the InfluxDB database.
   *                     Can be null. If null, the connection will not be initialized.
   * @param database     The name of the database, read from the application properties using
   *                     the key {@code application.influxdb.database}. Can be null if not configured.
   * @param influxMapper The {@link IInfluxMapper} for mapping data to/from InfluxDB. Can be null.
   *                     If null, default mapping behavior may be used.
   */
  @Autowired
  public InfluxConnection(@Nullable InfluxDB influxDB,
                          @Value("${application.influxdb.database}") @Nullable String database,
                          @Nullable IInfluxMapper influxMapper) {
    super(influxDB, database, influxMapper);
  }
}
