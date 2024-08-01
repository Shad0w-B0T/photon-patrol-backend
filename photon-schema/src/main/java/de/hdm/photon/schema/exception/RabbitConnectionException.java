package de.hdm.photon.schema.exception;

import org.jetbrains.annotations.Nullable;


/**
 * The {@code RabbitConnectionException} class represents exceptions that occur specifically in the context of RabbitMQ connections.
 * This class extends {@code AbstractConnectionException} and provides constructors to create exceptions related to RabbitMQ connections.
 */
public final class RabbitConnectionException extends AbstractConnectionException {
  /**
   * Constructs a new {@code RabbitConnectionException} with no specified detail message.
   */
  public RabbitConnectionException() {
  }

  /**
   * Constructs a new {@code RabbitConnectionException} with the specified detail message and cause.
   *
   * @param message the detail message. The detail message is saved for later retrieval by the {@link #getMessage()} method.
   * @param cause   the cause (which is saved for later retrieval by the {@link #getCause()} method).
   */
  public RabbitConnectionException(@Nullable String message,
                                   @Nullable Throwable cause) {
    super(message, cause);
  }

  /**
   * Constructs a new {@code RabbitConnectionException} with the specified detail message.
   *
   * @param message the detail message. The detail message is saved for later retrieval by the {@link #getMessage()} method.
   */
  public RabbitConnectionException(@Nullable String message) {
    super(message);
  }

  /**
   * Constructs a new {@code RabbitConnectionException} with the specified detail message, cause, suppression enabled or disabled, and writable stack trace enabled or disabled.
   *
   * @param message            the detail message (which is saved for later retrieval by the {@link #getMessage()} method).
   * @param cause              the cause (which is saved for later retrieval by the {@link #getCause()} method).
   * @param enableSuppression  whether suppression is enabled or disabled.
   * @param writableStackTrace whether the stack trace should be writable.
   */
  public RabbitConnectionException(@Nullable String message,
                                   @Nullable Throwable cause,
                                   boolean enableSuppression,
                                   boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }

  /**
   * Constructs a new {@code RabbitConnectionException} with the specified cause and a detail message of {@code (cause==null ? null : cause.toString())} (which typically contains the class and detail message of {@code cause}).
   *
   * @param cause the cause (which is saved for later retrieval by the {@link #getCause()} method).
   */
  public RabbitConnectionException(@Nullable Throwable cause) {
    super(cause);
  }
}
