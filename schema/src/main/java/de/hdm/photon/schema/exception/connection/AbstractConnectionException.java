package de.hdm.photon.schema.exception.connection;

import org.jetbrains.annotations.Nullable;


/**
 * The {@code AbstractConnectionException} class is the abstract base class for exceptions related to connection operations.
 * This class extends {@code Exception} and provides constructors to create connection-related exceptions.
 */
public abstract class AbstractConnectionException extends Exception {
  /**
   * Constructs a new {@code AbstractConnectionException} with no detail message.
   */
  public AbstractConnectionException() {
  }

  /**
   * Constructs a new {@code AbstractConnectionException} with the specified detail message.
   *
   * @param message the detail message. The detail message is saved for later retrieval by the {@link #getMessage()} method.
   */
  public AbstractConnectionException(@Nullable final String message) {
    super(message);
  }

  /**
   * Constructs a new {@code AbstractConnectionException} with the specified detail message and cause.
   *
   * @param message the detail message. The detail message is saved for later retrieval by the {@link #getMessage()} method.
   * @param cause   the cause (which is saved for later retrieval by the {@link #getCause()} method).
   */
  public AbstractConnectionException(@Nullable final String message,
                                     @Nullable final Throwable cause) {
    super(message, cause);
  }

  /**
   * Constructs a new {@code AbstractConnectionException} with the specified detail message, cause, suppression enabled or disabled, and writable stack trace enabled or disabled.
   *
   * @param message            the detail message (which is saved for later retrieval by the {@link #getMessage()} method).
   * @param cause              the cause (which is saved for later retrieval by the {@link #getCause()} method).
   * @param enableSuppression  whether or not suppression is enabled or disabled.
   * @param writableStackTrace whether or not the stack trace should be writable.
   */
  public AbstractConnectionException(@Nullable final String message,
                                     @Nullable final Throwable cause,
                                     boolean enableSuppression,
                                     boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }

  /**
   * Constructs a new {@code AbstractConnectionException} with the specified cause and a detail message of {@code (cause==null ? null : cause.toString())} (which typically contains the class and detail message of {@code cause}).
   *
   * @param cause the cause (which is saved for later retrieval by the {@link #getCause()} method).
   */
  public AbstractConnectionException(@Nullable final Throwable cause) {
    super(cause);
  }
}