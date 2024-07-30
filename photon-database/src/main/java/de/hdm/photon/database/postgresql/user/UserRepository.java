package de.hdm.photon.database.postgresql.user;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserRepository  extends JpaRepository<UserEntity, Long> {

  @NotNull
  Optional<UserEntity> findByUsername(@Nullable final String username);

}
