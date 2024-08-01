package de.hdm.photon.logic.hardware.session;

import de.hdm.photon.database.postgresql.hardware.HardwareRepository;
import de.hdm.photon.database.postgresql.hardware.session.HardwareSessionRepository;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * {@code HardwareSessionHelper} is a component that provides helper methods for managing hardware sessions.
 * It extends {@link AbstractHardwareSessionHelper} to leverage common session management functionalities.
 * This class is annotated with {@link Component} to indicate that it is a Spring-managed bean.
 * <p>
 * It uses dependency injection to obtain instances of {@link HardwareSessionRepository} and
 * {@link HardwareRepository} which are essential for interacting with the hardware session and hardware data
 * in the database.
 * </p>
 */
@Component
public class HardwareSessionHelper extends AbstractHardwareSessionHelper {

  @Autowired
  public HardwareSessionHelper(@Nullable HardwareSessionRepository hardwareSessionRepository,
                               @Nullable HardwareRepository hardwareRepository) {
    super(hardwareSessionRepository, hardwareRepository);
  }
}
