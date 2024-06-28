package de.hdm.photon.logic.measurement;

import de.hdm.photon.database.influxdb.connection.IInfluxConnection;
import de.hdm.photon.database.influxdb.entity.IInfluxHardwareEntity;
import de.hdm.photon.logic.security.io.ISecurityFilter;
import de.hdm.photon.logic.security.io.SecurityFilter;
import de.hdm.photon.schema.exception.entity.EntityNotPresentException;
import de.hdm.photon.schema.hardware.IHardwareData;
import de.hdm.photon.schema.measurement.IMeasurementTable;
import de.hdm.photon.schema.measurement.ITimePeriod;
import de.hdm.photon.schema.measurement.ITimeValue;
import de.hdm.photon.schema.measurement.ImmutableMeasurementTable;
import de.hdm.photon.schema.measurement.ImmutableTimeValue;
import de.hdm.photon.schema.measurement.Measurement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;


public abstract class AbstractMeasurementManager implements IMeasurementManager {

  private final @NotNull IInfluxConnection influxConnection;
  private final @NotNull ISecurityFilter securityFilter;

  private final Map<Measurement, Function<IInfluxHardwareEntity, ?>> measurementFunctionMap;

  protected AbstractMeasurementManager(@Nullable final IInfluxConnection influxConnection) {
    //Null check
    Objects.requireNonNull(influxConnection);

    this.influxConnection = influxConnection;
    this.securityFilter = new SecurityFilter();
    this.measurementFunctionMap = new HashMap<>();

    this.measurementFunctionMap.put(Measurement.ANGLE_X, IHardwareData::engineX);
    this.measurementFunctionMap.put(Measurement.ANGLE_Y, IHardwareData::engineY);
    this.measurementFunctionMap.put(Measurement.VERTICAL_EFFICIENCY,
        hardwareEntity -> Math.abs(hardwareEntity.lightSensorTop()-hardwareEntity.lightSensorBottom()));
    this.measurementFunctionMap.put(Measurement.HORIZONTAL_EFFICIENCY,
        hardwareEntity -> Math.abs(hardwareEntity.lightSensorRight()-hardwareEntity.lightSensorLeft()));
    this.measurementFunctionMap.put(Measurement.VOLTAGE_SOLAR, IHardwareData::voltageSolar);
    this.measurementFunctionMap.put(Measurement.AMPERE_SOLAR, IHardwareData::currentSolar);
    this.measurementFunctionMap.put(Measurement.POWER_SOLAR,
        hardwareEntity -> hardwareEntity.voltageSolar()*hardwareEntity.currentSolar());
    this.measurementFunctionMap.put(Measurement.VOLTAGE_CONTROLLER,
        IHardwareData::voltageSupply);
  }

  @Override
  public @NotNull List<String> getMeasurements() {
    return this.influxConnection.getMeasurements();
  }

  @Override
  public @NotNull List<String> getMeasurements(long time) {
    return this.influxConnection.getMeasurementsAfter(time);
  }

  @Override
  public @NotNull IMeasurementTable getMeasurements(@Nullable String hardware,
                                                    long groupBy,
                                                    @Nullable List<Measurement> measurements) {
    return this.measurementTables(hardware, this.influxConnection.getMeasurements(hardware, groupBy), measurements);
  }

  @Override
  public @NotNull IMeasurementTable getMeasurements(@Nullable String hardware,
                                                    long time,
                                                    long groupBy,
                                                    @Nullable List<Measurement> measurements) {
    return this.measurementTables(hardware, this.influxConnection.getMeasurementsAfter(hardware, time, groupBy),
        measurements);
  }


  private @NotNull IMeasurementTable measurementTables(@Nullable final String hardware,
                                                       @Nullable final List<IInfluxHardwareEntity> list,
                                                       @Nullable final List<Measurement> measurements) {
    //Null check
    Objects.requireNonNull(hardware);
    Objects.requireNonNull(list);
    Objects.requireNonNull(measurements);

    //Sanitize
    this.securityFilter.check(hardware);

    final MultiValueMap<Measurement, ITimeValue<?>> valueMap = new LinkedMultiValueMap<>();

    for (final IInfluxHardwareEntity hardwareEntity : list) {
      if (!hardwareEntity.measurement().equals(hardware)) {
        continue;
      }

      for (final Measurement measurement : measurements) {
        Optional
            .ofNullable(measurement)
            .map(this.measurementFunctionMap::get)
            .map(function -> function.apply(hardwareEntity))
            .ifPresent(object ->
                valueMap.add(measurement, new ImmutableTimeValue<>(hardwareEntity.time(), object)));
      }
    }

    return new ImmutableMeasurementTable(hardware, valueMap);
  }

  @Override
  public @NotNull ITimePeriod<?> getMeasurementsOfKey(@Nullable String hardware,
                                                      @Nullable String value,
                                                      long groupBy,
                                                      final long after) throws EntityNotPresentException {
    return this.influxConnection.getMeasurementsOfKey(hardware, value, groupBy, after);
  }

  @Override
  public @NotNull List<String> getMeasurementsNameList(@Nullable String hardware,
                                                       long after) throws EntityNotPresentException {
    return this.influxConnection.getMeasurementsNameList(hardware, after);
  }

  @Override
  public boolean addMeasurements(@Nullable String hardware,
                                 @Nullable Map<String, Object> fields) {
    //Null check
    Objects.requireNonNull(hardware);
    Objects.requireNonNull(fields);

    //Sanitize
    this.securityFilter.check(hardware);
    this.securityFilter.checkMap(fields);

    return this.influxConnection.addMeasurements(hardware, fields);
  }
}
