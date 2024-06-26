package de.hdm.photon.logic.measurement;

import de.hdm.photon.database.influxdb.connection.IInfluxConnection;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public final class MeasurementManager extends AbstractMeasurementManager {

  public MeasurementManager(@Autowired @Nullable final IInfluxConnection influxConnection) {
    super(influxConnection);
  }
}
