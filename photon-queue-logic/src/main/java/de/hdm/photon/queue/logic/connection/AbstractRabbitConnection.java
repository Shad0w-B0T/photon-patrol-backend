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

/**
 * Abstract base class for managing RabbitMQ connections and channels.
 *
 * <p>This abstract class provides methods to establish a connection to RabbitMQ, create and manage
 * channels, and interact with RabbitMQ contexts. It implements {@link IRabbitConnection} and uses
 * a {@link ConnectionFactory} to manage the RabbitMQ connection.</p>
 *
 * <p>The class is equipped with logging for various connection and channel operations, and handles
 * exceptions related to RabbitMQ operations by throwing a {@link RabbitConnectionException}.</p>
 *
 * @see IRabbitConnection
 * @see RabbitConnectionException
 * @see ConnectionFactory
 */
@Log4j2
public abstract class AbstractRabbitConnection implements IRabbitConnection {

  private final @NotNull ConnectionFactory connectionFactory;
  private @Nullable Connection connection;
  private @Nullable Channel channel;
  private @Nullable IRabbitContext rabbitContext;

  /**
   * Constructs a new {@code AbstractRabbitConnection} using individual connection parameters.
   *
   * <p>This constructor sets up the connection factory with the provided parameters and initializes
   * the RabbitMQ context.</p>
   *
   * @param user            The username for RabbitMQ authentication. Cannot be null.
   * @param password        The password for RabbitMQ authentication. Cannot be null.
   * @param virtualHost     The virtual host for RabbitMQ. Cannot be null.
   * @param host            The host address of the RabbitMQ server. Cannot be null.
   * @param port            The port number of the RabbitMQ server.
   * @param rabbitContext   The RabbitMQ context to use. Can be null.
   * @throws RabbitConnectionException If an error occurs while setting up the connection.
   */
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
   * Constructs a new {@code AbstractRabbitConnection} using a URI.
   *
   * <p>This constructor sets up the connection factory using the provided URI and initializes
   * the RabbitMQ context.</p>
   *
   * @param uri             The URI for RabbitMQ connection. Cannot be null.
   * @param rabbitContext   The RabbitMQ context to use. Can be null.
   * @throws Exception If an error occurs while setting up the connection.
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
  /**
   * Default constructor that initializes the connection factory.
   */
  private AbstractRabbitConnection() {
    //Create base factory
    this.connectionFactory = new ConnectionFactory();
  }

  @Override
  public @NotNull Connection connection() throws RabbitConnectionException {
    //create new if absent
    if (this.connection == null || !this.connection.isOpen()) {
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
   * Establishes the RabbitMQ context, including channel creation, exchange declaration,
   * queue declaration, and binding.
   *
   * @param rabbitContext The RabbitMQ context to establish. Can be null.
   * @return {@code true} if the context was successfully established; {@code false} otherwise.
   * @throws RabbitConnectionException If an error occurs while establishing the context.
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
      if (!rabbitContext.exchangeName().startsWith("amq")) {
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
