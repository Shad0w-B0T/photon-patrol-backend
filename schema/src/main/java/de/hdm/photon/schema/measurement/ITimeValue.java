package de.hdm.photon.schema.measurement;

import org.jetbrains.annotations.NotNull;


public interface ITimeValue<VALUE> extends TimeBounded {

  @NotNull VALUE value();

}
