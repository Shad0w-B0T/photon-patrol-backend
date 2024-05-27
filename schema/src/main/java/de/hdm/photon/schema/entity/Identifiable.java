package de.hdm.photon.schema.entity;

import io.swagger.v3.oas.annotations.media.Schema;


public interface Identifiable {
  /**
   * @return the id of the object.
   */
  @Schema(example="1", description="Unique identifier of an object.")
  long id();
}
