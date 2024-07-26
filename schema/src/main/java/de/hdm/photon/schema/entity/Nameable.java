package de.hdm.photon.schema.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import org.jetbrains.annotations.NotNull;


/**
 * The {@code Nameable} interface should be implemented by any class whose instances are intended
 * to have a name. This name is represented by a non-null {@code String} value.
 *
 * <p> The primary purpose of this interface is to provide a common method to retrieve the name
 * of an object, ensuring consistency across different types of nameable objects.</p>
 *
 * <p> Example usage:
 * <pre>
 * {@code
 * public class Product implements Nameable {
 *     private String name;
 *
 *     public Product(String name) {
 *         this.name = Objects.requireNonNull(name, "name cannot be null");
 *     }
 *
 *     @Override
 *     public String name() {
 *         return this.name;
 *     }
 * }
 * }
 * </pre>
 * </p>
 */
public interface Nameable {
  /**
   * Returns the name of the object.
   *
   * @return the name of the object.
   */
  @Schema(example="John Doe", description="The name of the object, which cannot be null.")
  @NotNull
  String name();
}
