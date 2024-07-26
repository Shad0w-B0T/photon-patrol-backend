package de.hdm.photon.schema.exception;

import org.jetbrains.annotations.Nullable;


public abstract class AbstractEntityException extends Exception {
  public AbstractEntityException() {
  }

  public AbstractEntityException(@Nullable final String message) {
    super(message);
  }
}
