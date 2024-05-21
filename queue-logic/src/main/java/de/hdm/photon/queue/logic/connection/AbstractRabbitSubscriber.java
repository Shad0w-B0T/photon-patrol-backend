package de.hdm.photon.queue.logic.connection;

import com.rabbitmq.client.Channel;
import de.hdm.photon.queue.logic.config.IRabbitContext;
import de.hdm.photon.schema.exception.connection.RabbitConnectionException;
import lombok.extern.log4j.Log4j2;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;


@Log4j2
public abstract class AbstractRabbitSubscriber extends AbstractRabbitPartisan implements IRabbitSubscriber {

  private final @NotNull List<Consumer<byte @NotNull []>> consumerList;
  private final boolean ack;

  public AbstractRabbitSubscriber(@Nullable Channel channel,
                                  @Nullable IRabbitContext rabbitContext, boolean ack) throws RabbitConnectionException {
    super(channel, rabbitContext);
    this.ack = ack;
    this.consumerList = new ArrayList<>();
    this.init();
  }

  private void init() throws RabbitConnectionException {
    try {
      final String tag = this.channel.basicConsume(this.rabbitContext.queueName(), (consumerTag, message) -> {
        log.debug("Received data length={}.", message.getBody().length);

        //Accept content.
        this.consumerList.forEach(consumer -> consumer.accept(message.getBody()));

        //Acknowledge message.
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

  @Override
  public @NotNull IRabbitSubscriber subscribe(@Nullable Consumer<byte @Nullable []> content) {
    //Null check
    Objects.requireNonNull(content);

    this.consumerList.add(content);
    log.info("Added rabbit consumer to subscriber.");

    return this;
  }
}
