package de.hdm.photon.webserver.application.web.response;


import io.swagger.v3.oas.annotations.media.Schema;
import org.jetbrains.annotations.NotNull;


@Schema(implementation= ResponseMessageRecord.class)
public interface IResponseMessage {
  /**
   * Value to store message.
   *
   * @return message stored in as body.
   */
  @Schema(example="Error at entity=xy | Everything is ok.", description="Message to hold.")
  @NotNull
  String message();
}
