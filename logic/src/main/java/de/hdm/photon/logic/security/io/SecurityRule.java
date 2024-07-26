package de.hdm.photon.logic.security.io;

import org.jetbrains.annotations.NotNull;

import java.util.function.Predicate;


public interface SecurityRule {

  @NotNull Predicate<Long> LONG_POSITIVE = aLong -> aLong>=0;
  @NotNull Predicate<Integer> INT_POSITIVE = integer -> integer>=0;
  @NotNull Predicate<Float> FLOAT_POSITIVE = aFloat -> aFloat>=0;
  @NotNull Predicate<Double> DOUBLE_POSITIVE = aDouble -> aDouble>=0;
}
