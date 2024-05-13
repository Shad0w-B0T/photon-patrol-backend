package de.hdm.photon.queue.logic.connection;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import de.hdm.photon.schema.exception.connection.RabbitConnectionException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;


public abstract class AbstractRabbitConnection implements IRabbitConnection {

  private final @NotNull ConnectionFactory connectionFactory;
  private @Nullable Connection connection;

  public AbstractRabbitConnection(@Nullable final String user,
                                  @Nullable final String password,
                                  @Nullable final String virtualHost,
                                  @Nullable final String host,
                                  final int port) {
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
  }

  /**
   * Create with uri
   */
  public AbstractRabbitConnection(@Nullable final String uri) throws Exception {
    //Create base factory ->
    this();

    //Null check
    Objects.requireNonNull(uri);

    //Define parameters of factory.
    this.connectionFactory.setUri(uri);
  }

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
}
