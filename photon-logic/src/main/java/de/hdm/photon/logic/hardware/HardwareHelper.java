package de.hdm.photon.logic.hardware;

import de.hdm.photon.database.postgresql.hardware.HardwareRepository;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * Provides helper methods for hardware operations.
 * This class extends {@link AbstractHardwareHelper} and provides specific implementations
 * for interacting with the hardware repository.
 */
@Component
public class HardwareHelper extends AbstractHardwareHelper {
  /**
   * Constructs a {@link HardwareHelper} with the specified {@link HardwareRepository}.
   *
   * @param hardwareRepository the repository for hardware operations, can be null
   */
  @Autowired
  public HardwareHelper(@Nullable HardwareRepository hardwareRepository) {
    super(hardwareRepository);
  }
}
