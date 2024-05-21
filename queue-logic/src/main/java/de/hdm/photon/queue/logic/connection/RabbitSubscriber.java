package de.hdm.photon.queue.logic.connection;

import com.rabbitmq.client.Channel;
import de.hdm.photon.queue.logic.config.IRabbitContext;
import de.hdm.photon.schema.exception.connection.RabbitConnectionException;
import org.jetbrains.annotations.Nullable;


public final class RabbitSubscriber extends AbstractRabbitSubscriber{
  public RabbitSubscriber(@Nullable Channel channel,
                          @Nullable IRabbitContext rabbitContext,
                          boolean ack) throws RabbitConnectionException {
    super(channel, rabbitContext, ack);
  }
}
