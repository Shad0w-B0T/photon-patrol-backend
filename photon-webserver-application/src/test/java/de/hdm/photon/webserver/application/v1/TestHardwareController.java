package de.hdm.photon.webserver.application.v1;


import de.hdm.photon.database.postgresql.hardware.HardwareRepository;
import de.hdm.photon.schema.hardware.IHardware;
import de.hdm.photon.schema.hardware.ImmutableHardware;
import de.hdm.photon.webserver.application.connection.AbstractSpringRestTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.ArrayList;


@SpringBootTest(webEnvironment=WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode=ClassMode.BEFORE_EACH_TEST_METHOD)
public final class TestHardwareController extends AbstractSpringRestTest {

  @Autowired
  private HardwareRepository hardwareRepository;

  public TestHardwareController() {
    super("/api/v1/hardware");
  }

  @BeforeEach
  public void cleanUp() {
    this.hardwareRepository.deleteAll();
  }

  @Test
  @DisplayName("Test get all hardware.")
  public void testGetHardware() {
    //Positive
    Assertions.assertTrue(this.get("", HardwareList.class).isEmpty());
    this.post("", ImmutableHardware.class);
    this.post("", ImmutableHardware.class);
    Assertions.assertFalse(this.get("", HardwareList.class).isEmpty());

    Assertions.assertEquals(2, this.get("", HardwareList.class).size());

    //Negative
    this.post("", ImmutableHardware.class);
    Assertions.assertNotEquals(2, this.get("", HardwareList.class).size());
  }

  @Test
  @DisplayName("Test get a specific hardware.")
  public void testGetSpecificHardware() {
    //Positive
    Assertions.assertTrue(this.get("", HardwareList.class).isEmpty());
    final IHardware firstHardware = this.post("", ImmutableHardware.class);
    final IHardware secondHardware = this.post("", ImmutableHardware.class);
    Assertions.assertFalse(this.get("", HardwareList.class).isEmpty());

    Assertions.assertDoesNotThrow(() -> this.get("/"+firstHardware.id(), ImmutableHardware.class));
    Assertions.assertDoesNotThrow(() -> this.get("/"+secondHardware.id(), ImmutableHardware.class));

    //Negative
    Assertions.assertThrows(WebClientResponseException.NotFound.class,
        () -> this.get("/"+(Long.MAX_VALUE-1), ImmutableHardware.class));
  }

  @Test
  @DisplayName("Test create new hardware.")
  public void testCreateHardware() {
    //Positive
    Assertions.assertTrue(this.get("", HardwareList.class).isEmpty());
    this.post("", ImmutableHardware.class);
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
    final IHardware createdHardware = this.post("", ImmutableHardware.class);
    Assertions.assertTrue(this.get("/"+createdHardware.id(), ImmutableHardware.class).tokenIsActive());

    this.put("/"+createdHardware.id()+"/toggle", ImmutableHardware.class);
    Assertions.assertFalse(this.get("/"+createdHardware.id(), ImmutableHardware.class).tokenIsActive());

    //Negative
    Assertions.assertThrows(WebClientResponseException.NotFound.class,
        () -> this.put("/1/toggle", ImmutableHardware.class));
    Assertions.assertFalse(this.get("/"+createdHardware.id(), ImmutableHardware.class).tokenIsActive());

    Assertions.assertThrows(WebClientResponseException.NotFound.class,
        () -> this.put("/"+(Long.MAX_VALUE-1)+"/toggle", ImmutableHardware.class));
  }

  @Test
  @DisplayName("Test delete specific hardware.")
  public void testDeleteSpecificHardware() {
    //Positive
    Assertions.assertTrue(this.get("", HardwareList.class).isEmpty());
    final IHardware hardware = this.post("", ImmutableHardware.class);
    Assertions.assertFalse(this.get("", HardwareList.class).isEmpty());
    this.delete("/"+hardware.id(), ImmutableHardware.class);
    Assertions.assertTrue(this.get("", HardwareList.class).isEmpty());

    //Negative
    Assertions.assertTrue(this.get("", HardwareList.class).isEmpty());
    Assertions.assertThrows(WebClientResponseException.NotFound.class, () ->
        this.delete("/"+hardware.id(), ImmutableHardware.class));
  }


  private final static class HardwareList extends ArrayList<ImmutableHardware> {
  }
}