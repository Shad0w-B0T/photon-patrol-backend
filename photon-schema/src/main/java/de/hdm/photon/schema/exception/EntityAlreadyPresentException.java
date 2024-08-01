package de.hdm.photon.schema.exception;

/**
 * The {@code EntityAlreadyPresentException} class represents an exception that is thrown
 * when an attempt is made to add an entity that already exists in the system. It extends
 * the {@link AbstractEntityException} class, providing a constructor to create an instance
 * of this exception with a specific error message.
 * <p>
 * This exception is typically used in scenarios where unique constraints are enforced
 * on entities, and an attempt to violate these constraints results in this exception being
 * thrown.
 * <p>
 * Example usage:
 * <pre>
 * {@code
 * if (entityAlreadyExists(entity)) {
 *     throw new EntityAlreadyPresentException("Entity with the same ID already exists.");
 * }
 * }
 * </pre>
 */
public class EntityAlreadyPresentException extends AbstractEntityException {
  /**
   * Constructs a new {@code EntityAlreadyPresentException} with the specified detail message.
   * The cause is not initialized, and may subsequently be initialized by a call to {@link #initCause}.
   *
   * @param message the detail message. The detail message is saved for later retrieval
   *                by the {@link #getMessage()} method.
   */
  public EntityAlreadyPresentException(String message) {
    super(message);
  }
}
