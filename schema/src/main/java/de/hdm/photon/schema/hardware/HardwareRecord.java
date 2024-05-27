package de.hdm.photon.schema.hardware;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import org.springframework.lang.Nullable;

import java.util.Date;


/**
 * This record creates a Hardware object which stores all relevant parameters for the hardware.
 *
 * @param id {@link IHardware#id()}
 * @param key {@link IHardware#token()}
 * @param macAddress {@link IHardware#macAddress()}
 * @param hardwareAdded {@link IHardware#hardwareAdded()}
 * @param macAdded {@link IHardware#macAdded()}
 * @param keyIsActive {@link IHardware#keyIsActive()}
 */
@Schema(name = "Hardware",
        description = "Object to store the parameters for the hardware.")
public record HardwareRecord(long id,
                             @NotNull String token,
                             @Nullable String macAddress,
                             @Nullable Date hardwareAdded,
                             @Nullable Date macAdded,
                             @Nullable boolean keyIsActive) implements IHardware{
}
