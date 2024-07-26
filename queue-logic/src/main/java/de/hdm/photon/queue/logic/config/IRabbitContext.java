package de.hdm.photon.queue.logic.config;

import org.jetbrains.annotations.NotNull;


/**
 * This interface represents the context for a RabbitMQ exchange, including the exchange name, channel name, and key name.
 */
public interface IRabbitContext {
  /**
   * Retrieves the name of the exchange associated with this context.
   *
   * @return The name of the exchange.
   */
  @NotNull
  String exchangeName();

  /**
   * Retrieves the name of the queue associated with this context.
   *
   * @return The name of the queue.
   */
  @NotNull
  String queueName();

  /**
   * Retrieves the key name associated with this context.
   *
   * @return The key name.
   */
  @NotNull
  String keyName();
}

