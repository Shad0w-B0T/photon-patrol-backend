package de.hdm.photon.queue.logic.test;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import de.hdm.photon.queue.logic.connection.IRabbitConnection;
import de.hdm.photon.queue.logic.connection.RabbitConnection;


public class sub2 {

  public static String CON = "amqp://user:password@localhost:5672/photon";

  public static void main(String[] args) throws Exception {
    final IRabbitConnection rabbitConnection = new RabbitConnection(CON);

    final Connection connection = rabbitConnection.connection();
    final Channel channel = connection.createChannel();

    channel.exchangeDeclare("tbroadcast", "fanout");
    channel.queueDeclare("tqueue", true, false, false, null);

    //Create queue.
    //final String queueName = channel.queueDeclare().getQueue();
    //Bind queue to exchange and key.
    channel.queueBind("tqueue", "tbroadcast", "");

    final String subscription = channel.basicConsume("tqueue", (s, delivery) -> {
      System.out.println("Recived("+delivery.getEnvelope().getExchange()+"):"+new String(delivery.getBody()));

      channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
    }, (consumerTag, sig) -> {
    });

    System.out.println("Waiting...");
  }

}
