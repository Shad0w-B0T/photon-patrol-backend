package de.hdm.photon.schema.exception.entity;

/**
 * Exception is thrown when the entity does not exist.
 */
public class EntityNotPresentException extends EntityException {
  public EntityNotPresentException(String message){
    super(message);
  }
}
