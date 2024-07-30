package de.hdm.photon.logic.measurement;

import de.hdm.photon.database.influxdb.connection.IInfluxConnection;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * Concrete implementation of {@link AbstractMeasurementManager} for managing measurements.
 * This class is a Spring component, allowing it to be managed and injected by the Spring container.
 *
 * <p>Provides a specific implementation for interacting with InfluxDB and handling measurements.</p>
 */
@Component
public final class MeasurementManager extends AbstractMeasurementManager {
  /**
   * Constructs an instance of {@code MeasurementManager} with the specified InfluxDB connection.
   *
   * @param influxConnection the InfluxDB connection to be used, can be null.
   */
  @Autowired
  public MeasurementManager(@Nullable final IInfluxConnection influxConnection) {
    super(influxConnection);
  }
}
