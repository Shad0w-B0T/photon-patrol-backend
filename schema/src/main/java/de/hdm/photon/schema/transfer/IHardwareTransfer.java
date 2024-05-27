package de.hdm.photon.schema.transfer;

import de.hdm.photon.schema.hardware.IHardwareAddress;
import de.hdm.photon.schema.hardware.IHardwareToken;
import org.jetbrains.annotations.NotNull;

import java.util.Map;


public interface IHardwareTransfer extends IHardwareAddress, IHardwareToken {

  @NotNull Map<String, Object> values();

}
