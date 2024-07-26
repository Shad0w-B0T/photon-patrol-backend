package de.hdm.photon.queue.logic.connection;

import com.rabbitmq.client.Channel;
import de.hdm.photon.queue.logic.config.IRabbitContext;
import de.hdm.photon.schema.exception.RabbitConnectionException;
import org.jetbrains.annotations.Nullable;


/**
 * A concrete implementation of {@link AbstractRabbitSubscriber} for subscribing to RabbitMQ messages.
 */
public final class RabbitSubscriber extends AbstractRabbitSubscriber {
  /**
   * Constructs a RabbitSubscriber with the specified channel, context, and acknowledgment setting.
   *
   * @param channel       the RabbitMQ channel. This value can be null.
   * @param rabbitContext the RabbitMQ context. This value can be null.
   * @param ack           a boolean indicating whether message acknowledgments are enabled.
   * @throws RabbitConnectionException if there is an error initializing the subscriber.
   */
  public RabbitSubscriber(@Nullable Channel channel,
                          @Nullable IRabbitContext rabbitContext,
                          boolean ack) throws RabbitConnectionException {
    super(channel, rabbitContext, ack);
  }
}
