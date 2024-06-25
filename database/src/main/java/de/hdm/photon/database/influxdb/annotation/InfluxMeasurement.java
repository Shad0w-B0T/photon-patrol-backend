package de.hdm.photon.database.influxdb.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * Indicates that the annotated field represents the measurement name in an InfluxDB entity.
 * <p>
 * This annotation should be used on a field within a class that is mapped to InfluxDB records.
 * The annotated field will hold the name of the measurement (the equivalent of a table name in relational databases).
 * </p>
 *
 * <pre>
 * {@code
 * @InfluxMeasurement
 * private String measurementName;
 * }
 * </pre>
 *
 * Example usage:
 * <pre>
 * {@code
 * @InfluxMeasurement
 * private String measurementName;
 * }
 * </pre>
 *
 * This annotation has runtime retention and can be applied to fields.
 *
 * @see java.lang.annotation.Retention
 * @see java.lang.annotation.Target
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface InfluxMeasurement {

}
