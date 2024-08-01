package de.hdm.photon.queue.logic.event;

import de.hdm.photon.schema.payload.IMessagePayload;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.context.ApplicationEvent;

/**
 * {@code MessageReceiveEvent} represents an event that is triggered when a message is received.
 * This event encapsulates the payload of the received message and the source of the event.
 * <p>
 * It extends {@link AbstractMessageReceiveEvent} and is used to handle message receipt within
 * the application. This class is primarily used in event-driven architectures where messages are
 * processed as events.
 * </p>
 */
public final class MessageReceiveEvent extends AbstractMessageReceiveEvent {
  /**
   * Constructs a new {@code AbstractMessageReceiveEvent}.
   *
   * @param source         the object on which the event initially occurred (never {@code null}).
   * @param messagePayload the payload of the received message. Must not be {@code null}.
   * @throws NullPointerException if {@code messagePayload} is {@code null}.
   * @see ApplicationEvent#ApplicationEvent(Object)
   */
  public MessageReceiveEvent(@NotNull final Object source,
                             @Nullable final IMessagePayload messagePayload) {
    super(source, messagePayload);
  }
}
