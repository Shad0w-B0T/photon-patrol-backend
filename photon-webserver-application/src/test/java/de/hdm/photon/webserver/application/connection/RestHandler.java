package de.hdm.photon.webserver.application.connection;


import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.junit.jupiter.api.Assertions;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;


/**
 * {@code RestHandler} implements the {@link IRequest} interface to handle HTTP requests using a {@link WebClient}.
 * This class is configured to send requests to a specified base URL with default headers and a custom HTTP client.
 */
public final class RestHandler implements IRequest {

  private final @NotNull WebClient webClient;

  /**
   * Constructs a new {@code RestHandler} instance with the specified base URL.
   * This constructor initializes the {@link WebClient} with the given base URL, sets default headers, and configures
   * the HTTP client to not follow redirects.
   *
   * @param baseUrl the base URL for the WebClient; must not be {@code null}
   * @throws NullPointerException if {@code baseUrl} is {@code null}
   */
  public RestHandler(@Nullable String baseUrl) {
    Objects.requireNonNull(baseUrl, "Base URL must not be null");

    /*
     * Create client instance.
     */
    final HttpClient httpClient = HttpClient.create()
        // Cancel redirect
        .followRedirect(false);

    this.webClient = WebClient.builder()
        /*
         * Set base URL.
         */
        .baseUrl(baseUrl)

        /*
         * Default headers.
         */
        .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        .defaultHeader(HttpHeaders.ACCEPT_CHARSET)
        // Use client as default header
        .clientConnector(new ReactorClientHttpConnector(httpClient))
        .build();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <RESPONSE> @NotNull RESPONSE get(@Nullable String apiEndpoint,
                                          @Nullable Class<RESPONSE> responseClass) {
    return this.response(apiEndpoint, HttpMethod.GET, responseClass, null);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <RESPONSE> @NotNull RESPONSE put(@Nullable String apiEndpoint,
                                          @Nullable Class<RESPONSE> responseClass) {
    return this.response(apiEndpoint, HttpMethod.PUT, responseClass, null);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <RESPONSE, TYPE> @NotNull RESPONSE put(@Nullable String apiEndpoint,
                                                @Nullable Class<RESPONSE> responseClass,
                                                @Nullable TYPE type) {
    return this.response(apiEndpoint, HttpMethod.PUT, responseClass, type);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <RESPONSE> @NotNull RESPONSE post(@Nullable String apiEndpoint,
                                           @Nullable Class<RESPONSE> responseClass) {
    return this.response(apiEndpoint, HttpMethod.POST, responseClass, null);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <RESPONSE, TYPE> @NotNull RESPONSE post(@Nullable String apiEndpoint,
                                                 @Nullable Class<RESPONSE> responseClass,
                                                 @Nullable TYPE type) {
    return this.response(apiEndpoint, HttpMethod.POST, responseClass, type);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <RESPONSE> @NotNull RESPONSE delete(@Nullable String apiEndpoint,
                                             @Nullable Class<RESPONSE> responseClass) {
    return this.response(apiEndpoint, HttpMethod.DELETE, responseClass, null);
  }

  /**
   * Sends an HTTP request to the specified API endpoint and processes the response.
   * This method supports various HTTP methods (GET, POST, PUT, etc.) and can handle requests with or without a payload.
   *
   * @param <RESPONSE>  the type of the expected response object
   * @param <TYPE>      the type of the request payload (if any)
   * @param apiEndpoint the API endpoint to send the request to; must not be {@code null}
   * @param httpMethod  the HTTP method to use for the request (e.g., GET, POST, PUT); must not be {@code null}
   * @param typeClass   the class type of the expected response; must not be {@code null}
   * @param type        the request payload to be sent with the request; may be {@code null}
   * @return the response object of type {@code RESPONSE}; never {@code null}
   * @throws NullPointerException if {@code apiEndpoint}, {@code httpMethod}, or {@code typeClass} is {@code null}
   * @throws AssertionError       if the response is {@code null} or not an instance of {@code typeClass}
   * @throws AssertionError       if any field in the response object is {@code null} (except for instances of {@link Map} or {@link Collection})
   * @throws RuntimeException     if the HTTP request fails or if there are issues accessing fields in the response object
   */
  private <RESPONSE, TYPE> RESPONSE response(@Nullable final String apiEndpoint,
                                             @Nullable final HttpMethod httpMethod,
                                             @Nullable final Class<RESPONSE> typeClass,
                                             @Nullable final TYPE type) {
    // Null check
    Objects.requireNonNull(apiEndpoint);
    Objects.requireNonNull(httpMethod);
    Objects.requireNonNull(typeClass);

    final WebClient.UriSpec<WebClient.RequestBodySpec> requestBodySpec = this
        .webClient
        .method(httpMethod);

    final WebClient.RequestBodySpec request = requestBodySpec
        // URL
        .uri(apiEndpoint)
        .acceptCharset(StandardCharsets.UTF_8);

    // If type is present -> set content of request.
    if (type != null) {
      // Insert object will be converted to JSON.
      request.body(BodyInserters.fromValue(type));
    }

    final RESPONSE response = request.exchangeToMono(clientResponse -> {
          if (clientResponse.statusCode().value() != 200) {
            return clientResponse.createError();
          }
          return clientResponse.bodyToMono(typeClass);
        })
        // Block until response.
        .block();

    // Check
    Assertions.assertNotNull(response);
    Assertions.assertInstanceOf(typeClass, response);

    // No check for Java components.
    if (!(response instanceof Map<?, ?> || response instanceof Collection<?>)) {
      // Check fields
      for (final Field declaredField : response.getClass().getDeclaredFields()) {
        try {
          // Check if field is present else throw error.
          declaredField.setAccessible(true);
          Assertions.assertNotNull(declaredField.get(response));
        } catch (final IllegalAccessException exception) {
          Assertions.fail();
        }
      }
    }

    return response;
  }
}
