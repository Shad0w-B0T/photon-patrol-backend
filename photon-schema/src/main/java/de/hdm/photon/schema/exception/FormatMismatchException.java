package de.hdm.photon.schema.exception;


import org.jetbrains.annotations.Nullable;


/**
 * Exception thrown when the format does not match the expected format.
 */
public final class FormatMismatchException extends Exception {
  /**
   * Constructs a new FormatMismatchException with the specified detail message.
   *
   * @param message The detailed message of the error.
   */
  public FormatMismatchException(@Nullable final String message) {
    super(message);
  }
}
