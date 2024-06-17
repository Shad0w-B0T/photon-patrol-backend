package de.hdm.photon.logic.security.io;

import org.jetbrains.annotations.Nullable;

import java.util.function.Predicate;


public interface ISecurityFilter {

  <TYPE> ISecurityFilter rule(@Nullable final Class<TYPE> typeClass,
                              @Nullable final Predicate<TYPE> typePredicate);

  <TYPE> boolean check(@Nullable final TYPE type);

  <TYPE> void checkWithException(@Nullable final TYPE type) throws SecurityException;
}
