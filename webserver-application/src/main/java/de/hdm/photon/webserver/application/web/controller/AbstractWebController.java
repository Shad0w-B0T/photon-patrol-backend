package de.hdm.photon.webserver.application.web.controller;

import jakarta.annotation.PostConstruct;
import lombok.extern.log4j.Log4j2;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Objects;
import java.util.Optional;


/**
 * An abstract base class for web controllers, providing common functionality for handling HTTP responses.
 */
@Log4j2
public abstract class AbstractWebController implements IWebController {

  /**
   * Initialize the controller. This method is called after the controller's properties have been set.
   * It logs the initialization of the REST controller with its name and path.
   */
  @PostConstruct
  public void init() {
    final String pathString = Optional
        .ofNullable(this.getClass().getAnnotation(RequestMapping.class))
        .map(RequestMapping::value)
        .map(path -> path[0])
        .orElse("none");

    log.info("Initialized rest-controller name={}(path={}).", this.getClass().getSimpleName(), pathString);
  }

  /**
   * Create a ResponseEntity with the specified HTTP status code and body.
   *
   * @param code   The HTTP status code.
   * @param type   The body of the response.
   * @param <TYPE> The type of the response body.
   * @return A ResponseEntity with the specified status code and body.
   * @throws NullPointerException      if the body is null.
   * @throws IndexOutOfBoundsException if the status code is not a valid HTTP status code.
   */
  @Override
  public @NotNull <TYPE> ResponseEntity<TYPE> body(int code,
                                                   @Nullable TYPE type) {
    // Null check
    Objects.requireNonNull(type);
    Objects.checkIndex(code, 600);

    return new ResponseEntity<>(type, HttpStatusCode.valueOf(code));
  }

  /**
   * Create a ResponseEntity with a success message and the specified HTTP status code.
   *
   * @param code    The HTTP status code.
   * @param message The success message.
   * @return A ResponseEntity with the success message and status code.
   */
  @Override
  public @NotNull ResponseEntity<IControllerMessage> successMessage(int code,
                                                                    @Nullable String message) {
    return this.message(true, code, message);
  }

  /**
   * Create a ResponseEntity with an error message and the specified HTTP status code.
   *
   * @param code    The HTTP status code.
   * @param message The error message.
   * @return A ResponseEntity with the error message and status code.
   */
  @Override
  public @NotNull ResponseEntity<IControllerMessage> errorMessage(int code,
                                                                  @Nullable String message) {
    return this.message(false, code, message);
  }

  /**
   * Create a ResponseEntity with a message and the specified HTTP status code.
   *
   * @param success Indicates whether the message is a success message.
   * @param code    The HTTP status code.
   * @param message The message.
   * @return A ResponseEntity with the message and status code.
   * @throws IndexOutOfBoundsException if the status code is not a valid HTTP status code.
   */
  private @NotNull ResponseEntity<IControllerMessage> message(final boolean success,
                                                              final int code,
                                                              @Nullable String message) {
    // Check
    Objects.checkIndex(code, 600);

    if (message == null) {
      message = "No specific information defined.";
    }

    return new ResponseEntity<>(new ImmutableControllerMessage(success, message), HttpStatusCode.valueOf(code));
  }
}