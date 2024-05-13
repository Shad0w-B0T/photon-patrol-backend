package de.hdm.photon.queue.logic.connection;

import org.jetbrains.annotations.Nullable;


public final class RabbitConnection extends AbstractRabbitConnection {

  public RabbitConnection(@Nullable String uri) throws Exception {
    super(uri);
  }
}
