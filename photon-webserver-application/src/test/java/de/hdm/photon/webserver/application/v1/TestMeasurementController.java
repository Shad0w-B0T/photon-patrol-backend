package de.hdm.photon.webserver.application.v1;

import de.hdm.photon.webserver.application.connection.AbstractSpringRestTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.ArrayList;



@SpringBootTest(webEnvironment=WebEnvironment.DEFINED_PORT)
@TestPropertySource(properties={
    "application.security.active=false",
    "server.port=80"
})
@DirtiesContext(classMode=ClassMode.BEFORE_EACH_TEST_METHOD)
public class TestMeasurementController extends AbstractSpringRestTest {


  public TestMeasurementController() {
    super("/api/v1/measurement");
  }


  @Test
  @DisplayName("Test if initial received data is empty after a specific Unix timestamp.")
  public void testAllGetMeasurements() {
    //Positive
    Assertions.assertTrue(this.get("", MeasurementList.class).isEmpty());
  }

  @Test
  @DisplayName("Test get all measurements of a not existing hardware.")
  public void testGetNotExistingHardware() {
    //Negative
    Assertions.assertThrows(WebClientResponseException.NotFound.class, () ->
        this.get("/hardwareNotExisting", MeasurementList.class));
  }

  @Test
  @DisplayName("Test get not existing measurement data for a not existing hardware after a specific Unix timestamp.")
  public void testGetNotExistingHardwareAndMeasurements() {
    //Negative
    Assertions.assertThrows(WebClientResponseException.NotFound.class, () ->
        this.get("/hardwareNotExisting/measurementNotExisting", MeasurementList.class));
  }


  private final static class MeasurementList extends ArrayList<String> {
  }
}
