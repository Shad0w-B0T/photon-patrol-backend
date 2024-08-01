package de.hdm.photon.schema.exception;

import org.jetbrains.annotations.Nullable;


/**
 * The {@code AbstractEntityException} class serves as a base class for exceptions
 * that are specific to entities within an application. It extends the {@code Exception}
 * class, providing constructors for creating exceptions with or without an error message.
 * <p>
 * This abstract class is intended to be extended by other custom exception classes
 * that represent specific entity-related error conditions.
 * <p>
 * Example usage:
 * <pre>
 * {@code
 * public class ProductNotFoundException extends AbstractEntityException {
 *     public ProductNotFoundException() {
 *         super();
 *     }
 *
 *     public ProductNotFoundException(String message) {
 *         super(message);
 *     }
 * }
 * }
 * </pre>
 */
public abstract class AbstractEntityException extends Exception {

  /**
   * Constructs a new {@code AbstractEntityException} with {@code null} as its detail message.
   * The cause is not initialized, and may subsequently be initialized by a call to {@link #initCause}.
   */
  public AbstractEntityException() {
  }

  /**
   * Constructs a new {@code AbstractEntityException} with the specified detail message.
   * The cause is not initialized, and may subsequently be initialized by a call to {@link #initCause}.
   *
   * @param message the detail message. The detail message is saved for later retrieval
   *                by the {@link #getMessage()} method.
   */
  public AbstractEntityException(@Nullable final String message) {
    super(message);
  }
}
