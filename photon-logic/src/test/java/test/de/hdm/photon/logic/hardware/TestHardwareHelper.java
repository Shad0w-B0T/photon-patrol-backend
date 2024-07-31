package test.de.hdm.photon.logic.hardware;

import de.hdm.photon.database.postgresql.hardware.HardwareEntity;
import de.hdm.photon.logic.hardware.IHardwareAccess;
import de.hdm.photon.logic.hardware.IHardwareHelper;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;


/**
 * Integration test class for {@link IHardwareHelper}.
 * <p>
 * This test class extends {@link TestAbstractHardware} and provides {@link IHardwareHelper}
 * as the hardware access implementation.
 * </p>
 */
@SpringBootTest
@DirtiesContext(classMode=ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace=Replace.NONE)
public class TestHardwareHelper extends TestAbstractHardware<HardwareEntity> {

  @Autowired
  private IHardwareHelper hardwareHelper;

  /**
   * {@inheritDoc}
   */
  @Override
  protected @NotNull IHardwareAccess<HardwareEntity> hardwareAccess() {
    return this.hardwareHelper;
  }
}
