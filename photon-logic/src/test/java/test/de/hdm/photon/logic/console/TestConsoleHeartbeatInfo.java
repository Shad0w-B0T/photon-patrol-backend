package test.de.hdm.photon.logic.console;

import de.hdm.photon.logic.console.ConsoleHeartbeatInfo;
import de.hdm.photon.logic.console.IConsoleHeartbeatInfo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


/**
 * Unit tests for {@link ConsoleHeartbeatInfo}.
 */
public final class TestConsoleHeartbeatInfo {

  @Test
  @DisplayName("Test HeartbeatInfo Positive")
  public void testHeartbeatInfoPositive() throws InterruptedException {
    //Positive Tests
    Assertions.assertDoesNotThrow(() -> new ConsoleHeartbeatInfo(0));

    //Test
    final IConsoleHeartbeatInfo heartbeatInfo = new ConsoleHeartbeatInfo(40);

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
    Assertions.assertThrows(IndexOutOfBoundsException.class, () -> new ConsoleHeartbeatInfo(-1));
  }
}
