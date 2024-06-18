package de.hdm.photon.schema.measurement;

import org.jetbrains.annotations.NotNull;


public interface ITimeValue<VALUE> {

  long time();

  @NotNull VALUE value();

}
