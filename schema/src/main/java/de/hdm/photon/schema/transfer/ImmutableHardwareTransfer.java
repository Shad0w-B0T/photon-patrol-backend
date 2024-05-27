package de.hdm.photon.schema.transfer;

import org.jetbrains.annotations.NotNull;

import java.util.Map;


public record ImmutableHardwareTransfer(@NotNull String macAddress,
                                        @NotNull String token,
                                        @NotNull Map<String, Object> values
) implements IHardwareTransfer {
}
