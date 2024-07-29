package de.hdm.photon.webserver.application.connection;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


public interface IRequest {

  <RESPONSE> @NotNull RESPONSE get(@Nullable String apiEndpoint,
                                   @Nullable Class<RESPONSE> responseClass);

  <RESPONSE> @NotNull RESPONSE put(@Nullable String apiEndpoint,
                                   @Nullable Class<RESPONSE> responseClass);

  <RESPONSE, TYPE> @NotNull RESPONSE put(@Nullable String apiEndpoint,
                                         @Nullable Class<RESPONSE> responseClass,
                                         @Nullable TYPE type);

  <RESPONSE> @NotNull RESPONSE post(@Nullable String apiEndpoint,
                                    @Nullable Class<RESPONSE> responseClass);

  <RESPONSE, TYPE> @NotNull RESPONSE post(@Nullable String apiEndpoint,
                                          @Nullable Class<RESPONSE> responseClass,
                                          @Nullable TYPE type);

  <RESPONSE> @NotNull RESPONSE delete(@Nullable String apiEndpoint,
                                      @Nullable Class<RESPONSE> responseClass);
}

