package de.hdm.photon.queue.logic.test;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import de.hdm.photon.queue.logic.connection.IRabbitConnection;
import de.hdm.photon.queue.logic.connection.RabbitConnection;

import java.nio.charset.StandardCharsets;
import java.util.UUID;


public class publish {

  public static String CON = "amqp://user:password@localhost:5672/photon";

  public static void main(String[] args) throws Exception {
    final IRabbitConnection rabbitConnection = new RabbitConnection(CON);

    final Connection connection = rabbitConnection.connection();
    final Channel channel = connection.createChannel();

    channel.exchangeDeclare("tbroadcast", "fanout");
    channel.queueDeclare("tqueue", true, false, false, null);


    while (true) {
      final String message = UUID.randomUUID().toString();

      channel.basicPublish("tbroadcast", "", null, message.getBytes(StandardCharsets.UTF_8));
      System.out.println("Publisched: "+message);
      Thread.sleep(2500L);
    }

  }

}
