package de.hdm.photon.database.influxdb.connection;

import de.hdm.photon.database.influxdb.mapper.IInfluxMapper;
import de.hdm.photon.schema.exception.EntityNotPresentException;
import de.hdm.photon.schema.measurement.ITimeMap;
import de.hdm.photon.schema.measurement.ITimePeriod;
import de.hdm.photon.schema.measurement.ImmutableTimePeriod;
import de.hdm.photon.schema.measurement.TimePeriodFactory;
import lombok.extern.log4j.Log4j2;
import org.influxdb.InfluxDB;
import org.influxdb.dto.Point;
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;
import org.influxdb.dto.QueryResult.Series;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;


/**
 * Abstract class representing a connection to an InfluxDB database.
 * Implements the {@link IInfluxConnection} interface.
 */
@Log4j2
public abstract class AbstractInfluxConnection implements IInfluxConnection {

  private final @NotNull InfluxDB influxDB;
  private final @NotNull String database;
  private final @NotNull IInfluxMapper influxMapper;

  /**
   * Constructs an instance of {@code AbstractInfluxConnection}.
   *
   * @param influxDB     The {@link InfluxDB} instance representing the connection to the InfluxDB database.
   *                     Must not be null.
   * @param database     The name of the database. Must not be null.
   * @param influxMapper The {@link IInfluxMapper} for mapping data to/from InfluxDB. Must not be null.
   * @throws NullPointerException if any of the parameters are null.
   */
  protected AbstractInfluxConnection(@Nullable final InfluxDB influxDB,
                                     @Nullable final String database,
                                     @Nullable final IInfluxMapper influxMapper) {
    // Null check
    Objects.requireNonNull(influxDB, "InfluxDB instance must not be null.");
    Objects.requireNonNull(database, "Database name must not be null.");
    Objects.requireNonNull(influxMapper, "InfluxMapper must not be null.");

    this.influxDB = influxDB;
    this.database = database;
    this.influxMapper = influxMapper;

    log.info("Initialize influxdb connection.");
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public @NotNull List<String> getMeasurements() {
    return this.query("SHOW MEASUREMENTS")
        .getResults()
        .stream()
        .flatMap(result -> Optional
            .ofNullable(result.getSeries())
            .stream()
            .flatMap(Collection::stream))
        .flatMap(series -> Optional
            .ofNullable(series.getValues())
            .stream()
            .flatMap(Collection::stream))
        .map(value -> (String) value.get(0))
        .collect(Collectors.toList());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public @NotNull List<String> getMeasurementsAfter(long time) {
    final List<String> measurements = this.getMeasurements();

    return measurements.stream()
        .filter(measurement ->
            this.query("SELECT * FROM \""+this.escape(measurement)+"\" WHERE time > "+time+"ms LIMIT 1")
                .getResults()
                .stream()
                .flatMap(result -> Optional
                    .ofNullable(result.getSeries())
                    .stream()
                    .flatMap(Collection::stream))
                .flatMap(series -> Optional
                    .ofNullable(series.getValues())
                    .stream()
                    .flatMap(Collection::stream))
                .findAny()
                .isPresent())
        .toList();
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

    final Point.Builder pointBuilder = Point.measurement(hardware)
        .time(System.currentTimeMillis(), TimeUnit.MILLISECONDS);

    pointBuilder.fields(fields);

    final Point point = pointBuilder.build();

    this.influxDB.write(point);
    return true;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public @NotNull ITimePeriod<?> getMeasurementsOfKey(@Nullable final String hardware,
                                                      @Nullable final String key,
                                                      final long groupBy,
                                                      final long after) throws EntityNotPresentException {
    //Null check
    Objects.requireNonNull(hardware);
    Objects.requireNonNull(key);

    final String afterString = after>0 ? " WHERE time > "+after+"ms " : "";
    final String escapeKey = this.escape(key);
    final String escapeHardware = this.escape(hardware);

    final ITimeMap periodTimeMap = this.influxMapper.toMap(
        this.query("SELECT mean(\""+escapeKey+"\"), max(\""+escapeKey+"\"), min(\""+escapeKey+"\") FROM \""+escapeHardware+
            "\""+afterString));

    final Map<Long, Map<String, Object>> periodMap = periodTimeMap.asMap();

    if (periodMap.isEmpty()) {
      throw new EntityNotPresentException("No hardware="+hardware+" present!");
    }

    final Map<String, Object> values = new ArrayList<>(periodMap.values()).get(0);

    if (values == null || values.isEmpty()) {
      throw new EntityNotPresentException("No values for date="+hardware+" present!");
    }

    final ITimeMap timeMap;

    if (groupBy<=0) {
      timeMap = this.influxMapper.toMap(
          this.query("SELECT \""+escapeKey+"\" as value FROM \""+escapeHardware+"\""+afterString));
    } else {
      timeMap = this.influxMapper.toMap(
          this.query("SELECT mean(\""+escapeKey+"\") as value FROM \""+escapeHardware+"\" "+afterString+" GROUP BY time("+groupBy+"ms)"));
    }

    return TimePeriodFactory.createTimePeriod(values.get("min"), values.get("max"), values.get("mean"), timeMap.asMap());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public @NotNull List<String> getMeasurementsNameList(@Nullable String hardware,
                                                       long after) throws EntityNotPresentException {
    //Null check
    Objects.requireNonNull(hardware);

    final String afterString = after>0 ? " WHERE time > "+after+"ms " : "";
    final QueryResult queryResult = this.query("SELECT * FROM \""+this.escape(hardware)+"\""+afterString+" LIMIT 1");

    if (queryResult.hasError()) {
      throw new EntityNotPresentException("Hardware="+hardware+" does not exists or has no fields.");
    }

    final List<String> fieldNames = queryResult.getResults().stream()
        // Filter out results that have no series
        .filter(result -> result.getSeries() != null)
        // Flatten the list of series from each result
        .flatMap(result -> result.getSeries().stream())
        // Flatten the list of values from each series
        .flatMap(series -> series.getValues().stream())
        // Find the first row of values
        .findFirst()
        // Map the row to the list of column names
        .map(row ->
            queryResult.getResults()
                .stream()
                // Filter out results that have no series
                .filter(result -> result.getSeries() != null)
                // Flatten the list of series from each result
                .flatMap(result -> result.getSeries().stream())
                // Find the first series
                .findFirst()
                // Get the list of columns from the first series
                .map(Series::getColumns)
                // If no series is found, return an empty list
                .orElseGet(List::of)
        )
        // If no row is found, return an empty list
        .orElseGet(Collections::emptyList)
        .stream()
        // Filter out the "time" column
        .filter(name -> !name.equals("time"))
        // Collect the results into a list
        .toList();

    if (fieldNames.isEmpty()) {
      throw new EntityNotPresentException("There are no fields for the specified measurement="+hardware+" and time or" +
          " the measurement does not exist.");
    }

    return fieldNames;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean deleteMeasurements(@Nullable String hardware) {
    //Null check
    Objects.requireNonNull(hardware);

    final QueryResult queryResult = this.query("DELETE FROM \""+this.escape(hardware)+"\"");

    return !queryResult.hasError();
  }

  @Override
  public boolean deleteMeasurements(@Nullable String hardware,
                                    @Nullable String key) {
    //Null check
    Objects.requireNonNull(hardware);
    Objects.requireNonNull(key);

    final QueryResult queryResult = this.query("DROP SERIES FROM \""+this.escape(hardware)+"\" WHERE \""+this.escape(key)+"\" "+
        "=~ /.*/");

    return !queryResult.hasError();
  }

  /**
   * Executes a query command on the InfluxDB database and returns the result.
   *
   * @param command the query command to be executed. Can be null.
   * @return the result of the query execution as a {@link QueryResult} object.
   * @throws NullPointerException if the database field is null.
   */
  private @NotNull QueryResult query(@Nullable final String command) {
    final Query query = new Query(command, this.database);

    return this.influxDB.query(query);
  }

  /**
   * Escapes special characters in a given string value. Currently, this method
   * replaces double quotes with escaped double quotes.
   *
   * @param value the string value to be escaped.
   * @return the escaped string value.
   * @throws NullPointerException if the input value is null.
   */
  private String escape(String value) {
    // Implement escaping logic if needed
    return value.replace("\"", "\\\"");
  }
}
