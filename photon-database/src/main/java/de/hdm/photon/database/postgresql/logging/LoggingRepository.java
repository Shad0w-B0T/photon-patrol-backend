package de.hdm.photon.database.postgresql.logging;


import de.hdm.photon.schema.exception.EntityNotPresentException;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;

@Repository
public interface LoggingRepository extends JpaRepository<LoggingEntity, Long> {


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
  Optional<LoggingEntity> findByIpAddress(@NotNull String ipAddress);

  /**
   * Query to delete the entity by ip.
   *
   * @param ipAddress value of the ip.
   * @return an int value of how many were deleted.
   */
  int deleteByIpAddress(@NotNull String ipAddress);


  /**
   * Query to check if the combination of the ip and timestamp exists.
   *
   * @param ipAddress value of the ip address.
   * @param timestamp the exact time when the ip was connected.
   * @return true if the combination exists.
   */
  boolean existsByIpAddressAndTimestampOfIp(@NotNull String ipAddress,
                                            @NotNull Date timestamp);


  /**
   * Query to get an ip with the time stamp.
   *
   * @param ipAddress value of the ip.
   * @param timestamp time when the ip was
   * @return an Optional if present. Null if no match.
   */
  @NotNull
  Optional<LoggingEntity> findByIpAddressAndTimestampOfIp(@NotNull String ipAddress,
                                                          @NotNull Date timestamp);

  /**
   * Query to get a timestamp of the ip.
   *
   * @param timestamp date when the ip was added.
   * @return an Optional if present. Null if no match.
   */
  @NotNull
  Optional<LoggingEntity> findByTimestampOfIp(@NotNull Date timestamp);

  /**
   * Get the logging entity by id. If no match throw Exception.
   *
   * @param loggingId id of the entity.
   * @return logging entity.
   * @throws EntityNotPresentException if entity not present.
   */
  default @NotNull LoggingEntity loggingElseThrow(final long loggingId) throws EntityNotPresentException {
    return this.findById(loggingId).orElseThrow(() -> new EntityNotPresentException("No logging entity with id ="+loggingId+"present!"));
  }
}
