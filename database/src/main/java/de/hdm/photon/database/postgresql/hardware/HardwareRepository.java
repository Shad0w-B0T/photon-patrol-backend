package de.hdm.photon.database.postgresql.hardware;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;


public interface HardwareRepository extends JpaRepository<HardwareEntity, Long> {

  /**
   * Query to check if the id exists.
   *
   * @param id unique id of the entity.
   * @return true if the entity with the id exists.
   */
  boolean existsById(@NotNull Long id);

  /**
   * Query to check if the key exists.
   *
   * @param key value to verify permissions.
   * @return true if the entity with the key exists.
   */
  boolean existsByKey(@NotNull String key);

  /**
   * Query to check if the mac address exists.
   *
   * @param macAddress value of the mac address.
   * @return true if the entity with the mac address exists.
   */
  boolean existsByMacAddress(@NotNull String macAddress);

  //TODO more queries


}
