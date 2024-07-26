package de.hdm.photon.schema.measurement;

/**
 * Interface representing an entity with a Unix timestamp in milliseconds.
 */
public interface TimeBounded {
  /**
   * Retrieves the Unix timestamp in milliseconds.
   *
   * @return Unix timestamp in milliseconds.
   */
  long time();
}
