package de.hdm.photon.webserver.application.connection;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


/**
 * Interface that defines methods for making HTTP requests with various methods (GET, PUT, POST, DELETE).
 * The methods handle different types of responses and optional request payloads.
 */
public interface IRequest {
  /**
   * Performs an HTTP GET request to the specified API endpoint.
   *
   * @param apiEndpoint   the API endpoint to send the GET request to; may be {@code null}
   * @param responseClass the class type of the expected response; may be {@code null}
   * @param <RESPONSE>    the type of the response object
   * @return the response of type {@code RESPONSE}; never {@code null}
   * @throws NullPointerException if {@code responseClass} is {@code null}
   */
  <RESPONSE> @NotNull RESPONSE get(@Nullable String apiEndpoint,
                                   @Nullable Class<RESPONSE> responseClass);

  /**
   * Performs an HTTP PUT request to the specified API endpoint.
   *
   * @param apiEndpoint   the API endpoint to send the PUT request to; may be {@code null}
   * @param responseClass the class type of the expected response; may be {@code null}
   * @param <RESPONSE>    the type of the response object
   * @return the response of type {@code RESPONSE}; never {@code null}
   * @throws NullPointerException if {@code responseClass} is {@code null}
   */
  <RESPONSE> @NotNull RESPONSE put(@Nullable String apiEndpoint,
                                   @Nullable Class<RESPONSE> responseClass);

  /**
   * Performs an HTTP PUT request to the specified API endpoint with a request payload.
   *
   * @param apiEndpoint   the API endpoint to send the PUT request to; may be {@code null}
   * @param responseClass the class type of the expected response; may be {@code null}
   * @param type          the request payload; may be {@code null}
   * @param <RESPONSE>    the type of the response object
   * @param <TYPE>        the type of the request payload
   * @return the response of type {@code RESPONSE}; never {@code null}
   * @throws NullPointerException if {@code responseClass} is {@code null}
   */
  <RESPONSE, TYPE> @NotNull RESPONSE put(@Nullable String apiEndpoint,
                                         @Nullable Class<RESPONSE> responseClass,
                                         @Nullable TYPE type);

  /**
   * Performs an HTTP POST request to the specified API endpoint.
   *
   * @param apiEndpoint   the API endpoint to send the POST request to; may be {@code null}
   * @param responseClass the class type of the expected response; may be {@code null}
   * @param <RESPONSE>    the type of the response object
   * @return the response of type {@code RESPONSE}; never {@code null}
   * @throws NullPointerException if {@code responseClass} is {@code null}
   */
  <RESPONSE> @NotNull RESPONSE post(@Nullable String apiEndpoint,
                                    @Nullable Class<RESPONSE> responseClass);

  /**
   * Performs an HTTP POST request to the specified API endpoint with a request payload.
   *
   * @param apiEndpoint   the API endpoint to send the POST request to; may be {@code null}
   * @param responseClass the class type of the expected response; may be {@code null}
   * @param type          the request payload; may be {@code null}
   * @param <RESPONSE>    the type of the response object
   * @param <TYPE>        the type of the request payload
   * @return the response of type {@code RESPONSE}; never {@code null}
   * @throws NullPointerException if {@code responseClass} is {@code null}
   */
  <RESPONSE, TYPE> @NotNull RESPONSE post(@Nullable String apiEndpoint,
                                          @Nullable Class<RESPONSE> responseClass,
                                          @Nullable TYPE type);

  /**
   * Performs an HTTP DELETE request to the specified API endpoint.
   *
   * @param apiEndpoint   the API endpoint to send the DELETE request to; may be {@code null}
   * @param responseClass the class type of the expected response; may be {@code null}
   * @param <RESPONSE>    the type of the response object
   * @return the response of type {@code RESPONSE}; never {@code null}
   * @throws NullPointerException if {@code responseClass} is {@code null}
   */
  <RESPONSE> @NotNull RESPONSE delete(@Nullable String apiEndpoint,
                                      @Nullable Class<RESPONSE> responseClass);
}

