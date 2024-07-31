package de.hdm.photon.schema.hardware;

import io.swagger.v3.oas.annotations.media.Schema;
import org.jetbrains.annotations.Nullable;


/**
 * Represents the MAC address of connected hardware.
 */
public interface IHardwareAddress {

  /**
   * @return the value of the MAC-Address.
   */
  @Schema(example="A1:B4:C5:C1:DD:3E", description="The MAC address of the connected hardware.")
  @Nullable
  String macAddress();

}
