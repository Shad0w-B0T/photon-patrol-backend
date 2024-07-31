package de.hdm.photon.database.postgresql.user;

import de.hdm.photon.schema.exception.EntityNotPresentException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


/**
 * Repository interface for managing user entities.
 */
public interface UserRepository extends JpaRepository<UserEntity, Long> {
  /**
   * Finds a user entity by username.
   *
   * @param username the username to search for.
   * @return an Optional containing the found user entity, or an empty Optional if no user is found.
   */
  @NotNull
  Optional<UserEntity> findByUsername(@Nullable final String username);

  /**
   * Finds a user entity by username or throws an exception if no user is found.
   *
   * @param username the username to search for.
   * @return the found user entity.
   * @throws EntityNotPresentException if no user is found.
   */
  default @NotNull UserEntity userOrElseThrow(@Nullable final String username) throws EntityNotPresentException {
    return this.findByUsername(username)
        .orElseThrow(() -> new EntityNotPresentException("No user found"));
  }
}
