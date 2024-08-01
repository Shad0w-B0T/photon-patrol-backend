package de.hdm.photon.logic.hardware.session;

import de.hdm.photon.database.postgresql.hardware.session.HardwareSessionEntity;

/**
 * {@code IHardwareSessionHelper} extends {@link IHardwareSessionAccess} to provide methods for managing
 * hardware session entities specifically for the {@link HardwareSessionEntity} type.
 * <p>
 * This interface serves as a helper for accessing hardware session data, providing additional
 * operations and handling for the specific entity type used within the application.
 * </p>
 */
public interface IHardwareSessionHelper extends IHardwareSessionAccess<HardwareSessionEntity> {
}
