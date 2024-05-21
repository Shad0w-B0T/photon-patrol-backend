package de.hdm.photon;

import de.hdm.photon.queue.logic.config.IRabbitContext;
import de.hdm.photon.queue.logic.connection.IRabbitConnection;
import de.hdm.photon.queue.logic.connection.IRabbitPublisher;
import de.hdm.photon.queue.logic.connection.IRabbitSubscriber;
import de.hdm.photon.schema.exception.connection.RabbitConnectionException;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.nio.charset.StandardCharsets;


@SpringBootApplication
@EntityScan("de.hdm.photon")
@EnableJpaRepositories(basePackages="de.hdm.photon")
public class Driver {
  /**
   * The main method that starts the Spring Boot application.
   *
   * @param args command-line arguments passed to the application.
   */
  public static void main(String[] args) {
    var
        a = new SpringApplicationBuilder()
        .sources(Driver.class)
        .main(Driver.class)
        .web(WebApplicationType.NONE)
        .build()
        .run(args);


    final IRabbitConnection connection = a.getBean(IRabbitConnection.class);

    try {
      final IRabbitSubscriber rabbitSubscriber = connection.subscriber(true);

      rabbitSubscriber.subscribe(bytes -> {
        System.out.println("Got: "+new String(bytes));
      });
    } catch (RabbitConnectionException e) {
      throw new RuntimeException(e);
    }


    try {
      final IRabbitPublisher rabbitPublisher = connection.publisher();

      while (true) {
        rabbitPublisher.publish("Test".getBytes(StandardCharsets.UTF_8));
        try {
          Thread.sleep(1000L);
        } catch (InterruptedException e) {
          throw new RuntimeException(e);
        }
      }

    } catch (RabbitConnectionException e) {
      throw new RuntimeException(e);
    }


  }
}