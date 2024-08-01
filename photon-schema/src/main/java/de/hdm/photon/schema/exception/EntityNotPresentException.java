package de.hdm.photon.schema.exception;

/**
 * The {@code EntityNotPresentException} class represents an exception that is thrown
 * when an attempt is made to access or manipulate an entity that does not exist in the system.
 * It extends the {@link AbstractEntityException} class, providing a constructor to create an
 * instance of this exception with a specific error message.
 * <p>
 * This exception is typically used in scenarios where operations are performed on entities,
 * and the specified entity cannot be found in the system, leading to this exception being thrown.
 * <p>
 * Example usage:
 * <pre>
 * {@code
 * if (!entityExists(entityId)) {
 *     throw new EntityNotPresentException("Entity with ID " + entityId + " does not exist.");
 * }
 * }
 * </pre>
 */
public class EntityNotPresentException extends AbstractEntityException {
  /**
   * Constructs a new {@code EntityNotPresentException} with the specified detail message.
   * The cause is not initialized, and may subsequently be initialized by a call to {@link #initCause}.
   *
   * @param message the detail message. The detail message is saved for later retrieval
   *                by the {@link #getMessage()} method.
   */
  public EntityNotPresentException(String message) {
    super(message);
  }
}
