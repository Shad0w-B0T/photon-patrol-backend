package de.hdm.photon.logic.security.io;

import org.jetbrains.annotations.NotNull;

import java.util.function.Predicate;

/**
 * {@code SecurityRule} defines common security rules as predicates for validating various numeric types.
 * <p>
 * This interface contains predefined predicates to check if numeric values are non-negative. These rules can be
 * used for validating input data to ensure that values meet specific security criteria, such as being positive or
 * zero.
 * </p>
 *
 * The predicates included are:
 *  <ul>
 *   <li>{@link #LONG_POSITIVE} - Validates that a {@code long} value is non-negative.</li>
 *   <li>{@link #INT_POSITIVE} - Validates that an {@code int} value is non-negative.</li>
 *   <li>{@link #FLOAT_POSITIVE} - Validates that a {@code float} value is non-negative.</li>
 *   <li>{@link #DOUBLE_POSITIVE} - Validates that a {@code double} value is non-negative.</li>
 *  </ul>
 *
 */
public interface SecurityRule {

  /**
   * Predicate that checks if a {@code long} value is non-negative (greater than or equal to zero).
   */
  @NotNull Predicate<Long> LONG_POSITIVE = aLong -> aLong >= 0;

  /**
   * Predicate that checks if an {@code int} value is non-negative (greater than or equal to zero).
   */
  @NotNull Predicate<Integer> INT_POSITIVE = integer -> integer >= 0;

  /**
   * Predicate that checks if a {@code float} value is non-negative (greater than or equal to zero).
   */
  @NotNull Predicate<Float> FLOAT_POSITIVE = aFloat -> aFloat >= 0;

  /**
   * Predicate that checks if a {@code double} value is non-negative (greater than or equal to zero).
   */
  @NotNull Predicate<Double> DOUBLE_POSITIVE = aDouble -> aDouble >= 0;
}
