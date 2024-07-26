package de.hdm.photon.schema.measurement;

import org.jetbrains.annotations.NotNull;


public record ImmutableTimeValue<VALUE>(long time,
                                        @NotNull VALUE value
) implements ITimeValue<VALUE> {
}
