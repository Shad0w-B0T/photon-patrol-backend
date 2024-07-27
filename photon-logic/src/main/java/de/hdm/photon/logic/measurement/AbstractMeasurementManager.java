package de.hdm.photon.logic.measurement;

import de.hdm.photon.database.influxdb.connection.IInfluxConnection;
import de.hdm.photon.logic.security.io.ISecurityFilter;
import de.hdm.photon.logic.security.io.SecurityFilter;
import de.hdm.photon.schema.exception.EntityNotPresentException;
import de.hdm.photon.schema.measurement.ITimePeriod;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;
import java.util.Objects;


/**
 * Abstract base class for managing measurements. Provides common functionality and
 * sets up the required components for measurement management.
 *
 * <p>This class implements the {@link IMeasurementManager} interface and includes
 * dependencies for interacting with InfluxDB and applying security filters.</p>
 */
public abstract class AbstractMeasurementManager implements IMeasurementManager {

  private final @NotNull IInfluxConnection influxConnection;
  private final @NotNull ISecurityFilter securityFilter;

  /**
   * Constructs an instance of {@code AbstractMeasurementManager} with the specified
   * InfluxDB connection.
   *
   * @param influxConnection the InfluxDB connection to be used, must not be null.
   * @throws NullPointerException if {@code influxConnection} is null.
   */
  protected AbstractMeasurementManager(@Nullable final IInfluxConnection influxConnection) {
    // Null check
    Objects.requireNonNull(influxConnection, "influxConnection must not be null");

    this.influxConnection = influxConnection;
    this.securityFilter = new SecurityFilter();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public @NotNull List<String> getMeasurements() {
    return this.influxConnection.getMeasurements();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public @NotNull List<String> getMeasurements(long time) {
    return this.influxConnection.getMeasurementsAfter(time);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public @NotNull ITimePeriod<?> getMeasurementsOfKey(@Nullable String hardware,
                                                      @Nullable String value,
                                                      long groupBy,
                                                      final long after) throws EntityNotPresentException {
    return this.influxConnection.getMeasurementsOfKey(hardware, value, groupBy, after);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public @NotNull List<String> getMeasurementsNameList(@Nullable String hardware,
                                                       long after) throws EntityNotPresentException {
    return this.influxConnection.getMeasurementsNameList(hardware, after);
  }

  /**
   * {@inheritDoc}
   */
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

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean deleteMeasurements(@Nullable String hardware) {
    //Null check
    Objects.requireNonNull(hardware);

    //Sanitize
    this.securityFilter.check(hardware);

    return this.influxConnection.deleteMeasurements(hardware);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean deleteMeasurements(@Nullable String hardware,
                                    @Nullable String key) {
    //Null check
    Objects.requireNonNull(hardware);
    Objects.requireNonNull(key);

    //Sanitize
    this.securityFilter.check(hardware);
    this.securityFilter.check(key);

    return this.influxConnection.deleteMeasurements(hardware, key);
  }
}
