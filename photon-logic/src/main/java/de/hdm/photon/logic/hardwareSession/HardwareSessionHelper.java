package de.hdm.photon.logic.hardwareSession;

import de.hdm.photon.database.postgresql.hardware.HardwareRepository;
import de.hdm.photon.database.postgresql.hardware.HardwareSessionRepository;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class HardwareSessionHelper extends AbstractHardwareSessionHelper {

  @Autowired
  public HardwareSessionHelper(@Nullable HardwareSessionRepository hardwareSessionRepository,
                               @Nullable HardwareRepository hardwareRepository) {
    super(hardwareSessionRepository, hardwareRepository);
  }
}
