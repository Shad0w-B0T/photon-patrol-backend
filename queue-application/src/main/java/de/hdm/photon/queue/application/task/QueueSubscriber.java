package de.hdm.photon.queue.application.task;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.hdm.photon.queue.application.heartbeat.HeartbeatInfo;
import de.hdm.photon.queue.logic.connection.IRabbitConnection;
import de.hdm.photon.queue.logic.connection.IRabbitSubscriber;
import de.hdm.photon.schema.exception.connection.RabbitConnectionException;
import jakarta.annotation.PostConstruct;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Log4j2
@Component
public final class QueueSubscriber {
  @Autowired
  private HeartbeatInfo heartbeatInfo;
  @Autowired
  private IRabbitConnection rabbitConnection;
  @Autowired
  private ObjectMapper objectMapper;

  @PostConstruct
  public void init() throws RabbitConnectionException {
    final IRabbitSubscriber rabbitSubscriber = this.rabbitConnection.subscriber(true);

    rabbitSubscriber.subscribe(bytes -> {
      //Return if no payload is present.
      if (bytes == null) {
        log.warn("Empty payload as message.");
        return;
      }

      log.info("Data set received with the length of {} bytes. Message is being processed.", bytes.length);
      System.out.println("Got: "+new String(bytes));
      this.heartbeatInfo.reset();
    });
    log.info("Subscribed to system, waiting for messages.");
  }
}
