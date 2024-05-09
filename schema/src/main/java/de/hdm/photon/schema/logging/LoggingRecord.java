package de.hdm.photon.schema.logging;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.util.Date;


/**
 * This record creates a Logging object which stores the connection parameters to the hardware.
 *
 * @param ipAddress {@link ILogging#ipAddress()}
 * @param timestampOfIp {@link ILogging#timestampOfIp()}
 */
@Schema(name = "Logging",
        description = "Object to store the parameters who is connected to the hardware.")
public record LoggingRecord(long id,
                            @NotNull String ipAddress,
                            @NotNull Date timestampOfIp) implements ILogging{
}
