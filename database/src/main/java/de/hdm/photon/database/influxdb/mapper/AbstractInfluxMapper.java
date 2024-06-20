package de.hdm.photon.database.influxdb.mapper;

import de.hdm.photon.database.influxdb.annoation.InfluxMeasurement;
import de.hdm.photon.database.influxdb.annoation.InfluxTime;
import de.hdm.photon.database.influxdb.annoation.InfluxValue;
import lombok.extern.log4j.Log4j2;
import org.influxdb.dto.QueryResult;
import org.influxdb.dto.QueryResult.Result;
import org.influxdb.dto.QueryResult.Series;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;


@Log4j2

/*
 * Abstract class for mapping InfluxDB query results to Java objects.
 */
public abstract class AbstractInfluxMapper implements IInfluxMapper {

  /**
   * Record to store class blueprint information.
   *
   * @param measurementField Field annotated with @InfluxMeasurement
   * @param timeField        Field annotated with @InfluxTime
   * @param contentFields    Map of field names to fields annotated with @InfluxValue
   */
  private record ClassBlueprint(@Nullable Field measurementField,
                                @Nullable Field timeField,
                                @NotNull Map<String, Field> contentFields
  ) {
  }

  /**
   * Retrieves the blueprint of the given class.
   *
   * @param blueprint The class to analyze.
   * @param <TYPE>    The type of the class.
   * @return The class blueprint.
   */
  private <TYPE> @NotNull ClassBlueprint getBlueprint(@Nullable final Class<TYPE> blueprint) {
    // Null check
    Objects.requireNonNull(blueprint);

    log.info("Get class blueprint for influx mapper of class={}.", blueprint.getSimpleName());
    Field measurementField = null;
    Field timeField = null;

    final Map<String, Field> contentFields = new HashMap<>();

    // Iterate through all declared fields of the class
    for (final Field declaredField : blueprint.getDeclaredFields()) {
      declaredField.setAccessible(true); // Make the field accessible

      // Check if the field is annotated with @InfluxMeasurement
      if (declaredField.isAnnotationPresent(InfluxMeasurement.class)) {
        measurementField = declaredField;
        log.info("Measurement field for class={} present name={}.", blueprint.getSimpleName(), declaredField.getName());
        continue;
      }
      // Check if the field is annotated with @InfluxTime
      if (declaredField.isAnnotationPresent(InfluxTime.class)) {
        timeField = declaredField;
        log.info("Time field for class={} present name={}.", blueprint.getSimpleName(), declaredField.getName());
        continue;
      }

      // Check if the field is annotated with @InfluxValue
      final InfluxValue influxValue = declaredField.getAnnotation(InfluxValue.class);

      if (influxValue == null) {
        continue; // If not annotated with @InfluxValue, skip the field
      }

      // Add the field to contentFields map with the annotation's name as the key
      contentFields.put(influxValue.name(), declaredField);
      log.info("Value field for class={} found name={}.", blueprint.getSimpleName(), declaredField.getName());
    }

    // Return a new ClassBlueprint with the identified fields
    return new ClassBlueprint(measurementField, timeField, contentFields);
  }

  /**
   * Converts InfluxDB query results to a list of Java objects.
   *
   * @param typeClass   The class of the objects to create.
   * @param queryResult The query result from InfluxDB.
   * @param <TYPE>      The type of the objects to create.
   * @return A list of objects of type TYPE.
   */
  @Override
  public <TYPE> List<TYPE> toObject(@Nullable Class<TYPE> typeClass,
                                    @Nullable QueryResult queryResult) {
    // Null check
    Objects.requireNonNull(typeClass);
    Objects.requireNonNull(queryResult);

    final List<TYPE> response = new ArrayList<>();
    final List<Result> queryResults = queryResult.getResults();

    Objects.requireNonNull(queryResult, "No results.");

    final ClassBlueprint classBlueprint = this.getBlueprint(typeClass);

    // Iterate through all results in the query
    for (final Result result : queryResults) {
      System.out.println(result.getSeries().get(0).getName());
      // Iterate through all series in the result
      for (final Series series : result.getSeries()) {
        try {
          // Iterate through all values in the series
          for (final List<Object> value : series.getValues()) {
            int index = 0;

            final TYPE type = typeClass.getConstructor().newInstance(); // Create a new instance of the typeClass

            // Set the measurement field if present
            if (classBlueprint.measurementField() != null) {
              classBlueprint.measurementField().set(type, series.getName());
            }

            // Iterate through all objects in the value list
            for (Object object : value) {
              final int currentValue = index++;

              if (object == null) {
                continue; // Skip if the object is null
              }

              // Check if the column is "time" and set the time field if present
              if (series.getColumns().get(0).equals("time") && currentValue == 0 && classBlueprint.timeField() != null) {
                classBlueprint.timeField().set(type, Instant.parse((String) object).toEpochMilli());
                continue;
              }

              try {
                // Get the corresponding field from contentFields map
                final Field contentField = classBlueprint.contentFields().get(series.getColumns().get(currentValue));

                if (contentField == null) {
                  continue; // Skip if no corresponding field
                }

                contentField.set(type, object); // Set the field's value
              } catch (final IllegalArgumentException exception) {
                // Error: wrong type
                log.warn("Can' use type for field={}.", series.getColumns().get(currentValue), exception);
              }
            }

            response.add(type); // Add the populated object to the response list
          }
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                 NoSuchMethodException e) {
          throw new RuntimeException(e);
        }
      }
    }

    return response; // Return the list of objects
  }
}
