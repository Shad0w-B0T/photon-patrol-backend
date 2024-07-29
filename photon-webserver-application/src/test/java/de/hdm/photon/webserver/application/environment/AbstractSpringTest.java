package de.hdm.photon.webserver.application.environment;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Field;


@Log4j2
public abstract class AbstractSpringTest {

  @Test
  @DisplayName("Test JPA-Repository load.")
  public void contextLoads() {
    log.info("Checking autowired components for={}.", this.getClass().getSimpleName());

    for (final Field declaredField : this.getClass().getDeclaredFields()) {
      if (declaredField.getAnnotation(Autowired.class) == null) {
        continue;
      }
      log.info("Checking bean={}.", declaredField.getClass().getSimpleName());
      Assertions.assertNotNull(declaredField);
    }
  }
}