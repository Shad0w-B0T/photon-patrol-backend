package de.hdm.photon.logic.hardware;

import de.hdm.photon.schema.hardware.IHardware;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;


@SpringBootTest
@DirtiesContext(classMode=ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class TestHardwareManager extends TestAbstractHardware<IHardware> {

  @Autowired
  private IHardwareManager hardwareManager;

  /**
   * {@inheritDoc}
   */
  @Override
  protected @NotNull IHardwareAccess<IHardware> hardwareAccess() {
    return this.hardwareManager;
  }
}
