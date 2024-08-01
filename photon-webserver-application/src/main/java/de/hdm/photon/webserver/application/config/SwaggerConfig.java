package de.hdm.photon.webserver.application.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;

import io.swagger.v3.oas.models.info.Info;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * Configuration class for Swagger/OpenAPI documentation.
 */
@Configuration
public class SwaggerConfig {

  /**
   * Configures the OpenAPI documentation with custom title, description, and version.
   *
   * @param apiTitle       The title of the API, retrieved from application properties.
   * @param apiDescription The description of the API, retrieved from application properties.
   * @param apiVersion     The version of the API, retrieved from application properties.
   * @return An instance of {@link OpenAPI} configured with the specified title, description, and version.
   */
  @Bean
  public OpenAPI customOpenAPI(
      @Value("${study.web.swagger.title}") @NotNull final String apiTitle,
      @Value("${study.web.swagger.description}") @NotNull final String apiDescription,
      @Value("${study.web.swagger.version}") @NotNull final String apiVersion
  ) {
    return new OpenAPI()
        // Create empty components.
        .components(new Components())
        // Set info.
        .info(new Info()
            .title(apiTitle)
            .description(apiDescription)
            .version(apiVersion));
  }
}
