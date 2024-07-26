package de.hdm.photon.schema.exception;

import org.jetbrains.annotations.Nullable;


/**
 * Exception thrown when there is a mismatch in password requirements.
 */
public final class PasswordException extends Exception {
  /**
   * Constructs a new {@code PasswordRequirementMismatchException} with {@code null} as its detail message.
   */
  public PasswordException() {
  }

  /**
   * Constructs a new {@code PasswordRequirementMismatchException} with the specified detail message.
   *
   * @param message the detail message (which is saved for later retrieval by the {@link #getMessage()} method)
   */
  public PasswordException(@Nullable final String message) {
    super(message);
  }

  /**
   * Constructs a new {@code PasswordRequirementMismatchException} with the specified detail message and cause.
   *
   * @param message the detail message (which is saved for later retrieval by the {@link #getMessage()} method)
   * @param cause   the cause (which is saved for later retrieval by the {@link #getCause()} method)
   */
  public PasswordException(@Nullable final String message,
                           @Nullable final Throwable cause) {
    super(message, cause);
  }

  /**
   * Constructs a new {@code PasswordRequirementMismatchException} with the specified cause and a detail message
   * of {@code (cause==null ? null : cause.toString())} (which typically contains the class and detail message
   * of {@code cause}). This constructor is useful for exceptions that are little more than wrappers for other
   * throwable (for example, {@link java.security.PrivilegedActionException}).
   *
   * @param cause the cause (which is saved for later retrieval by the {@link #getCause()} method)
   */
  public PasswordException(@Nullable final Throwable cause) {
    super(cause);
  }

  /**
   * Constructs a new {@code PasswordRequirementMismatchException} with the specified detail message,
   * cause, suppression enabled or disabled, and writable stack trace enabled or disabled.
   *
   * @param message            the detail message
   * @param cause              the cause. (A {@code null} value is permitted, and indicates that the cause
   *                           is nonexistent or unknown.)
   * @param enableSuppression  whether suppression is enabled or disabled
   * @param writableStackTrace whether stack trace should be writable
   */
  public PasswordException(@Nullable final String message,
                           @Nullable final Throwable cause,
                           boolean enableSuppression,
                           boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}