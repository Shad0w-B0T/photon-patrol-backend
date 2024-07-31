package de.hdm.photon.database.postgresql;


import de.hdm.photon.schema.entity.Identifiable;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;


/**
 * Abstract base class for all entities that provides a common identifier.
 *
 * <p>This class is annotated with {@link MappedSuperclass}, which means it is not an entity itself but
 * rather a base class for other entity classes. Fields defined in this class will be inherited by
 * subclasses and included in their database mappings.</p>
 *
 * <p>Entities that extend this class will inherit the `id` field and its associated getter method.</p>
 *
 * @see Identifiable
 */
@MappedSuperclass
public abstract class AbstractEntity implements Identifiable {

  /**
   * The unique identifier for this entity.
   *
   * <p>This field is automatically generated and managed by the persistence provider.</p>
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  /**
   * Returns the unique identifier of this entity.
   *
   * @return the unique identifier of this entity.
   */
  @Override
  public long id() {
    return this.id;
  }
}
