package de.hdm.photon.logic.heartbeat;

import lombok.Getter;
import lombok.experimental.Accessors;
import lombok.extern.log4j.Log4j2;

import java.util.Objects;


/**
 * Abstract base class for managing heartbeat information.
 * Implements common functionality for resetting the heartbeat and checking for timeouts.
 */
@Getter
@Accessors(fluent=true)
@Log4j2
public abstract class AbstractHeartbeatInfo implements IHeartbeatInfo {
  /**
   * The duration after which a timeout is considered, in milliseconds.
   */
  private final long timeoutAfter;

  /**
   * The Unix timestamp of the latest action.
   */
  private long latestAction;

  /**
   * Constructs an AbstractHeartbeatInfo with the specified timeout duration.
   *
   * @param timeoutAfter the duration in milliseconds after which a timeout is considered.
   * @throws IndexOutOfBoundsException if timeoutAfter is negative or exceeds Long.MAX_VALUE.
   */
  protected AbstractHeartbeatInfo(final long timeoutAfter) {
    // Check if timeoutAfter is within valid range
    Objects.checkIndex(timeoutAfter, Long.MAX_VALUE);
    this.timeoutAfter = timeoutAfter;

    this.reset();
    log.info("Initialized heartbeat with delay={}ms.", timeoutAfter);
  }

  /**
   * Resets the latest action timestamp to the current time.
   */
  @Override
  public void reset() {
    this.latestAction = System.currentTimeMillis();
  }

  /**
   * Checks if the heartbeat has timed out.
   *
   * @return true if the current time exceeds the latest action time plus the timeout duration, false otherwise.
   */
  @Override
  public boolean timeout() {
    return !(System.currentTimeMillis()-this.latestAction>this.timeoutAfter);
  }
}
