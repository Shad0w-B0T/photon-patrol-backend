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
   * Retrieves the name of the channel associated with this context.
   *
   * @return The name of the channel.
   */
  @NotNull
  String channelName();

  /**
   * Retrieves the key name associated with this context.
   *
   * @return The key name.
   */
  @NotNull
  String keyName();
}

