package de.hdm.photon.queue.logic.connection;

import de.hdm.photon.schema.exception.connection.RabbitConnectionException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


/**
 * Interface representing a RabbitMQ publisher.
 */
public interface IRabbitPublisher {
  /**
   * Publishes a message to RabbitMQ.
   *
   * @param content the message content as a byte array. This value can be null.
   * @return the current instance of {@link IRabbitPublisher}, allowing for method chaining.
   * @throws RabbitConnectionException if there is an error during publishing.
   */
  @NotNull IRabbitPublisher publish(byte @Nullable [] content) throws RabbitConnectionException;
}
