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

  /**
   * Constructs a new instance of {@link HardwareSessionHelper}.
   *
   * <p>This constructor is used by Spring to inject dependencies for the {@link HardwareSessionHelper}
   * class. It is used to initialize the helper with the necessary repositories needed for hardware session
   * management.</p>
   *
   * @param hardwareSessionRepository the repository for managing hardware sessions. This parameter
   *                                  can be {@code null}, which means that the helper might not be able
   *                                  to perform certain operations that require this repository.
   * @param hardwareRepository        the repository for managing hardware entities. This parameter
   *                                  can be {@code null}, which means that the helper might not be able
   *                                  to perform certain operations that require this repository.
   */
  @Autowired
  public HardwareSessionHelper(@Nullable HardwareSessionRepository hardwareSessionRepository,
                               @Nullable HardwareRepository hardwareRepository) {
    super(hardwareSessionRepository, hardwareRepository);
  }
}
