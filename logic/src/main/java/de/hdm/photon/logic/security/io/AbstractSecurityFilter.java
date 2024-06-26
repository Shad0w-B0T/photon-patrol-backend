package de.hdm.photon.logic.security.io;

import lombok.extern.log4j.Log4j2;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;


/**
 * AbstractSecurityFilter provides an abstract implementation of the ISecurityFilter interface
 * with common functionalities for managing and applying security rules.
 */
@Log4j2
public abstract class AbstractSecurityFilter implements ISecurityFilter {

  private final @NotNull MultiValueMap<Class<?>, Predicate<Object>> ruleMap;

  /**
   * Default constructor initializing the rule map.
   */
  protected AbstractSecurityFilter() {
    this.ruleMap = new LinkedMultiValueMap<>();
  }

  /**
   * Constructor that merges rules from another ISecurityFilter.
   *
   * @param includeFilter the ISecurityFilter to include rules from; can be null
   */
  protected AbstractSecurityFilter(@Nullable final ISecurityFilter includeFilter) {
    this();

    // Null check
    Objects.requireNonNull(includeFilter);

    this.ruleMap.putAll(includeFilter.ruleMap());
    log.info("Merged a total of {} objects.", includeFilter.ruleMap().size());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  @SuppressWarnings("unchecked")
  public <TYPE> @NotNull ISecurityFilter rule(@Nullable Class<TYPE> typeClass,
                                              @Nullable Predicate<TYPE> typePredicate) {
    // Null check
    Objects.requireNonNull(typeClass);
    Objects.requireNonNull(typePredicate);

    // Add rule with cast.
    this.ruleMap.add(typeClass, o -> typePredicate.test((TYPE) o));
    log.info("Added rule to type={}.", typeClass.getSimpleName());

    return this;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <TYPE> boolean check(@Nullable TYPE type) {
    //Null check
    Objects.requireNonNull(type);

    try {
      this.checkWithException(type);
      return true;
    } catch (final SecurityException exception) {
      return false;
    } catch (final NullPointerException exception) {
      return true;
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <OBJECT> boolean checkCollection(@Nullable Collection<OBJECT> collection) {
    //Null check
    Objects.requireNonNull(collection);

    //Store value of check
    boolean valid = true;

    //Iterate trough objects.
    for (final OBJECT object : collection) {
      //o.k.
      if (object != null && this.check(object)) {
        continue;
      }

      //Not valid and interrupt.
      valid = false;
      break;
    }

    return valid;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <KEY, VALUE> boolean checkMap(@Nullable Map<KEY, VALUE> map) {
    //Null check
    Objects.requireNonNull(map);
    //Store value of check
    boolean valid = true;
    for (final Entry<KEY, VALUE> keyvalueEntry : map.entrySet()) {
      final KEY key = keyvalueEntry.getKey();
      final VALUE value = keyvalueEntry.getValue();

      //o.k.
      if ((key != null && this.check(key)) && (value != null && this.check(value))) {
        continue;
      }
      //Not valid and interrupt.
      valid = false;
      break;
    }

    return valid;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  @SuppressWarnings("unchecked")
  public <TYPE> void checkWithException(@Nullable TYPE type) throws SecurityException {
    // Null check
    Objects.requireNonNull(type);

    final Class<?> typeClass = type.getClass();

    @Nullable final List<Predicate<Object>> predicateList = this.ruleMap.get(typeClass);
    if (predicateList == null || predicateList.isEmpty()) {
      log.warn("No rules are defined for type={}.", typeClass.getSimpleName());
      throw new NullPointerException("No rule for type="+typeClass+" defined.");
    }

    for (final Predicate<Object> objectPredicate : predicateList) {
      if (objectPredicate.test(type)) {
        log.debug("Check of type={} is valid.", typeClass.getSimpleName());
        continue;
      }

      log.warn("Security mismatch for type={}.", typeClass.getSimpleName());
      throw new SecurityException("Error while checking type="+typeClass+".");
    }

    log.info("Type={} with values is valid.", typeClass.getSimpleName());
  }

  /**
   * Checks if a given object's fields pass the security rules defined for their types.
   *
   * @param <OBJECT> the type of the object to check
   * @param object   the object to check; can be null
   * @return true if all fields of the object pass the security rules, false otherwise
   * @throws NullPointerException if the provided object is null
   */
  @Override
  public <OBJECT> boolean checkObject(@Nullable OBJECT object) {
    // Null check
    Objects.requireNonNull(object);

    final Class<?> typeClass = object.getClass();

    for (final Field declaredField : typeClass.getDeclaredFields()) {
      log.info("Checking field={} of object={}.", declaredField.getName(), typeClass.getSimpleName());
      try {
        declaredField.setAccessible(true);
        final Object fieldValue = declaredField.get(object);

        if (this.check(fieldValue)) {
          continue;
        }

        log.warn("Security mismatch for field={} of object={}.", declaredField.getName(), typeClass.getSimpleName());
        return false;
      } catch (final IllegalAccessException ignored) {
      }
    }
    return true;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public @NotNull Map<Class<?>, List<Predicate<Object>>> ruleMap() {
    return this.ruleMap;
  }
}
