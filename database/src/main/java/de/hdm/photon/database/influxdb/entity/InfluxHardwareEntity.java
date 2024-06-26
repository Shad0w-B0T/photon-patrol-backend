package de.hdm.photon.database.influxdb.entity;

import de.hdm.photon.database.influxdb.annotation.InfluxMeasurement;
import de.hdm.photon.database.influxdb.annotation.InfluxTime;
import de.hdm.photon.database.influxdb.annotation.InfluxValue;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.Accessors;


@ToString
@Getter
@Accessors(fluent=true)
public final class InfluxHardwareEntity implements IInfluxHardwareEntity {

  @InfluxMeasurement
  String measurement;

  @InfluxTime
  private long time;
  @InfluxValue(name="engineX")
  private double engineX;

  @InfluxValue(name="engineY")
  private double engineY;

  @InfluxValue(name="lightSensorTop")
  private double lightSensorTop;

  @InfluxValue(name="lightSensorRight")
  private double lightSensorRight;

  @InfluxValue(name="lightSensorBottom")
  private double lightSensorBottom;

  @InfluxValue(name="lightSensorLeft")
  private double lightSensorLeft;

  @InfluxValue(name="voltageSupply")
  private double voltageSupply;

  @InfluxValue(name="voltageSolar")
  private double voltageSolar;

  @InfluxValue(name="currentSolar")
  private double currentSolar;

}
