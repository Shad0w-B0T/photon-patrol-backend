package de.hdm.photon.webserver.application.v1;

/*
import de.hdm.photon.schema.hardware.HardwareRecord;
import de.hdm.photon.webserver.application.connection.AbstractSpringRestTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.ArrayList;


@SpringBootTest(webEnvironment=WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode=ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(connection=EmbeddedDatabaseConnection.H2)
public final class TestHardwareController extends AbstractSpringRestTest {

  public TestHardwareController() {
    super("/api/v1/hardware");
  }


  @Test
  @DisplayName("Test get all hardware.")
  public void testGetHardware() {
*/
/*     //Positive
    Assertions.assertTrue(this.get("", HardwareList.class).isEmpty());
    this.createCard();
    Assertions.assertFalse(this.get("", HardwareList.class).isEmpty());
    Assertions.assertFalse(this.get("?folderId=1", HardwareList.class).isEmpty());

    TestRestUtils.createFolder();
    Assertions.assertTrue(this.get("?folderId=2", HardwareList.class).isEmpty());

    //Negative
    Assertions.assertThrows(WebClientResponseException.NotFound.class, () ->
        this.get("?folderId=4", HardwareList.class)); *//*

  }

  @Test
  @DisplayName("Test get a specific hardware.")
  public void testGetSpecificHardware() {

  }

  @Test
  @DisplayName("Test create new hardware.")
  public void testCreateHardware() {

  }

  @Test
  @DisplayName("Test update hardware stats.")
  public void testUpdateHardware() {

  }

  @Test
  @DisplayName("Test update a whole hardwareobject.")
  public void testToggleHardware() {

  }

  @Test
  @DisplayName("Test delete specific hardware.")
  public void testDeleteSpecificHardware() {

  }




  private final static class HardwareList extends ArrayList<HardwareRecord> {
  }

  private final static class TagList extends ArrayList<TagList> {
  }

}
*/
