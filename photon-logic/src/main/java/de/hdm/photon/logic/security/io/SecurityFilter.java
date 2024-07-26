package de.hdm.photon.logic.security.io;

import org.jetbrains.annotations.Nullable;


/**
 * SecurityFilter is a concrete implementation of the AbstractSecurityFilter,
 * providing methods to define and check security rules.
 */
public final class SecurityFilter extends AbstractSecurityFilter {
  /**
   * Default constructor that initializes an empty SecurityFilter.
   */
  public SecurityFilter() {
    super();
  }

  /**
   * Constructor that merges rules from another ISecurityFilter.
   *
   * @param includeFilter the ISecurityFilter to include rules from; can be null
   */
  public SecurityFilter(@Nullable final ISecurityFilter includeFilter) {
    super(includeFilter);
  }
}
