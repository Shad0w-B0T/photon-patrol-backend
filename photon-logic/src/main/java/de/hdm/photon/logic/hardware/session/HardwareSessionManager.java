package de.hdm.photon.logic.hardware.session;

import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Component;

/**
 * {@code HardwareSessionManager} is a component responsible for managing hardware sessions.
 * It extends {@link AbstractHardwareSessionManager} to inherit and implement session management functionalities.
 * This class is annotated with {@link Component} to indicate that it is a Spring-managed bean.
 * <p>
 * The manager uses an {@link IHardwareSessionHelper} to assist with logging and session operations.
 * </p>
 */
@Component
public class HardwareSessionManager extends AbstractHardwareSessionManager {

  /**
   * Constructs a {@code HardwareSessionManager} with the specified {@code IHardwareSessionHelper}.
   * <p>
   * The constructor is designed to receive an optional helper for session-related operations, which may be
   * {@code null}. This allows for flexible configuration depending on the application's needs.
   * </p>
   *
   * @param loggingHelper the helper used for managing hardware sessions and logging; may be {@code null}.
   */
  public HardwareSessionManager(@Nullable IHardwareSessionHelper loggingHelper) {
    super(loggingHelper);
  }
}
