package de.hdm.photon.database.influxdb.annotation;

import org.jetbrains.annotations.NotNull;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * Indicates that the annotated field represents a value in an InfluxDB entity.
 * <p>
 * This annotation should be used on a field within a class that is mapped to InfluxDB records.
 * The annotated field will hold a value corresponding to a column in the InfluxDB measurement.
 * </p>
 *
 * <pre>
 * {@code
 * @InfluxValue(name = "temperature")
 * private double temperature;
 * }
 * </pre>
 * <p>
 * Example usage:
 * <pre>
 * {@code
 * @InfluxValue(name = "temperature")
 * private double temperature;
 * }
 * </pre>
 * <p>
 * This annotation has runtime retention and can be applied to fields.
 *
 * @see java.lang.annotation.Retention
 * @see java.lang.annotation.Target
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface InfluxValue {

  /**
   * The name of the value as it appears in the InfluxDB measurement.
   *
   * @return the name of the value
   */
  @NotNull String name();
}