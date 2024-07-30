package test.de.hdm.photon.logic.hearbeat;

import de.hdm.photon.logic.heartbeat.HeartbeatInfo;
import de.hdm.photon.logic.heartbeat.IHeartbeatInfo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


public final class HeartbeatInfoTest {

  @Test
  @DisplayName("Test HeartbeatInfo Positive")
  public void testHeartbeatInfoPositive() throws InterruptedException {
    //Positive Tests
    Assertions.assertDoesNotThrow(() -> new HeartbeatInfo(0));

    //Test
    final IHeartbeatInfo heartbeatInfo = new HeartbeatInfo(40);

    Assertions.assertTrue(heartbeatInfo.latestAction()>0);

    //Timeout and check
    Thread.sleep(50);
    Assertions.assertTrue(heartbeatInfo.timeout());

    //Reset ab check
    heartbeatInfo.reset();
  }

  @Test
  @DisplayName("Test HeartbeatInfo Negative")
  public void testHeartbeatInfoNegative() {
    //Negative Tests
    Assertions.assertThrows(IndexOutOfBoundsException.class, () -> new HeartbeatInfo(-1));
  }
}
