package de.hdm.photon.queue.logic.connection;

import com.rabbitmq.client.Channel;
import de.hdm.photon.queue.logic.config.IRabbitContext;
import lombok.extern.log4j.Log4j2;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;


/**
 * Abstract base class for RabbitMQ components that require a channel and context.
 */
@Log4j2
public abstract class AbstractRabbitPartisan {
  /**
   * The RabbitMQ channel used for communication.
   */
  protected final @NotNull Channel channel;

  /**
   * The RabbitMQ context containing configuration and state.
   */
  protected final @NotNull IRabbitContext rabbitContext;

  /**
   * Constructs an AbstractRabbitPartisan with the specified channel and context.
   *
   * @param channel       the RabbitMQ channel. This value cannot be null.
   * @param rabbitContext the RabbitMQ context. This value cannot be null.
   */
  public AbstractRabbitPartisan(@Nullable final Channel channel,
                                @Nullable final IRabbitContext rabbitContext) {
    // Null check
    Objects.requireNonNull(channel, "Channel cannot be null");
    Objects.requireNonNull(rabbitContext, "RabbitContext cannot be null");

    this.channel = channel;
    this.rabbitContext = rabbitContext;

    log.info("Created partisan with context.");
  }
}
