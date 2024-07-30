package de.hdm.photon.logic.hardware.session;

import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Component;


@Component
public class HardwareSessionManager extends AbstractHardwareSessionManager {

  public HardwareSessionManager(@Nullable IHardwareSessionHelper loggingHelper) {
    super(loggingHelper);
  }
}
