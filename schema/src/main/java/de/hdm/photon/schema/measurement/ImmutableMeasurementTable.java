package de.hdm.photon.schema.measurement;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;


public record ImmutableMeasurementTable(@NotNull String name,
                                        @NotNull Map<Measurement, List<ITimeValue<?>>> measurements
) implements IMeasurementTable {
}
