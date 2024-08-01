package de.hdm.photon.logic.hardware.session;

import de.hdm.photon.schema.hardware.session.IHardwareSession;

/**
 * {@code IHardwareSessionManager} extends {@link IHardwareSessionAccess} to manage hardware session entities
 * of type {@link IHardwareSession}.
 * <p>
 * This interface provides methods for creating, retrieving, and deleting hardware session entities.
 * It is designed to work with the {@link IHardwareSession} type, offering a higher level of abstraction
 * for managing hardware sessions in the system.
 * </p>
 */
public interface IHardwareSessionManager extends IHardwareSessionAccess<IHardwareSession> {
}
