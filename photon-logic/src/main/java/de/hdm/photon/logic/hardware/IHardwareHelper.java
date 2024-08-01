package de.hdm.photon.logic.hardware;

import de.hdm.photon.database.postgresql.hardware.HardwareEntity;


/**
 * Interface for hardware helper operations.
 * Extends {@link IHardwareAccess} to provide access to operations for {@link HardwareEntity} objects.
 */
public interface IHardwareHelper extends IHardwareAccess<HardwareEntity> {

}
