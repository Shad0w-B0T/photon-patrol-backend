package de.hdm.photon.queue.application.task;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.hdm.photon.logic.console.IConsoleHeartbeatInfo;
import de.hdm.photon.logic.security.io.ISecurityFilter;
import de.hdm.photon.logic.security.io.SecurityFilter;
import de.hdm.photon.logic.security.io.SecurityRule;
import de.hdm.photon.queue.application.listener.MessageReceiveListener;
import de.hdm.photon.queue.logic.connection.IRabbitConnection;
import de.hdm.photon.queue.logic.connection.IRabbitSubscriber;
import de.hdm.photon.queue.logic.event.MessageReceiveEvent;
import de.hdm.photon.schema.exception.RabbitConnectionException;
import de.hdm.photon.schema.payload.IMessagePayload;
import de.hdm.photon.schema.payload.ImmutableMessagePayload;
import jakarta.annotation.PostConstruct;
import lombok.extern.log4j.Log4j2;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Log4j2
@Component
public final class QueueSubscriber {
  @Autowired
  private ApplicationEventPublisher eventPublisher;
  @Autowired
  private IConsoleHeartbeatInfo heartbeatInfo;
  @Autowired
  private IRabbitConnection rabbitConnection;
  @Autowired
  private MessageReceiveListener messageReceiveListener;

  @Autowired
  private ObjectMapper objectMapper;

  private final @NotNull Executor executor;
  private final @NotNull ISecurityFilter securityFilter;

  public QueueSubscriber() {
    this.executor = Executors.newCachedThreadPool();
    this.securityFilter = new SecurityFilter();
  }

  @PostConstruct
  public void init() throws RabbitConnectionException {
    this.securityFilter
        .rule(String.class, s -> s.length()<=512)
        .rule(Integer.class, SecurityRule.INT_POSITIVE)
        .rule(Long.class, SecurityRule.LONG_POSITIVE)
        .rule(Float.class, SecurityRule.FLOAT_POSITIVE)
        .rule(Double.class, SecurityRule.DOUBLE_POSITIVE);

    final IRabbitSubscriber rabbitSubscriber = this.rabbitConnection.subscriber(true);

    rabbitSubscriber.subscribe(bytes -> {
      //Return if no payload is present.
      if (bytes == null) {
        log.warn("Empty payload as message.");
        return;
      }

      log.info("Data set received with the length of {} bytes. Message is being processed.", bytes.length);
      this.processMessage(bytes);

      this.heartbeatInfo.reset();
    });
    log.info("Subscribed to system, waiting for messages.");
  }

  /**
   * Processes a message in a byte array by deserializing it, sanitizing it, and publishing an event.
   *
   * @param bytes the byte array containing the message; cannot be null
   */
  private void processMessage(final byte @NotNull [] bytes) {
    this.executor.execute(() -> {
      try {
        final IMessagePayload messagePayload = this.objectMapper.readValue(bytes, ImmutableMessagePayload.class);

        // Sanitize
        this.securityFilter.checkObject(messagePayload);

        final  MessageReceiveEvent event = new MessageReceiveEvent(this, messagePayload);

        // Execute event
        this.eventPublisher.publishEvent(event);
        log.info("Parsed message and passed to backend.");
      } catch (final IOException exception) {
        log.warn("Error while mapping payload: {}.", new String(bytes));
      }
    });
  }
}
