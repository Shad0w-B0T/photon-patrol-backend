package de.hdm.photon.database.influxdb.mapper;

import de.hdm.photon.schema.measurement.ITimeMap;
import de.hdm.photon.schema.measurement.TimeMap;
import lombok.extern.log4j.Log4j2;
import org.influxdb.dto.QueryResult;
import org.influxdb.dto.QueryResult.Result;
import org.influxdb.dto.QueryResult.Series;
import org.jetbrains.annotations.Nullable;

import java.time.Instant;
import java.util.List;
import java.util.Objects;


@Log4j2
/*
 * Abstract class for mapping InfluxDB query results to Java objects.
 */
public abstract class AbstractInfluxMapper implements IInfluxMapper {

  @Override
  public ITimeMap toMap(@Nullable QueryResult queryResult) {
    // Null check
    Objects.requireNonNull(queryResult);

    final List<Result> queryResults = queryResult.getResults();
    Objects.requireNonNull(queryResult, "No results.");

    final ITimeMap timeMap = new TimeMap();

    // Iterate through all results in the query
    for (final Result result : queryResults) {
      if (result.getSeries() == null) {
        continue; //Todo error
      }

      // Iterate through all series in the result
      for (final Series series : result.getSeries()) {

          // Iterate through all values in the series
          for (final List<Object> value : series.getValues()) {
            int index = 0;

            long time = -1;
            // Iterate through all objects in the value list
            for (Object object : value) {
              final int currentValue = index++;

              if (object == null) {
                continue; // Skip if the object is null
              }

              // Check if the column is "time" and set the time field if present
              if (series.getColumns().get(0).equals("time") && currentValue == 0 && time < 0) {
                time = Instant.parse((String) object).toEpochMilli();
                continue;
              }

              timeMap.addValue(time, series.getColumns().get(currentValue), object);
            }
          }
      }
    }

    return timeMap;
  }
}
