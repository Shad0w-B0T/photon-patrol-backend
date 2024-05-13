package de.hdm.photon.queue.logic.config;

import org.jetbrains.annotations.Nullable;


/**
 * This record represents an immutable context for a RabbitMQ exchange, including the exchange name, channel name, and key name.
 * It implements the IRabbitContext interface.
 *
 * @param exchangeName The name of the exchange. Can be null.
 * @param channelName  The name of the channel. Can be null.
 * @param keyName      The key name. Can be null.
 */
public record ImmutableRabbitContext(@Nullable String exchangeName,
                                     @Nullable String channelName,
                                     @Nullable String keyName
) implements IRabbitContext {
}
