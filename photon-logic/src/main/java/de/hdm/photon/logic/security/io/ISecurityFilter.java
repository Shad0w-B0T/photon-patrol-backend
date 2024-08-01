package de.hdm.photon.logic.security.io;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;


/**
 * ISecurityFilter interface provides methods to define and check security rules.
 */
public interface ISecurityFilter {

  /**
   * Defines a security rule for a given type.
   *
   * @param <TYPE>        the type of the objects to which the security rule will be applied
   * @param typeClass     the class of the type for which the rule is defined; can be null
   * @param typePredicate the predicate that defines the rule for the given type; can be null
   * @return the updated ISecurityFilter instance with the added rule
   */
  <TYPE> ISecurityFilter rule(@Nullable final Class<TYPE> typeClass,
                              @Nullable final Predicate<TYPE> typePredicate);

  /**
   * Checks if a given object passes the security rules defined for its type.
   *
   * @param <TYPE> the type of the object to check
   * @param type   the object to check; can be null
   * @return true if the object passes the security rules, false otherwise
   */
  <TYPE> boolean check(@Nullable final TYPE type);

  /**
   * Checks if a given object passes the security rules defined for its type,
   * and throws a SecurityException if it does not.
   *
   * @param <TYPE> the type of the object to check
   * @param type   the object to check; can be null
   * @throws SecurityException if the object does not pass the security rules
   */
  <TYPE> void checkWithException(@Nullable final TYPE type) throws SecurityException;

  /**
   * Checks if a given object passes the security rules defined for its type.
   *
   * @param <OBJECT> the type of the object to check
   * @param object   the object to check; can be null
   * @return true if the object passes the security rules, false otherwise
   */
  <OBJECT> boolean checkObject(@Nullable final OBJECT object);

  /**
   * Checks if a given collection of objects passes the security rules defined for their type.
   *
   * @param <OBJECT>   the type of the objects to check
   * @param collection the collection of objects to check; can be null
   * @return true if all objects in the collection pass the security rules, false otherwise
   */
  <OBJECT> boolean checkCollection(@Nullable Collection<OBJECT> collection);

  /**
   * Checks if a given map of objects passes the security rules defined for their type.
   *
   * @param <KEY>   the type of the keys in the map
   * @param <VALUE> the type of the values in the map
   * @param map     the map of objects to check; can be null
   * @return true if all entries in the map pass the security rules, false otherwise
   */
  <KEY, VALUE> boolean checkMap(@Nullable Map<KEY, VALUE> map);

  /**
   * Returns the map of security rules defined for each type.
   *
   * @return a map where the keys are classes of types and the values are lists of predicates
   * that define the security rules for the corresponding types
   */
  @NotNull Map<Class<?>, List<Predicate<Object>>> ruleMap();
}