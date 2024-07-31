package test.de.hdm.photon.webserver.logic.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
public class TestConfig {

  /**
   * Configures the PasswordEncoder bean which encodes passwords using the BCrypt hashing function.
   *
   * @return the configured PasswordEncoder instance.
   */
  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

}
