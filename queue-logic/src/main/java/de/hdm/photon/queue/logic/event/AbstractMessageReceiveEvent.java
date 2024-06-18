package de.hdm.photon.queue.logic.event;

import de.hdm.photon.schema.payload.IMessagePayload;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.context.ApplicationEvent;

import java.util.Objects;


/**
 * Abstract base class for message receive events.
 * <p>
 * This class extends {@link ApplicationEvent} and includes an {@link IMessagePayload} that represents the payload of the received message.
 * </p>
 */
@Getter
@Accessors(fluent = true)
public abstract class AbstractMessageReceiveEvent extends ApplicationEvent {

  /**
   * The payload of the received message.
   */
  private final @NotNull IMessagePayload messagePayload;

  /**
   * Constructs a new {@code AbstractMessageReceiveEvent}.
   *
   * @param source the object on which the event initially occurred (never {@code null}).
   * @param messagePayload the payload of the received message. Must not be {@code null}.
   * @throws NullPointerException if {@code messagePayload} is {@code null}.
   * @see ApplicationEvent#ApplicationEvent(Object)
   */
  public AbstractMessageReceiveEvent(@NotNull final Object source,
                                     @Nullable final IMessagePayload messagePayload) {
    super(source);
    // Null check
    Objects.requireNonNull(messagePayload, "messagePayload must not be null");
    this.messagePayload = messagePayload;
  }
}
