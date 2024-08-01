package de.hdm.photon.logic.console;


import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.TimerTask;


/**
 * HeartbeatTask is a scheduled task that checks the heartbeat status and logs the time elapsed
 * since the last action if the system is still active.
 */
@Component
@Log4j2
public final class ConsoleHeartbeatTask extends TimerTask {
  /**
   * The HeartbeatInfo instance used to check the timeout status and reset the heartbeat.
   */
  @Autowired
  private ConsoleHeartbeatInfo heartbeatInfo;

  /**
   * Executes the scheduled task.
   * If the heartbeat has timed out, the method returns immediately.
   * Otherwise, it logs the time since the last action and resets the heartbeat.
   */
  @Override
  @Scheduled(fixedDelay = 1000, initialDelay = 1000)
  public void run() {
    if (this.heartbeatInfo.timeout()) {
      return;
    }

    // Calculate the duration between the current time and the latest action time
    final Duration duration = Duration.ofMillis(System.currentTimeMillis() - this.heartbeatInfo.latestAction());

    // Extract hours, minutes, and seconds from the duration
    final long hours = duration.toHours();
    final long minutes = duration.toMinutes() % 60;
    final long seconds = duration.getSeconds() % 60;

    log.info("Console Heartbeat, the system is still active. Last action was {} hours {} minutes and {} seconds ago.",
        hours,
        minutes,
        seconds);

    // Reset the heartbeat
    this.heartbeatInfo.reset();
  }
}
