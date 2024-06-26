package de.hdm.photon.logic.measurement;

import de.hdm.photon.schema.measurement.IMeasurementTable;
import de.hdm.photon.schema.measurement.Measurement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;


public interface IMeasurementManager {

  @NotNull List<String> getMeasurements();

  @NotNull List<String> getMeasurements(final long time);

  @NotNull IMeasurementTable getMeasurements(@Nullable final String hardware,
                                             final long time,
                                             final long groupBy,
                                             @Nullable final List<Measurement> measurements);

  @NotNull IMeasurementTable getMeasurements(@Nullable final String hardware,
                                             final long groupBy,
                                             @Nullable final List<Measurement> measurements);

  boolean addMeasurements(@Nullable final String hardware,
                          @Nullable final Map<String, Object> fields);
}
