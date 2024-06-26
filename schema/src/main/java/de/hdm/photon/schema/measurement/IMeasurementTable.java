package de.hdm.photon.schema.measurement;

import de.hdm.photon.schema.entity.Nameable;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;


public interface IMeasurementTable extends Nameable {

  @NotNull Map<Measurement, List<ITimeValue<?>>> measurements();

}
