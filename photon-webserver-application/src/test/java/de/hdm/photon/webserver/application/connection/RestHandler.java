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


public final class RestHandler implements IRequest {

  private final @NotNull WebClient webClient;

  public RestHandler(@Nullable String baseUrl) {
    Objects.requireNonNull(baseUrl);

    /*
     * Create client instance.
     */
    final HttpClient httpClient = HttpClient.create()
        //Cancel redirect
        .followRedirect(false);

    this.webClient = WebClient.builder()
        /*
         * Set base url.
         */
        .baseUrl(baseUrl)

        /*
         * Default headers.
         */
        .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        .defaultHeader(HttpHeaders.ACCEPT_CHARSET)
        //Use client as default header
        .clientConnector(new ReactorClientHttpConnector(httpClient))
        .build();
  }

  @Override
  public <RESPONSE> @NotNull RESPONSE get(@Nullable String apiEndpoint,
                                          @Nullable Class<RESPONSE> responseClass) {
    return this.response(apiEndpoint, HttpMethod.GET, responseClass, null);
  }

  @Override
  public <RESPONSE> @NotNull RESPONSE put(@Nullable String apiEndpoint,
                                          @Nullable Class<RESPONSE> responseClass) {
    return this.response(apiEndpoint, HttpMethod.PUT, responseClass, null);
  }

  @Override
  public <RESPONSE, TYPE> @NotNull RESPONSE put(@Nullable String apiEndpoint,
                                                @Nullable Class<RESPONSE> responseClass,
                                                @Nullable TYPE type) {
    return this.response(apiEndpoint, HttpMethod.PUT, responseClass, type);
  }

  @Override
  public <RESPONSE> @NotNull RESPONSE post(@Nullable String apiEndpoint,
                                           @Nullable Class<RESPONSE> responseClass) {
    return this.response(apiEndpoint, HttpMethod.POST, responseClass, null);
  }

  @Override
  public <RESPONSE, TYPE> @NotNull RESPONSE post(@Nullable String apiEndpoint,
                                                 @Nullable Class<RESPONSE> responseClass,
                                                 @Nullable TYPE type) {
    return this.response(apiEndpoint, HttpMethod.POST, responseClass, type);
  }

  @Override
  public <RESPONSE> @NotNull RESPONSE delete(@Nullable String apiEndpoint,
                                             @Nullable Class<RESPONSE> responseClass) {
    return this.response(apiEndpoint, HttpMethod.DELETE, responseClass, null);
  }

  private <RESPONSE, TYPE> RESPONSE response(@Nullable final String apiEndpoint,
                                             @Nullable final HttpMethod httpMethod,
                                             @Nullable final Class<RESPONSE> typeClass,
                                             @Nullable final TYPE type) {
    //Null check
    Objects.requireNonNull(apiEndpoint);
    Objects.requireNonNull(httpMethod);
    Objects.requireNonNull(typeClass);

    final WebClient.UriSpec<WebClient.RequestBodySpec> requestBodySpec = this
        .webClient
        .method(httpMethod);

    final WebClient.RequestBodySpec request = requestBodySpec
        //Url
        .uri(apiEndpoint)
        .acceptCharset(StandardCharsets.UTF_8);

    //If type is present -> set content of request.
    if (type != null) {
      //Insert object will be converted to json.
      request.body(BodyInserters.fromValue(type));
    }

    final RESPONSE response = request.exchangeToMono(clientResponse -> {
          if (clientResponse.statusCode().value() != 200) {
            return clientResponse.createError();
          }
          return clientResponse.bodyToMono(typeClass);
        })

        //Block until response.
        .block();

    //Check
    Assertions.assertNotNull(response);
    Assertions.assertInstanceOf(typeClass, response);

    //No check for java components.
    if (!(response instanceof Map<?, ?> || response instanceof Collection<?>)) {
      //Check fields
      for (final Field declaredField : response.getClass().getDeclaredFields()) {
        try {

          //Check if field is present else throw error.
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
