package de.hdm.photon.webserver.application.security;

import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


/**
 * Security configuration class to define security-related beans and settings.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {
  /**
   * Configures the AuthenticationManager bean which is responsible for handling authentication.
   *
   * @param userDetailsService the UserDetailsService to load user-specific data.
   * @param passwordEncoder    the PasswordEncoder to encode user passwords.
   * @return the configured AuthenticationManager instance.
   */
  @Bean
  public AuthenticationManager authenticationManager(@NotNull final UserDetailsService userDetailsService,
                                                     @NotNull final PasswordEncoder passwordEncoder) {
    final DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();

    authenticationProvider.setUserDetailsService(userDetailsService);
    authenticationProvider.setPasswordEncoder(passwordEncoder);

    return new ProviderManager(authenticationProvider);
  }

  /**
   * Configures the SecurityFilterChain bean which defines the security filter chain.
   *
   * @param httpSecurity the HttpSecurity to configure web-based security for specific HTTP requests.
   * @return the configured SecurityFilterChain instance.
   * @throws Exception if an error occurs during configuration.
   */
  @Bean
  public SecurityFilterChain securityFilterChain(@NotNull final HttpSecurity httpSecurity) throws Exception {
    httpSecurity
        .httpBasic(Customizer.withDefaults())
        .authorizeHttpRequests((authorize) -> authorize
   //         .requestMatchers("/api/v1/**")
    //        .authenticated()
            .anyRequest()
            .permitAll()
        )
        .csrf(AbstractHttpConfigurer::disable);
    return httpSecurity.build();
  }

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