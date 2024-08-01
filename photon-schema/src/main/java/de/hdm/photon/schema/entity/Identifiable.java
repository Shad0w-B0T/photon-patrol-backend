package de.hdm.photon.schema.entity;

import io.swagger.v3.oas.annotations.media.Schema;


/**
 * The {@code Identifiable} interface should be implemented by any class whose instances are intended
 * to be identifiable by a unique identifier. This identifier is represented by a long value.
 * <p>
 * The primary purpose of this interface is to provide a common method to retrieve the identifier
 * of an object, ensuring consistency across different types of identifiable objects.
 * <p>
 * Example usage:
 * <pre>
 * {@code
 * public class User implements Identifiable {
 *     private long id;
 *
 *     public User(long id) {
 *         this.id = id;
 *     }
 *
 *     @Override
 *     public long id() {
 *         return this.id;
 *     }
 * }
 * }
 * </pre>
 */
public interface Identifiable {
  /**
   * Returns the unique identifier of the object.
   *
   * @return the id of the object.
   */
  @Schema(example="1", description="Unique identifier of an object.")
  long id();
}
