package de.hdm.photon.webserver.application.controller;

import io.swagger.v3.oas.annotations.media.Schema;
import org.jetbrains.annotations.NotNull;


/**
 * Interface representing a controller message.
 */
@Schema(implementation=ImmutableControllerMessage.class)
public interface IControllerMessage {
  /**
   * Indicates whether the operation was successful.
   *
   * @return true if the operation was successful, false otherwise.
   */
  @Schema(description="Indicates whether the operation was successful.", example="true")
  boolean success();

  /**
   * Retrieves the message associated with the operation.
   *
   * @return A non-null string containing the message.
   */
  @Schema(description="The message associated with the operation.", example="Operation completed successfully.")
  @NotNull String message();
}