package de.hdm.photon.queue.logic.connection;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;


/**
 * Interface representing a RabbitMQ subscriber.
 */
public interface IRabbitSubscriber {
  /**
   * Subscribes to RabbitMQ messages.
   *
   * @param content a {@link Consumer} that processes the message content as a byte array. This value can be null.
   * @return the current instance of {@link IRabbitSubscriber}, allowing for method chaining.
   */
  @NotNull IRabbitSubscriber subscribe(@Nullable final Consumer<byte @Nullable []> content);
}
