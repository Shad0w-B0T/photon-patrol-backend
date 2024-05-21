package de.hdm.photon.queue.logic.connection;


import com.rabbitmq.client.Channel;
import de.hdm.photon.queue.logic.config.IRabbitContext;
import org.jetbrains.annotations.Nullable;


public final class RabbitPublisher extends AbstractRabbitPublisher {
  public RabbitPublisher(@Nullable Channel channel,
                         @Nullable IRabbitContext rabbitContext) {
    super(channel, rabbitContext);
  }
}
