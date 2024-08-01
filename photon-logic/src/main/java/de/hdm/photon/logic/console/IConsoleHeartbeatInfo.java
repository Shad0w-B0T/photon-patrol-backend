package de.hdm.photon.logic.console;

/**
 * Interface representing heartbeat information.
 * It provides methods to access and manage the latest action timestamp,
 * reset the heartbeat, and check for timeout conditions.
 */
public interface IConsoleHeartbeatInfo {
  /**
   * Gets the Unix timestamp of the latest action.
   *
   * @return the Unix timestamp of the latest action.
   */
  long latestAction();

  /**
   * Resets the heartbeat information.
   * This typically involves updating the latest action timestamp to the current time.
   */
  void reset();

  /**
   * Checks if the heartbeat has timed out.
   * The definition of a timeout is implementation-specific.
   *
   * @return true if the heartbeat has timed out, false otherwise.
   */
  boolean timeout();
}
