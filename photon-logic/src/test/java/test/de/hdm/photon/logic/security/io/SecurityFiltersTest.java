package test.de.hdm.photon.logic.security.io;

import de.hdm.photon.logic.security.io.ISecurityFilter;
import de.hdm.photon.logic.security.io.SecurityFilters;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


/**
 * Unit tests for the {@link SecurityFilters} class, specifically for MAC address filtering.
 */
public final class SecurityFiltersTest {
  /**
   * Tests the MAC address filter from {@link SecurityFilters}.
   * Verifies correct handling of valid and invalid MAC address formats.
   */
  @Test
  @DisplayName("Test SecurityFilters MAC")
  public void testSecurityFilterPositive() {
    final ISecurityFilter securityFilter = SecurityFilters.macStringFilter();
    //Positive Tests
    Assertions.assertNotNull(securityFilter);

    //Check different annotations
    Assertions.assertTrue(securityFilter.check("00-B0-D0-63-C2-26"));
    Assertions.assertTrue(securityFilter.check("00:B0:D0:63:C2:26"));

    Assertions.assertTrue(securityFilter.check("FF:FF:FF:FF:FF:FF"));
    Assertions.assertTrue(securityFilter.check("00:00:00:00:00:00"));

    Assertions.assertTrue(securityFilter.check("ff:ff:ff:ff:ff:ff"));
    Assertions.assertTrue(securityFilter.check("00:00:00:00:00:00"));

    //Negative Tests
    //Check different annotations
    Assertions.assertFalse(securityFilter.check("00.B0.D0.63.C2.26"));

    //Length
    Assertions.assertFalse(securityFilter.check("00:B0:D0:63:C2:26:23"));
    Assertions.assertFalse(securityFilter.check("00:B0:D0:63:C2"));
    Assertions.assertFalse(securityFilter.check("00:B0:D0:63C:C2:26"));

    //Check outside 0-f
    Assertions.assertFalse(securityFilter.check("gg:B0:D0:63C:C2:26"));
  }
}
