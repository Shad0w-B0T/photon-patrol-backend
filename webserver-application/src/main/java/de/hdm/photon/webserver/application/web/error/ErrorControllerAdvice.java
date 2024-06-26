package de.hdm.photon.webserver.application.web.error;

import de.hdm.photon.schema.exception.auth.FormatMismatchException;
import de.hdm.photon.schema.exception.entity.EntityAlreadyPresentException;
import de.hdm.photon.schema.exception.entity.EntityNotPresentException;
import de.hdm.photon.webserver.application.web.controller.AbstractWebController;
import de.hdm.photon.webserver.application.web.controller.IControllerMessage;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * Controller advice to handle exceptions and provide appropriate responses.
 */
@ControllerAdvice
public final class ErrorControllerAdvice extends AbstractWebController {
  /**
   * Handles EntityNotPresentException.
   *
   * @param exception The EntityNotPresentException to handle.
   * @return ResponseEntity containing an error message and HTTP status code 404.
   */
  @ResponseBody
  @ExceptionHandler(EntityNotPresentException.class)
  @ApiResponse(
      responseCode="404",
      description="The entity was not found.",
      content={
          @Content(
              mediaType=MediaType.APPLICATION_JSON_VALUE,
              schema=@Schema(implementation=IControllerMessage.class),
              examples=@ExampleObject(
                  value="{ \"success\": false, \"message\": \"Entity not found.\" }"
              )
          )
      }
  )
  private ResponseEntity<IControllerMessage> entityNotFoundExceptionHandler(@NotNull final EntityNotPresentException exception) {
    return this.errorMessage(404, exception.getMessage());
  }

  /**
   * Handles EntityAlreadyPresentException.
   *
   * @param exception The EntityAlreadyPresentException to handle.
   * @return ResponseEntity containing an error message and HTTP status code 409.
   */
  @ResponseBody
  @ExceptionHandler(EntityAlreadyPresentException.class)
  @ApiResponse(
      responseCode="409",
      description="The entity already exists.",
      content={
          @Content(
              mediaType=MediaType.APPLICATION_JSON_VALUE,
              schema=@Schema(implementation=IControllerMessage.class),
              examples=@ExampleObject(
                  value="{ \"success\": false, \"message\": \"Entity already present.\" }"
              )
          )
      }
  )
  private ResponseEntity<IControllerMessage> entityExceptionHandler(@NotNull final EntityAlreadyPresentException exception) {
    return this.errorMessage(409, exception.getMessage());
  }

  /**
   * Handles FormatMismatchException.
   *
   * @param exception The FormatMismatchException to handle.
   * @return ResponseEntity containing an error message and HTTP status code 406.
   */
  @ResponseBody
  @ExceptionHandler(FormatMismatchException.class)
  @ApiResponse(
      responseCode="406",
      description="The format of the request is not acceptable.",
      content={
          @Content(
              mediaType=MediaType.APPLICATION_JSON_VALUE,
              schema=@Schema(implementation=IControllerMessage.class),
              examples=@ExampleObject(
                  value="{ \"success\": false, \"message\": \"Format mismatch.\" }"
              )
          )
      }
  )
  private ResponseEntity<IControllerMessage> formatMismatchExceptionHandler(@NotNull final FormatMismatchException exception) {
    return this.errorMessage(406, exception.getMessage());
  }
}
