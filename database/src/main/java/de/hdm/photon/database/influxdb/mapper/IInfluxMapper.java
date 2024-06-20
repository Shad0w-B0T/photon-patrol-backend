package de.hdm.photon.database.influxdb.mapper;

import org.influxdb.InfluxDB;
import org.influxdb.dto.QueryResult;
import org.jetbrains.annotations.Nullable;

import java.util.List;


public interface IInfluxMapper {

 /* <TYPE> List<TYPE> queryToObject(@Nullable final InfluxDB influxDB,
                                  @Nullable final Class<TYPE> typeClass,
                                  @Nullable final QueryResult queryResult);*/

  <TYPE> List<TYPE> toObject(@Nullable final Class<TYPE> typeClass,
                             @Nullable final QueryResult queryResult);

}
