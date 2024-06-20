package de.hdm.photon.database.influxdb.annoation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * Indicates that the annotated field represents the timestamp in an InfluxDB entity.
 * <p>
 * This annotation should be used on a field within a class that is mapped to InfluxDB records.
 * The annotated field will hold the timestamp of the measurement.
 * </p>
 *
 * <pre>
 * {@code
 * @InfluxTime
 * private long timestamp;
 * }
 * </pre>
 * <p>
 * Example usage:
 * <pre>
 * {@code
 * @InfluxTime
 * private long timestamp;
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
public @interface InfluxTime {
}
