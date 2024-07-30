package de.hdm.photon.webserver.application.connection;



import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import test.de.hdm.photon.AbstractSpringTest;


/**
 * {@code AbstractSpringRestTest} is an abstract class for integration testing of Spring Boot applications with REST endpoints.
 * It extends {@link AbstractSpringTest} and implements {@link IRequest}. This class sets up a {@link RestHandler}
 * instance to perform REST operations in the test environment.
 * <p>
 * The class is annotated with {@code @SpringBootTest} to start the Spring Boot application context for testing,
 * with a random port assigned for the web environment.
 * </p>
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class AbstractSpringRestTest extends AbstractSpringTest implements IRequest {

  private final RestHandler restHandler;

  /**
   * Constructs a new {@code AbstractSpringRestTest} instance with the specified base URL.
   * Initializes a {@link RestHandler} with the provided base URL for making REST requests in the test environment.
   *
   * @param baseUrl the base URL to be used by the {@link RestHandler}; may be {@code null}
   */
  public AbstractSpringRestTest(@Nullable final String baseUrl) {
    this.restHandler = new RestHandler(baseUrl);
  }

  /**
   * Tests the initialization of the {@link RestHandler} instance.
   * Asserts that the {@code restHandler} is not {@code null} to confirm proper setup.
   */
  @Test
  @DisplayName("Check web client.")
  public void webClient() {
    Assertions.assertNotNull(this.restHandler, "RestHandler should not be null");
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <RESPONSE> @NotNull RESPONSE get(@Nullable String apiEndpoint,
                                          @Nullable Class<RESPONSE> responseClass) {
    return this.restHandler.get(apiEndpoint, responseClass);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <RESPONSE> @NotNull RESPONSE put(@Nullable String apiEndpoint,
                                          @Nullable Class<RESPONSE> responseClass) {
    return this.restHandler.put(apiEndpoint, responseClass);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <RESPONSE, TYPE> @NotNull RESPONSE put(@Nullable String apiEndpoint,
                                                @Nullable Class<RESPONSE> responseClass,
                                                @Nullable TYPE type) {
    return this.restHandler.put(apiEndpoint, responseClass, type);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <RESPONSE> @NotNull RESPONSE post(@Nullable String apiEndpoint,
                                           @Nullable Class<RESPONSE> responseClass) {
    return this.restHandler.post(apiEndpoint, responseClass);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <RESPONSE, TYPE> @NotNull RESPONSE post(@Nullable String apiEndpoint,
                                                 @Nullable Class<RESPONSE> responseClass,
                                                 @Nullable TYPE type) {
    return this.restHandler.post(apiEndpoint, responseClass, type);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <RESPONSE> @NotNull RESPONSE delete(@Nullable String apiEndpoint,
                                             @Nullable Class<RESPONSE> responseClass) {
    return this.restHandler.delete(apiEndpoint, responseClass);
  }
}
