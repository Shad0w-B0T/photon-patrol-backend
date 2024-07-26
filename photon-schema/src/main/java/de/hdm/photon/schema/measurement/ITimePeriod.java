package de.hdm.photon.schema.measurement;

import org.jetbrains.annotations.NotNull;

import java.util.Map;


public interface ITimePeriod<VALUE> {

  @NotNull VALUE min();

  @NotNull VALUE max();

  @NotNull VALUE average();

  @NotNull Map<Long, Map<String, Object>> values();

}
