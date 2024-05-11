package de.hdm.photon.schema.exception.entity;

public abstract class EntityException extends Exception{
  public EntityException() {
  }

  public EntityException(String message){
    super(message);
  }
}
