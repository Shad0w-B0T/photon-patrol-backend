package de.hdm.photon.schema.measurement;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface ITimeTable<VALUE> {

  @NotNull VALUE average();
  @NotNull VALUE min();
  @NotNull VALUE max();
  @NotNull List<ITimeValue<VALUE>> values();

}
