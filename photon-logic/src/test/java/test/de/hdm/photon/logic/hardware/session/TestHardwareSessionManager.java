package test.de.hdm.photon.logic.hardware.session;

import de.hdm.photon.logic.hardware.IHardwareAccess;
import de.hdm.photon.logic.hardware.IHardwareManager;
import de.hdm.photon.logic.hardware.session.IHardwareSessionAccess;
import de.hdm.photon.logic.hardware.session.IHardwareSessionManager;
import de.hdm.photon.schema.hardware.IHardware;
import de.hdm.photon.schema.hardware.session.IHardwareSession;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;


@SpringBootTest
@DirtiesContext(classMode=ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace=Replace.NONE)
public class TestHardwareSessionManager extends TestAbstractHardwareSession<IHardware, IHardwareSession> {

  @Autowired
  private IHardwareManager hardwareManager;

  @Autowired
  private IHardwareSessionManager hardwareSessionManager;

  /**
   * {@inheritDoc}
   */
  @Override
  protected @NotNull IHardwareAccess<IHardware> hardwareAccess() {
    return this.hardwareManager;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected @NotNull IHardwareSessionAccess<IHardwareSession> hardwareSessionAccess() {
    return this.hardwareSessionManager;
  }
}
