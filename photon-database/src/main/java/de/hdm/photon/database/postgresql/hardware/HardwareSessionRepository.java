package de.hdm.photon.database.postgresql.hardware;


import de.hdm.photon.schema.exception.EntityNotPresentException;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;

@Repository
public interface HardwareSessionRepository extends JpaRepository<HardwareSessionEntity, Long> {

  //------------------------------------------ Ip address -------------------------------------------------

  /**
   * Query to check if the ip address exists.
   *
   * @param ipAddress value of the ip.
   * @return true if the ip exists.
   */
  boolean existsByIpAddress(@NotNull String ipAddress);

  /**
   * Query to get an ip address.
   *
   * @param ipAddress value of the ip.
   * @return an Optional if present. Null if no match.
   */
  @NotNull
  Optional<HardwareSessionEntity> findByIpAddress(@NotNull String ipAddress);

  /**
   * Query to delete the entity by ip.
   *
   * @param ipAddress value of the ip.
   * @return an int value of how many were deleted.
   */
  int deleteByIpAddress(@NotNull String ipAddress);


  //------------------------------------------ Timestamp of ip -------------------------------------------------

  /**
   * Query to get a timestamp of the ip.
   *
   * @param timestamp date when the ip was added.
   * @return an Optional if present. Null if no match.
   */
  @NotNull
  Optional<HardwareSessionEntity> findByTimestamp(@NotNull Date timestamp);

  /**
   * Query to check if the timestamp exists.
   *
   * @param timestamp time of a used ip.
   * @return true if the timestamp exists.
   */
  boolean existsByTimestamp(@NotNull Date timestamp);

  /**
   * Query to delete the entity by timestamp.
   *
   * @param timestamp timestamp of the ip.
   * @return an int value of how many were deleted.
   */
  int deleteByTimestamp(@NotNull Date timestamp);


  //------------------------------------ Combination of ip and timestamp -------------------------------------

  /**
   * Query to check if the combination of the ip and timestamp exists.
   *
   * @param ipAddress value of the ip address.
   * @param timestamp the exact time when the ip was connected.
   * @return true if the combination exists.
   */
  boolean existsByIpAddressAndTimestamp(@NotNull String ipAddress,
                                        @NotNull Date timestamp);

  /**
   * Query to get an ip with the time stamp.
   *
   * @param ipAddress value of the ip.
   * @param timestamp time when the ip was
   * @return an Optional if present. Null if no match.
   */
  @NotNull
  Optional<HardwareSessionEntity> findByIpAddressAndTimestamp(@NotNull String ipAddress,
                                                              @NotNull Date timestamp);

  /**
   * Query to delete the entity by hardware entity, ip and timestamp.
   *
   * @param hardwareEntity the hardware entity which it is linked to.
   * @param ipAddress value of the ip address.
   * @param timestamp the exact time when the ip was connected.
   * @return an int value of how many were deleted.
   */
  int deleteByHardwareEntityAndIpAddressAndTimestamp(@NotNull HardwareEntity hardwareEntity,
                                                     @NotNull String ipAddress,
                                                     @NotNull Date timestamp);


  //------------------------------- hardware entity, ip and timestamp --------------------------------------------

  /**
   * Query to check if the combination of the hardware entity, the ip and timestamp exists.
   *
   * @param hardwareEntity hardware.
   * @param ipAddress value of the ip address.
   * @param timestamp the exact time when the ip was connected.
   * @return true if the combination exists.
   */
  boolean existsByHardwareEntityAndIpAddressAndTimestamp(@NotNull HardwareEntity hardwareEntity,
                                                         @NotNull String ipAddress,
                                                         @NotNull Date timestamp);

  /**
   * Get the hardware session entity by id. If no match throw Exception.
   *
   * @param hardwareSessionId id of the entity.
   * @return hardware session entity.
   * @throws EntityNotPresentException if entity not present.
   */
  default @NotNull HardwareSessionEntity hardwareSessionElseThrow(final long hardwareSessionId) throws EntityNotPresentException {
    return this.findById(hardwareSessionId).orElseThrow(() -> new EntityNotPresentException("No hardware session entity with id ="+hardwareSessionId+"present!"));
  }
}
