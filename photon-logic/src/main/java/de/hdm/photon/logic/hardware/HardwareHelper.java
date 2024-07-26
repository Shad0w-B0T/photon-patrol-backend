package de.hdm.photon.logic.hardware;

import de.hdm.photon.database.postgresql.hardware.HardwareRepository;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class HardwareHelper extends AbstractHardwareHelper {
  @Autowired
  public HardwareHelper(@Nullable HardwareRepository hardwareRepository) {
    super(hardwareRepository);
  }
}
