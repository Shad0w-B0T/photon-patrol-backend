package de.hdm.photon.queue.logic.connection;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import de.hdm.photon.queue.logic.config.IRabbitContext;
import de.hdm.photon.schema.exception.RabbitConnectionException;
import lombok.extern.log4j.Log4j2;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.Objects;


@Log4j2
public abstract class AbstractRabbitConnection implements IRabbitConnection {

  private final @NotNull ConnectionFactory connectionFactory;
  private @Nullable Connection connection;
  private @Nullable Channel channel;
  private @Nullable IRabbitContext rabbitContext;

  public AbstractRabbitConnection(@Nullable final String user,
                                  @Nullable final String password,
                                  @Nullable final String virtualHost,
                                  @Nullable final String host,
                                  final int port,
                                  @Nullable final IRabbitContext rabbitContext) throws RabbitConnectionException {
    //Create base factory ->
    this();

    //Null check
    Objects.requireNonNull(user);
    Objects.requireNonNull(password);
    Objects.requireNonNull(virtualHost);
    Objects.requireNonNull(host);

    //Define parameters of factory.
    this.connectionFactory.setUsername(user);
    this.connectionFactory.setPassword(password);
    this.connectionFactory.setVirtualHost(virtualHost);
    this.connectionFactory.setHost(host);
    this.connectionFactory.setPort(port);

    this.context(rabbitContext);
  }

  /**
   * Create with uri
   */
  public AbstractRabbitConnection(@Nullable final String uri,
                                  @Nullable final IRabbitContext rabbitContext) throws Exception {
    //Create base factory ->
    this();

    //Null check
    Objects.requireNonNull(uri);

    //Define parameters of factory.
    this.connectionFactory.setUri(uri);
    this.context(rabbitContext);
  }

  private AbstractRabbitConnection() {
    //Create base factory
    this.connectionFactory = new ConnectionFactory();
  }

  @Override
  public @NotNull Connection connection() throws RabbitConnectionException {
    //create new if absent
    if (this.connection == null || !this.connection.isOpen()) {
      log.info("The connection is initialized because it does not yet exist or is closed.");
      //Create
      try {
        this.connection = this.connectionFactory.newConnection();
      } catch (final Exception exception) {
        //Error with factory.
        throw new RabbitConnectionException("Current rabbitmq connection has an error.");
      }
    }
    return this.connection;
  }

  @Override
  public @NotNull IRabbitPublisher publisher() throws RabbitConnectionException {
    return new RabbitPublisher(this.channel, this.rabbitContext);
  }

  @Override
  public @NotNull IRabbitSubscriber subscriber(boolean ack) throws RabbitConnectionException {
    return new RabbitSubscriber(this.channel, this.rabbitContext, ack);
  }

  /**
   * Establishes a context for a RabbitMQ connection using the provided Rabbit context.
   *
   * @param rabbitContext The RabbitMQ context to establish the connection with. Can be null.
   * @return True if the context is successfully established, false otherwise.
   * @throws RabbitConnectionException If there is an error establishing the RabbitMQ connection.
   */
  private boolean context(@Nullable IRabbitContext rabbitContext) throws RabbitConnectionException {
    //Null check
    Objects.requireNonNull(rabbitContext);
    this.rabbitContext = rabbitContext;

    final Connection connection = this.connection();

    //Create channel if absent.
    if (this.channel == null) {
      try {
        this.channel = connection.createChannel();
        log.info("Created channel to participate in communication.");
      } catch (final IOException exception) {
        log.warn("Error while creating context for rabbit mq.");
        throw new RabbitConnectionException("Error while creating channel.", exception);
      }
    }

    try {
      //Blacklist own runners of rabbit.
      if(!rabbitContext.exchangeName().startsWith("amq")) {
        this.channel.exchangeDeclare(rabbitContext.exchangeName(), "fanout");
      }
      log.info("Found exchange={}.", rabbitContext.exchangeName());
    } catch (final IOException exception) {
      log.warn("Error while creating exchange for rabbit mq.");
      throw new RabbitConnectionException("Error while creating exchange.", exception);
    }

    try {
      this.channel.queueDeclare(rabbitContext.queueName(), true, false, false, null);
      log.info("Found queue={}.", rabbitContext.queueName());
    } catch (final IOException exception) {
      log.warn("Error while creating queue for rabbit mq.");
      throw new RabbitConnectionException("Error while creating queue.", exception);
    }

    try {
      this.channel.queueBind(rabbitContext.queueName(), rabbitContext.exchangeName(), rabbitContext.keyName());
      log.info("Bound exchange={}, queue={} and key={}.",
          rabbitContext.exchangeName(),
          rabbitContext.queueName(),
          rabbitContext.keyName());
    } catch (final IOException exception) {
      log.warn("Error while binding.");
      throw new RabbitConnectionException("Error while binding rabbit parameter.", exception);
    }


    return true;
  }
}
