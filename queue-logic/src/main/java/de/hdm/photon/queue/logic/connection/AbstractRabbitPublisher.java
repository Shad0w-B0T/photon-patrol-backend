package de.hdm.photon.queue.logic.connection;

import com.rabbitmq.client.Channel;
import de.hdm.photon.queue.logic.config.IRabbitContext;
import de.hdm.photon.schema.exception.connection.RabbitConnectionException;
import lombok.extern.log4j.Log4j2;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.Objects;


/**
 * Abstract base class for RabbitMQ publishers.
 * Implements common functionality for publishing messages to RabbitMQ.
 */
@Log4j2
public abstract class AbstractRabbitPublisher extends AbstractRabbitPartisan implements IRabbitPublisher {
  /**
   * Constructs an AbstractRabbitPublisher with the specified channel and context.
   *
   * @param channel       the RabbitMQ channel. This value can be null.
   * @param rabbitContext the RabbitMQ context. This value can be null.
   */
  public AbstractRabbitPublisher(@Nullable Channel channel,
                                 @Nullable IRabbitContext rabbitContext) {
    super(channel, rabbitContext);
  }

  /**
   * Publishes a message to RabbitMQ.
   *
   * @param content the message content as a byte array. This value can be null.
   * @return the current instance of {@link IRabbitPublisher}, allowing for method chaining.
   * @throws RabbitConnectionException if there is an error during publishing.
   */
  @Override
  public @NotNull IRabbitPublisher publish(byte @Nullable [] content) throws RabbitConnectionException {
    // Null check
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
