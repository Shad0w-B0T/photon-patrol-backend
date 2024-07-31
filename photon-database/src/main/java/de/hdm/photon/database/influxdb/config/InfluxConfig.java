package de.hdm.photon.database.influxdb.config;

import lombok.extern.log4j.Log4j2;
import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.Pong;
import org.influxdb.impl.InfluxDBMapper;
import org.influxdb.impl.InfluxDBResultMapper;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * Configuration for InfluxDB connection.
 */
@Configuration
@Log4j2
public class InfluxConfig {
  /**
   * Configures and provides an InfluxDB instance.
   *
   * @param url      InfluxDB server URL.
   * @param user     InfluxDB username for authentication.
   * @param password InfluxDB password for authentication.
   * @param database InfluxDB database name to connect to.
   * @return Configured InfluxDB instance.
   */
  @Bean
  public InfluxDB influxDB(@Value("${application.influxdb.url}") @NotNull String url,
                           @Value("${application.influxdb.user}") @NotNull String user,
                           @Value("${application.influxdb.password}") @NotNull String password,
                           @Value("${application.influxdb.database}") @NotNull String database) {
    // Initialize InfluxDB connection
    final InfluxDB connect = InfluxDBFactory.connect(url, user, password);

    // Check connection status
    final Pong pong = connect.ping();
    log.info("Connected to InfluxDB version {}. ({} ms)", pong.getVersion(), pong.getResponseTime());

    // Set the default database for operations
    connect.setDatabase(database);
    log.info("Set application operation database to '{}'.", database);

    return connect;
  }
}
