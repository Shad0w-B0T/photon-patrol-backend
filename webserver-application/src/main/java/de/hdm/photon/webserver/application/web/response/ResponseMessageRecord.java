package de.hdm.photon.webserver.application.web.response;

import io.swagger.v3.oas.annotations.media.Schema;
import org.jetbrains.annotations.Nullable;


/**
 * @param message see {@link IResponseMessage#message()}.
 */
@Schema(
    name="ResponseMessage",
    description="Wrapper object to hold message."
)
public record ResponseMessageRecord(@Nullable String message) implements IResponseMessage {
}
