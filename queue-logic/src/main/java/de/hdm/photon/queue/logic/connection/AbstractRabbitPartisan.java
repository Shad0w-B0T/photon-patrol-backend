package de.hdm.photon.queue.logic.connection;

import com.rabbitmq.client.Channel;
import de.hdm.photon.queue.logic.config.IRabbitContext;
import lombok.extern.log4j.Log4j2;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;


@Log4j2
public abstract class AbstractRabbitPartisan {
  protected final @NotNull Channel channel;
  protected final @NotNull IRabbitContext rabbitContext;

  public AbstractRabbitPartisan(@Nullable final Channel channel,
                                @Nullable final IRabbitContext rabbitContext) {
    //Null check
    Objects.requireNonNull(channel);
    Objects.requireNonNull(rabbitContext);

    this.channel = channel;
    this.rabbitContext = rabbitContext;

    log.info("Created partisan with context.");
  }
}
