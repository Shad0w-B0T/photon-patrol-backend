package de.hdm.photon.logic.logging;

import de.hdm.photon.database.postgresql.hardware.HardwareRepository;
import de.hdm.photon.database.postgresql.logging.LoggingRepository;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class LoggingHelper extends AbstractLoggingHelper {

  @Autowired
  public LoggingHelper(@Nullable LoggingRepository loggingRepository,
                       @Nullable HardwareRepository hardwareRepository) {
    super(loggingRepository, hardwareRepository);
  }
}
