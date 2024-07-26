package de.hdm.photon.queue.logic.connection;


import com.rabbitmq.client.Channel;
import de.hdm.photon.queue.logic.config.IRabbitContext;
import org.jetbrains.annotations.Nullable;


/**
 * A concrete implementation of {@link AbstractRabbitPublisher} for publishing messages to RabbitMQ.
 */
public final class RabbitPublisher extends AbstractRabbitPublisher {

  /**
   * Constructs a RabbitPublisher with the specified channel and context.
   *
   * @param channel       the RabbitMQ channel. This value can be null.
   * @param rabbitContext the RabbitMQ context. This value can be null.
   */
  public RabbitPublisher(@Nullable Channel channel,
                         @Nullable IRabbitContext rabbitContext) {
    super(channel, rabbitContext);
  }
}
