package de.hdm.photon.queue.logic.connection;

import com.rabbitmq.client.Channel;
import de.hdm.photon.queue.logic.config.IRabbitContext;
import de.hdm.photon.schema.exception.RabbitConnectionException;
import lombok.extern.log4j.Log4j2;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;


/**
 * Abstract base class for RabbitMQ subscribers.
 * Implements common functionality for subscribing to RabbitMQ messages and handling acknowledgments.
 */
@Log4j2
public abstract class AbstractRabbitSubscriber extends AbstractRabbitPartisan implements IRabbitSubscriber {
  /**
   * List of consumers that process the message content.
   */
  private final @NotNull List<Consumer<byte @NotNull []>> consumerList;

  /**
   * Flag indicating whether acknowledgments are enabled.
   */
  private final boolean ack;

  /**
   * Constructs an AbstractRabbitSubscriber with the specified channel, context, and acknowledgment setting.
   *
   * @param channel       the RabbitMQ channel. This value can be null.
   * @param rabbitContext the RabbitMQ context. This value can be null.
   * @param ack           a boolean indicating whether message acknowledgments are enabled.
   * @throws RabbitConnectionException if there is an error initializing the subscriber.
   */
  public AbstractRabbitSubscriber(@Nullable Channel channel,
                                  @Nullable IRabbitContext rabbitContext,
                                  boolean ack) throws RabbitConnectionException {
    super(channel, rabbitContext);
    this.ack = ack;
    this.consumerList = new ArrayList<>();
    this.init();
  }

  /**
   * Initializes the subscriber by setting up message consumption from the RabbitMQ channel.
   *
   * @throws RabbitConnectionException if there is an error during initialization.
   */
  private void init() throws RabbitConnectionException {
    try {
      final String tag = this.channel.basicConsume(this.rabbitContext.queueName(), (consumerTag, message) -> {
        log.debug("Received data length={}.", message.getBody().length);

        // Accept content.
        this.consumerList.forEach(consumer -> consumer.accept(message.getBody()));

        // Acknowledge message.
        if (this.ack) {
          this.channel.basicAck(message.getEnvelope().getDeliveryTag(), false);
          log.debug("Acknowledged tag={}.", message.getEnvelope().getDeliveryTag());
        }
      }, (consumerTag, sig) -> {
      });

      log.info("Subscribed to channel with tag={}.", tag);
    } catch (final IOException exception) {
      throw new RabbitConnectionException("Error while subscribing, channel.", exception);
    }
  }

  /**
   * Subscribes a consumer to process RabbitMQ messages.
   *
   * @param content a {@link Consumer} that processes the message content as a byte array. This value can be null.
   * @return the current instance of {@link IRabbitSubscriber}, allowing for method chaining.
   */
  @Override
  public @NotNull IRabbitSubscriber subscribe(@Nullable Consumer<byte @Nullable []> content) {
    // Null check
    Objects.requireNonNull(content);

    this.consumerList.add(content);
    log.info("Added rabbit consumer to subscriber.");

    return this;
  }
}
