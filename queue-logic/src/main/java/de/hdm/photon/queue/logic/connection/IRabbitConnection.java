package de.hdm.photon.queue.logic.connection;

import com.rabbitmq.client.Connection;
import de.hdm.photon.schema.exception.connection.RabbitConnectionException;
import org.jetbrains.annotations.NotNull;


/**
 * The {@code IRabbitConnection} interface defines methods for managing RabbitMQ connections.
 * Implementations of this interface provide functionality to establish and manage connections to RabbitMQ.
 *
 * <p>
 * Implementations should provide a non-null connection object via the {@link #connection()} method.
 * If any exception occurs during the connection process, it should be wrapped in a {@link RabbitConnectionException} and thrown.
 * </p>
 */
public interface IRabbitConnection {
  /**
   * Returns the RabbitMQ connection.
   *
   * @return a non-null {@link Connection} object representing the RabbitMQ connection.
   * @throws RabbitConnectionException if an error occurs while establishing or retrieving the RabbitMQ connection.
   */
  @NotNull
  Connection connection() throws RabbitConnectionException;

  /**
   * Provides an instance of {@link IRabbitPublisher}.
   *
   * @return an instance of {@link IRabbitPublisher}.
   * @throws RabbitConnectionException if there is an error creating the publisher.
   */
  @NotNull IRabbitPublisher publisher() throws RabbitConnectionException;

  /**
   * Provides an instance of {@link IRabbitSubscriber}.
   *
   * @param ack a boolean indicating whether message acknowledgments are enabled.
   * @return an instance of {@link IRabbitSubscriber}.
   * @throws RabbitConnectionException if there is an error creating the subscriber.
   */
  @NotNull IRabbitSubscriber subscriber(final boolean ack) throws RabbitConnectionException;
}
