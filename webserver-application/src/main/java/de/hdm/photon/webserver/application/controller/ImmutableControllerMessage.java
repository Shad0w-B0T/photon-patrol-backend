package de.hdm.photon.webserver.application.controller;

import io.swagger.v3.oas.annotations.media.Schema;
import org.jetbrains.annotations.NotNull;


/**
 * A record implementation of the IControllerMessage interface, representing an immutable controller message.
 *
 * @param success Indicates whether the operation was successful.
 * @param message A non-null string containing the message associated with the operation.
 */
@Schema(name="ControllerMessage",
    description="Represents a controller message that includes a success flag and a message.")
public record ImmutableControllerMessage(boolean success,
                                         @NotNull String message
) implements IControllerMessage {
}