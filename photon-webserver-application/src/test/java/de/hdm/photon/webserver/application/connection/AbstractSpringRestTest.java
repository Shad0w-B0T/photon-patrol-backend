package de.hdm.photon.webserver.application.connection;


import de.hdm.photon.webserver.application.environment.AbstractSpringTest;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest(webEnvironment=SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class AbstractSpringRestTest extends AbstractSpringTest implements IRequest {

  private final RestHandler restHandler;

  public AbstractSpringRestTest(@Nullable final String baseUrl) {
    this.restHandler = new RestHandler(baseUrl);
  }

  @Test
  @DisplayName("Check web client.")
  public void webClient() {
    Assertions.assertNotNull(this.restHandler);
  }

  @Override
  public <RESPONSE> @NotNull RESPONSE get(@Nullable String apiEndpoint,
                                          @Nullable Class<RESPONSE> responseClass) {
    return this.restHandler.get(apiEndpoint, responseClass);
  }

  @Override
  public <RESPONSE> @NotNull RESPONSE put(@Nullable String apiEndpoint,
                                          @Nullable Class<RESPONSE> responseClass) {
    return this.restHandler.get(apiEndpoint, responseClass);
  }

  @Override
  public <RESPONSE, TYPE> @NotNull RESPONSE put(@Nullable String apiEndpoint,
                                                @Nullable Class<RESPONSE> responseClass,
                                                @Nullable TYPE type) {
    return this.restHandler.put(apiEndpoint, responseClass, type);
  }

  @Override
  public <RESPONSE> @NotNull RESPONSE post(@Nullable String apiEndpoint,
                                           @Nullable Class<RESPONSE> responseClass) {
    return this.restHandler.post(apiEndpoint, responseClass);
  }

  @Override
  public <RESPONSE, TYPE> @NotNull RESPONSE post(@Nullable String apiEndpoint,
                                                 @Nullable Class<RESPONSE> responseClass,
                                                 @Nullable TYPE type) {
    return this.restHandler.post(apiEndpoint, responseClass, type);
  }

  @Override
  public <RESPONSE> @NotNull RESPONSE delete(@Nullable String apiEndpoint,
                                             @Nullable Class<RESPONSE> responseClass) {
    return this.restHandler.delete(apiEndpoint, responseClass);
  }
}
