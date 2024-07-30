package test.de.hdm.photon.logic.hardware.session;

import de.hdm.photon.database.postgresql.hardware.HardwareEntity;
import de.hdm.photon.database.postgresql.hardware.HardwareSessionEntity;
import de.hdm.photon.logic.hardware.IHardwareAccess;
import de.hdm.photon.logic.hardware.IHardwareHelper;
import de.hdm.photon.logic.hardware.session.IHardwareSessionAccess;
import de.hdm.photon.logic.hardware.session.IHardwareSessionHelper;
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
public class TestHardwareSessionHelper extends TestAbstractHardwareSession<HardwareEntity, HardwareSessionEntity> {

  @Autowired
  private IHardwareHelper hardwareHelper;

  @Autowired
  private IHardwareSessionHelper sessionHelper;

  /**
   * {@inheritDoc}
   */
  @Override
  protected @NotNull IHardwareAccess<HardwareEntity> hardwareAccess() {
    return this.hardwareHelper;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected @NotNull IHardwareSessionAccess<HardwareSessionEntity> hardwareSessionAccess() {
    return this.sessionHelper;
  }
}
