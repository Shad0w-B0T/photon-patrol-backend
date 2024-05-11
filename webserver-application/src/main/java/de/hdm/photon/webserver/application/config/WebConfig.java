package de.hdm.photon.webserver.application.config;

import de.hdm.photon.webserver.application.config.interceptor.WebInterceptor;
import lombok.extern.log4j.Log4j2;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


/**
 * Configuration class for Spring MVC web application.
 * This class implements the {@link WebMvcConfigurer} interface to customize the behavior of the Spring MVC framework.
 */
@Component
@Log4j2
public final class WebConfig implements WebMvcConfigurer {
  /**
   * The interceptor for logging request processing time.
   */
  @Autowired
  private WebInterceptor webInterceptor;

  /**
   * Configures view controllers to handle requests.
   *
   * @param registry The registry for view controllers.
   */
  @Override
  public void addViewControllers(@NotNull final ViewControllerRegistry registry) {
    registry
        // Define controller pattern
        .addViewController("/")
        // Redirect to default registry
        .setViewName("forward:/index.html");
    log.info("Added view controller for default request path ('/').");
  }

  /**
   * Configures interceptors to be applied to incoming requests.
   *
   * @param registry The registry for interceptors.
   */
  @Override
  public void addInterceptors(@NotNull final InterceptorRegistry registry) {
    registry.addInterceptor(this.webInterceptor);
  }
}
