package de.hdm.photon.schema.exception;

public class EntityAlreadyPresentException extends AbstractEntityException {
  public EntityAlreadyPresentException(String message){
    super(message);
  }
}
