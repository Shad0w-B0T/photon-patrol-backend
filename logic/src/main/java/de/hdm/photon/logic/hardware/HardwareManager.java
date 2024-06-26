package de.hdm.photon.logic.hardware;


import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class HardwareManager extends AbstractHardwareManager {
  @Autowired
  public HardwareManager(@Nullable IHardwareHelper hardwareHelper) {
    super(hardwareHelper);
  }
}
