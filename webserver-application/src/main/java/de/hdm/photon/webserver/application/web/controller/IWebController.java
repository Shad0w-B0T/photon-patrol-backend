package de.hdm.photon.webserver.application.web.controller;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.http.ResponseEntity;


/**
 * Interface defining common web controller operations.
 */
public interface IWebController {
  /**
   * Creates a ResponseEntity with the given status code and body.
   *
   * @param <TYPE> The type of the body.
   * @param code   The HTTP status code.
   * @param type   The body of the response.
   * @return A ResponseEntity with the specified status code and body.
   */
  @NotNull <TYPE> ResponseEntity<TYPE> body(final int code,
                                            @Nullable final TYPE type);

  /**
   * Creates a ResponseEntity with a success message.
   *
   * @param code    The HTTP status code.
   * @param message The success message.
   * @return A ResponseEntity with the specified status code and success message.
   */
  @NotNull ResponseEntity<IControllerMessage> successMessage(final int code,
                                                             @Nullable final String message);

  /**
   * Creates a ResponseEntity with an error message.
   *
   * @param code    The HTTP status code.
   * @param message The error message.
   * @return A ResponseEntity with the specified status code and error message.
   */
  @NotNull ResponseEntity<IControllerMessage> errorMessage(final int code,
                                                           @Nullable final String message);
}
