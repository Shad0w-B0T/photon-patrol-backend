package test.de.hdm.photon.pseudo.config;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import test.de.hdm.photon.pseudo.task.PublishTask;

import java.util.Timer;


@Configuration
public class TaskConfig {

  @Autowired
  private PublishTask publishTask;

  @Bean
  public @NotNull Timer timer(@Value("${application.system.delay}") final long delay) {
    final Timer timer = new Timer();
    timer.schedule(this.publishTask, delay, delay);
    return timer;
  }

}
