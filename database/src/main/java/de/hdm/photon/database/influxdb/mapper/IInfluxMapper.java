package de.hdm.photon.database.influxdb.mapper;

import de.hdm.photon.schema.measurement.ITimeMap;
import org.influxdb.dto.QueryResult;
import org.jetbrains.annotations.Nullable;

import java.util.List;


public interface IInfluxMapper {

  ITimeMap toMap(@Nullable final QueryResult queryResult);

}
