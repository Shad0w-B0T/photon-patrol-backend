package test.de.hdm.photon.pseudo.config;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import test.de.hdm.photon.pseudo.task.PublishTask;

import java.util.Timer;


/**
 * Configuration class for scheduling tasks with a Timer.
 */
@Configuration
public class TaskConfig {
  @Autowired
  private PublishTask publishTask;

  /**
   * Creates and configures a Timer bean to schedule tasks.
   *
   * @param delay the delay in milliseconds before task is to be executed,
   *              and the period between successive executions.
   * @return a configured {@link Timer} instance
   */
  @Bean
  public @NotNull Timer timer(@Value("${application.system.delay}") final long delay) {
    final Timer timer = new Timer();
    timer.schedule(this.publishTask, delay, delay);
    return timer;
  }
}
