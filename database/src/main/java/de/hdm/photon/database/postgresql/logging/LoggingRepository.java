package de.hdm.photon.database.postgresql.logging;


import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;


public interface LoggingRepository extends JpaRepository<LoggingEntity, Long> {


  /**
   * Query to check if the id exists.
   *
   * @param id unique id of the entity.
   * @return true if the entity with the id exists.
   */
  boolean existsById(long id);

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
   * Query to check if the ip address exists.
   *
   * @param ipAddress value of the ip.
   * @return true if the ip exists.
   */
  boolean existsByIpAddress(@NotNull String ipAddress);


}
