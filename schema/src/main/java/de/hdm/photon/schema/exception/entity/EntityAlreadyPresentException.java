package de.hdm.photon.schema.exception.entity;

public class EntityAlreadyPresentException extends EntityException {
  public EntityAlreadyPresentException(String message){
    super(message);
  }
}
