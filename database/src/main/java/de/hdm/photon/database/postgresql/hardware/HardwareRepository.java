package de.hdm.photon.database.postgresql.hardware;

import de.hdm.photon.database.postgresql.logging.LoggingEntity;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;


public interface HardwareRepository extends JpaRepository<HardwareEntity, Long> {

  //------------------------------------------ ID -------------------------------------------------

  /**
   * Query to check if the id exists.
   *
   * @param id unique id of the entity.
   * @return true if the entity with the id exists.
   */
  boolean existsById(long id);


  /**
   * Query to get an entity via id.
   *
   * @param id value of the id.
   * @return an Optional if present. Null if no match.
   */
  @NotNull
  Optional<LoggingEntity> findById(long id);


  /**
   * Query to delete the entity via id.
   *
   * @param id value of the id.
   * @return an int value of how many were deleted.
   */
  int deleteById(long id);

  //------------------------------------------ Token -------------------------------------------------

  /**
   * Query to check if the token exists.
   *
   * @param token value to verify permissions.
   * @return true if the entity with the token exists.
   */
  boolean existsByToken(@NotNull String token);

  /**
   * Query to get an entity via token.
   *
   * @param token value of the token
   * @return an Optional if present. Null if no match.
   */
  @NotNull
  Optional<HardwareEntity> findByToken(@NotNull String token);

  /**
   * Query to delete the entity via the token.
   *
   * @param token value of the key.
   * @return an int value of how many were deleted.
   */
  int deleteByToken(@NotNull String token);

  //------------------------------------------ MAC -------------------------------------------------

  /**
   * Query to check if the mac address exists.
   *
   * @param macAddress value of the mac address.
   * @return true if the entity with the mac address exists.
   */
  boolean existsByMacAddress(@NotNull String macAddress);

  /**
   * Query to get a mac address.
   *
   * @param macAddress value of the mac address.
   * @return an Optional if present. Null if no match.
   */
  @NotNull
  Optional<HardwareEntity> findByMacAddress(@NotNull String macAddress);

  /**
   * Query to delete the entity via the mac address.
   *
   * @param macAddress value of the mac address.
   * @return an int value of how many were deleted.
   */
  int deleteByMacAddress(@NotNull String macAddress);


  //------------------------------------------ Hardware added -------------------------------------------------

  /**
   * Query to check if there is connected hardware.
   *
   * @param hardwareAdded time when the hardware was added.
   * @return true if there is connected hardware.
   */
  boolean existsByHardwareAdded(@NotNull Date hardwareAdded);

  /**
   * Query to get an entity by the time the hardware was added.
   *
   * @param hardwareAdded time when the hardware was added.
   * @return an Optional if present. Null if no match.
   */
  @NotNull
  Optional<HardwareEntity> findByHardwareAdded(@NotNull Date hardwareAdded);

  /**
   * Query to delete all hardware that were added on this date.
   *
   * @param hardwareAdded time when the hardware was added.
   * @return an int value of how many were deleted.
   */
  int deleteAllByHardwareAdded(@NotNull Date hardwareAdded);


  //------------------------------------------ MAC added -------------------------------------------------

  /**
   * Query to check if the mac was added.
   *
   * @param macAdded time when the mac was added.
   * @return true if the mac address exists.
   */
  boolean existsByMacAdded(@NotNull Date macAdded);

  /**
   * Query to get an entity when the hardware was added.
   *
   * @param macAdded time when the hardware was added.
   * @return an Optional if present. Null if no match.
   */
  @NotNull
  Optional<HardwareEntity> findByMacAdded(@NotNull Date macAdded);

  /**
   * Query to delete all mac addresses that were added on this date.
   *
   * @param macAdded time when the hardware was added.
   * @return an int value of how many were deleted.
   */
  int deleteAllByMacAdded(@NotNull Date macAdded);


  //------------------------------------------ Key active or not -------------------------------------------------

  /**
   * Checks if at least one entity with the specified value for the "keyIsActive" attribute exists.
   *
   * @param keyIsActive the value to search for
   * @return true if at least one entity with the specified value exists, otherwise false
   */
  boolean existsByKeyIsActive(boolean keyIsActive);


  /**
   * Query to get a list of active keys.
   *
   * @param keyIsActive whether key is active or not.
   * @return a List of all active keys.
   */
  @NotNull
  List<HardwareEntity> findByKeyIsActive(boolean keyIsActive);

  /**
   * Query to delete all entities by active/inactive key.
   *
   * @param keyIsActive whether key is active or not.
   * @return an int value of how many were deleted.
   */
  int deleteAllByKeyIsActive(boolean keyIsActive);

}
