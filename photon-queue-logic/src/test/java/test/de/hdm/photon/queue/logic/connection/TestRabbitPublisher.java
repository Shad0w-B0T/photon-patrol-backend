package test.de.hdm.photon.queue.logic.connection;

import de.hdm.photon.queue.logic.connection.IRabbitConnection;
import de.hdm.photon.queue.logic.connection.RabbitConnection;
import de.hdm.photon.queue.logic.connection.RabbitPublisher;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.annotation.DirtiesContext;
import test.de.hdm.photon.AbstractSpringTest;


@SpringBootTest
@DirtiesContext(classMode=DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace=AutoConfigureTestDatabase.Replace.NONE)
public final class TestRabbitPublisher extends AbstractSpringTest {

  @Autowired
  private IRabbitConnection rabbitConnection;

  @Test
  @DisplayName("Testing RabbitPublisher negative")
  public void testRabbitPublisherNegative() {
    Assertions.assertThrows(NullPointerException.class,
        () -> new RabbitPublisher(null, null));
  }

  @Test
  @DisplayName("Testing RabbitPublisher bean")
  public void testRabbitPublisherBean() {
    Assertions.assertDoesNotThrow(() -> {
      Assertions.assertNotNull(this.rabbitConnection.publisher());
    });
  }
}
