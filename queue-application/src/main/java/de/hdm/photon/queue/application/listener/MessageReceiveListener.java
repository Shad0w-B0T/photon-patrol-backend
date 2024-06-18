package de.hdm.photon.queue.application.listener;

import de.hdm.photon.queue.logic.event.MessageReceiveEvent;
import de.hdm.photon.schema.payload.IMessagePayload;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;


@Component
public final class MessageReceiveListener {

  @EventListener
  public void handle(@NotNull final MessageReceiveEvent event) {
    final IMessagePayload messagePayload = event.messagePayload();
    System.out.println(messagePayload.token());
    System.out.println(messagePayload.macAddress());
    System.out.println(messagePayload.values());
  }
}
