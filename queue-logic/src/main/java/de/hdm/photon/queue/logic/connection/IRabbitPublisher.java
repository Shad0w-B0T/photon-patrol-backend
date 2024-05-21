package de.hdm.photon.queue.logic.connection;

import de.hdm.photon.schema.exception.connection.RabbitConnectionException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


public interface IRabbitPublisher {

  @NotNull IRabbitPublisher publish(byte @Nullable [] content) throws RabbitConnectionException;

}
