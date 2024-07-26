package de.hdm.photon.queue.logic.connection;

import de.hdm.photon.queue.logic.config.IRabbitContext;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


/**
 * RabbitConnection is a Spring component that manages a connection to a RabbitMQ server.
 * It extends the AbstractRabbitConnection class.
 */
@Component
public final class RabbitConnection extends AbstractRabbitConnection {

  /**
   * Constructs a new RabbitConnection instance.
   *
   * @param uri the URI of the RabbitMQ server. This value can be null.
   * @throws Exception if an error occurs during connection setup.
   */
  @Autowired
  public RabbitConnection(@Value("${application.rabbit.uri}") @Nullable String uri,
                          @Nullable final IRabbitContext rabbitContext) throws Exception {
    super(uri, rabbitContext);
  }
}
