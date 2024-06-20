package de.hdm.photon.schema.hardware;

/**
 * Interface representing hardware data including engine angles, light sensor readings,
 * voltage supply values, and solar current measurements.
 */
public interface IHardwareData {

  /**
   * Retrieves the engine X-axis angle in degrees.
   *
   * @return Engine X-axis angle in degrees.
   */
  double engineX();

  /**
   * Retrieves the engine Y-axis angle in degrees.
   *
   * @return Engine Y-axis angle in degrees.
   */
  double engineY();

  /**
   * Retrieves the irradiation value from the top light sensor.
   *
   * @return Irradiation value from the top light sensor.
   */
  double lightSensorTop();

  /**
   * Retrieves the irradiation value from the right light sensor.
   *
   * @return Irradiation value from the right light sensor.
   */
  double lightSensorRight();

  /**
   * Retrieves the irradiation value from the bottom light sensor.
   *
   * @return Irradiation value from the bottom light sensor.
   */
  double lightSensorBottom();

  /**
   * Retrieves the irradiation value from the left light sensor.
   *
   * @return Irradiation value from the left light sensor.
   */
  double lightSensorLeft();

  /**
   * Retrieves the voltage supply value in volts.
   *
   * @return Voltage supply value in volts.
   */
  double voltageSupply();

  /**
   * Retrieves the voltage value from the solar panel in volts.
   *
   * @return Voltage value from the solar panel in volts.
   */
  double voltageSolar();

  /**
   * Retrieves the current value from the solar panel in amperes.
   *
   * @return Current value from the solar panel in amperes.
   */
  double currentSolar();
}
