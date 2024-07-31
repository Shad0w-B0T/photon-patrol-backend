package de.hdm.photon.schema.hardware.session;

import io.swagger.v3.oas.annotations.media.Schema;
import org.jetbrains.annotations.NotNull;

import java.util.Date;


/**
 * This record creates a Logging object which stores the connection parameters to the hardware.
 *
 * @param ipAddress {@link IHardwareSession#ipAddress()}
 * @param timestamp {@link IHardwareSession#timestamp()}
 */
@Schema(name="Logging",
    description="Object to store the parameters who is connected to the hardware.")
public record ImmutableHardwareSession(long id,
                                       @NotNull String ipAddress,
                                       @NotNull Date timestamp
) implements IHardwareSession {
}
