package de.hdm.photon.logic.hardware;


import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * Manages hardware operations.
 * This class extends {@link AbstractHardwareManager} and provides concrete implementation for hardware management.
 */
@Component
public class HardwareManager extends AbstractHardwareManager {

  /**
   * Constructs a {@link HardwareManager} with the specified {@link IHardwareHelper}.
   *
   * @param hardwareHelper the helper for hardware operations, can be null
   */
  @Autowired
  public HardwareManager(@Nullable IHardwareHelper hardwareHelper) {
    super(hardwareHelper);
  }
}
