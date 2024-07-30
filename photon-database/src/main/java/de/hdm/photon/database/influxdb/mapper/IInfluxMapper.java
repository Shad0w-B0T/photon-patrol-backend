package de.hdm.photon.database.influxdb.mapper;

import de.hdm.photon.schema.measurement.ITimeMap;
import org.influxdb.dto.QueryResult;
import org.jetbrains.annotations.Nullable;

import java.util.List;


/**
 * Interface that defines the mapping from {@link QueryResult} to an {@link ITimeMap}.
 */
public interface IInfluxMapper {
  /**
   * Maps the provided {@link QueryResult} to an {@link ITimeMap}.
   *
   * @param queryResult the result of a query, which may be {@code null}
   * @return an {@link ITimeMap} representation of the query result; may be {@code null} if {@code queryResult} is {@code null}
   */
  ITimeMap toMap(@Nullable final QueryResult queryResult);
}
