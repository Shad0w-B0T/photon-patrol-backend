package de.hdm.photon.database.influxdb.connection;

import org.jetbrains.annotations.Nullable;

import java.util.Map;


public interface IInfluxConnection {



  boolean addPoint(@Nullable final String pointName,
                   @Nullable final Map<String, Object> fields);

}
