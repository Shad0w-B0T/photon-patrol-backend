package de.hdm.photon.webserver.application.v1;



import de.hdm.photon.schema.hardware.ImmutableHardware;
import de.hdm.photon.webserver.application.connection.AbstractSpringRestTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import java.util.ArrayList;


@SpringBootTest(webEnvironment=WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode=ClassMode.BEFORE_EACH_TEST_METHOD)
public final class TestHardwareController extends AbstractSpringRestTest {

  public TestHardwareController() {
    super("/api/v1/hardware");
  }


  @Test
  @DisplayName("Test get all hardware.")
  public void testGetHardware() {

    //Positive
    Assertions.assertTrue(this.get("", HardwareList.class).isEmpty());
    this.post("",ImmutableHardware.class);
    this.post("",ImmutableHardware.class);
    Assertions.assertFalse(this.get("", HardwareList.class).isEmpty());

    Assertions.assertEquals(2, this.get("", HardwareList.class).size());

    //Negative
    this.post("",ImmutableHardware.class);
    Assertions.assertNotEquals(2, this.get("", HardwareList.class).size());

  }

  @Test
  @DisplayName("Test get a specific hardware.")
  public void testGetSpecificHardware() {

    //Positive
    Assertions.assertTrue(this.get("", HardwareList.class).isEmpty());
    this.post("",ImmutableHardware.class);
    this.post("",ImmutableHardware.class);
    Assertions.assertFalse(this.get("", HardwareList.class).isEmpty());

    Assertions.assertDoesNotThrow(() -> this.get("/1", ImmutableHardware.class));
    Assertions.assertDoesNotThrow(() -> this.get("/2", ImmutableHardware.class));

    //Negative
    Assertions.assertThrows(WebClientResponseException.NotFound.class, () -> this.get("/3", ImmutableHardware.class));

  }

  @Test
  @DisplayName("Test create new hardware.")
  public void testCreateHardware() {

    //Positive
    Assertions.assertTrue(this.get("", HardwareList.class).isEmpty());
    this.post("",ImmutableHardware.class);
    Assertions.assertFalse(this.get("", HardwareList.class).isEmpty());

  }

  @Test
  @DisplayName("Test update hardware stats.")
  public void testUpdateHardware() {


  }

  @Test
  @DisplayName("Test update hardware tokenIsActive state.")
  public void testToggleHardware() {

    Assertions.assertTrue(this.get("", HardwareList.class).isEmpty());
    this.post("",ImmutableHardware.class);
    Assertions.assertTrue(this.get("/1", ImmutableHardware.class).tokenIsActive());

    this.put("/1/toggle",ImmutableHardware.class, "");
    Assertions.assertFalse(this.get("/1", ImmutableHardware.class).tokenIsActive());

    //Negative
    this.put("/1/toggle",ImmutableHardware.class, "");
    Assertions.assertFalse(this.get("/1", ImmutableHardware.class).tokenIsActive());

  }

  @Test
  @DisplayName("Test delete specific hardware.")
  public void testDeleteSpecificHardware() {

    //Positive
    Assertions.assertTrue(this.get("", HardwareList.class).isEmpty());
    this.post("",ImmutableHardware.class);
    Assertions.assertFalse(this.get("", HardwareList.class).isEmpty());
    this.delete("/1",ImmutableHardware.class);
    Assertions.assertTrue(this.get("", HardwareList.class).isEmpty());

    //Negative
    Assertions.assertTrue(this.get("", HardwareList.class).isEmpty());
    Assertions.assertThrows(WebClientResponseException.NotFound.class, () -> this.delete("/1", ImmutableHardware.class));

  }




  private final static class HardwareList extends ArrayList<ImmutableHardware> {
  }



}

