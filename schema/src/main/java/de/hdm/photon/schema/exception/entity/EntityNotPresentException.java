package de.hdm.photon.schema.exception.entity;

public class EntityNotPresentException extends EntityException {
  /**
   * Exception is thrown when the entity does not exist.
   *
   * @param message individual message of the exception.
   */
  public EntityNotPresentException(String message){
    super(message);
  }
}
