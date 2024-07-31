package de.hdm.photon.schema.hardware;

import io.swagger.v3.oas.annotations.media.Schema;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Date;


/**
 * This record creates a Hardware object which stores all relevant parameters for the hardware.
 *
 * @param id            {@link IHardware#id()}
 * @param token         {@link IHardware#token()}
 * @param macAddress    {@link IHardware#macAddress()}
 * @param hardwareAdded {@link IHardware#hardwareAdded()}
 * @param macAdded      {@link IHardware#macAdded()}
 * @param tokenIsActive {@link IHardware#tokenIsActive()}
 */
@Schema(name="Hardware",
    description="Object to store the parameters for the hardware.")
public record ImmutableHardware(long id,
                                @NotNull String token,
                                @Nullable String macAddress,
                                @Nullable Date hardwareAdded,
                                @Nullable Date macAdded,
                                @Nullable Boolean tokenIsActive
) implements IHardware {
}
