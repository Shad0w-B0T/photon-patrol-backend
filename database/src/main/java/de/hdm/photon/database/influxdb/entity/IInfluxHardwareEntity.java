package de.hdm.photon.database.influxdb.entity;

import de.hdm.photon.schema.hardware.IHardwareData;
import de.hdm.photon.schema.measurement.TimeBounded;
import org.jetbrains.annotations.NotNull;


/**
 * Interface representing an entity for hardware data stored in InfluxDB,
 * including measurements from hardware sensors and a Unix timestamp.
 */
public interface IInfluxHardwareEntity extends IHardwareData, TimeBounded {

  @NotNull String measurement();

}
