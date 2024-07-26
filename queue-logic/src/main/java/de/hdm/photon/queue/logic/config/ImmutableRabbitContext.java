package de.hdm.photon.queue.logic.config;

import lombok.Getter;
import lombok.experimental.Accessors;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Objects;


/**
 * This record represents an immutable context for a RabbitMQ exchange, including the exchange name, channel name, and key name.
 * It implements the IRabbitContext interface.
 */
@Component
@Getter
@Accessors(fluent=true)
public final class ImmutableRabbitContext implements IRabbitContext {
  private final @NotNull String exchangeName;
  private final @NotNull String queueName;
  private final @NotNull String keyName;

  /**
   * @param exchangeName The name of the exchange. Can be null.
   * @param queueName    The name of the queue. Can be null.
   * @param keyName      The key name. Can be null.
   */
  @Autowired
  public ImmutableRabbitContext(@Value("${application.rabbit.exchange}") @Nullable final String exchangeName,
                                @Value("${application.rabbit.queue}") @Nullable final String queueName,
                                @Value("${application.rabbit.key}") @Nullable final String keyName) {
    //Null check
    Objects.requireNonNull(exchangeName);
    Objects.requireNonNull(queueName);
    Objects.requireNonNull(keyName);

    this.exchangeName = exchangeName;
    this.queueName = queueName;
    this.keyName = keyName;
  }
}
