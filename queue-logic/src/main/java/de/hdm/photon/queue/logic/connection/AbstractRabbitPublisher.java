package de.hdm.photon.queue.logic.connection;

import com.rabbitmq.client.Channel;
import de.hdm.photon.queue.logic.config.IRabbitContext;
import de.hdm.photon.schema.exception.connection.RabbitConnectionException;
import lombok.extern.log4j.Log4j2;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.Objects;


@Log4j2
public abstract class AbstractRabbitPublisher extends AbstractRabbitPartisan implements IRabbitPublisher {

  public AbstractRabbitPublisher(@Nullable Channel channel,
                                 @Nullable IRabbitContext rabbitContext) {
    super(channel, rabbitContext);
  }

  @Override
  public @NotNull IRabbitPublisher publish(byte @Nullable [] content) throws RabbitConnectionException {
    //Null check
    Objects.requireNonNull(content);

    try {
      this.channel.basicPublish(this.rabbitContext.exchangeName(), this.rabbitContext.keyName(), null, content);

      log.debug("Published data to exchange={} and key={}.",
          this.rabbitContext.exchangeName(),
          this.rabbitContext.keyName());
    } catch (final IOException exception) {
      throw new RabbitConnectionException("Error while publishing data.", exception);
    }

    return this;
  }
}
