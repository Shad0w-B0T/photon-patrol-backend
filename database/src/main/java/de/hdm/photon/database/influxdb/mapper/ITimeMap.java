package de.hdm.photon.database.influxdb.mapper;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;


public interface ITimeMap  {

  @NotNull Map<Long, Map<String, Object>> asMap();

  void addValue(long time,
                @Nullable final String key,
                @Nullable final Object value);

}
