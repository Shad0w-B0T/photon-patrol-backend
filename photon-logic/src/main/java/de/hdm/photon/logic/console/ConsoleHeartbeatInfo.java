package de.hdm.photon.logic.console;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Duration;


@Component
public final class ConsoleHeartbeatInfo extends AbstractConsoleHeartbeatInfo {
  /**
   * Constructs an AbstractHeartbeatInfo with the specified timeout duration.
   *
   * @param timeoutAfter the duration in milliseconds after which a timeout is considered.
   * @throws IndexOutOfBoundsException if timeoutAfter is negative or exceeds Long.MAX_VALUE.
   */
  public ConsoleHeartbeatInfo(@Value("${application.heartbeat.delay}") long timeoutAfter) {
    super(Duration.ofSeconds(timeoutAfter).toMillis());
  }
}
