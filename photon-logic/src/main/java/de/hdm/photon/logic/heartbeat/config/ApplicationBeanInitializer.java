package de.hdm.photon.logic.heartbeat.config;

import de.hdm.photon.logic.heartbeat.HeartbeatTask;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Timer;


/**
 * Configuration class for initializing application beans.
 */
@Configuration
public class ApplicationBeanInitializer {
  @Autowired
  private HeartbeatTask heartbeatTask;

  /**
   * Creates and configures a {@link Timer} bean.
   * The timer is scheduled to execute the {@link HeartbeatTask} every second.
   *
   * @return a configured {@link Timer} instance.
   */
  @Bean
  public @NotNull Timer timer() {
    final Timer timer = new Timer();

    // Schedule the procurerTimerTask to run every second (1000 milliseconds)
    timer.schedule(this.heartbeatTask, 1000L, 1000L);

    return timer;
  }
}
