package de.hdm.photon.queue.logic.connection;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;


public interface IRabbitSubscriber {

  @NotNull IRabbitSubscriber subscribe(@Nullable final Consumer<byte @Nullable []> content);

}
