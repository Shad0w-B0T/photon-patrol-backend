package de.hdm.photon.logic.hardwareSession;

import de.hdm.photon.schema.hardware.IHardwareSession;
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
public class TestHardwareSessionManager extends TestAbstractHardwareSession<IHardwareSession> {

  @Autowired
  private IHardwareSessionManager hardwareSessionManager;

  @Override
  protected @NotNull IHardwareSessionAccess<IHardwareSession> hardwareSessionAccess() {
    return this.hardwareSessionManager;
  }
}
