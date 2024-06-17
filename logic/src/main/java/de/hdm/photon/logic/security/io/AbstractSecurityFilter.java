package de.hdm.photon.logic.security.io;

import lombok.extern.log4j.Log4j2;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;


@Log4j2
public abstract class AbstractSecurityFilter implements ISecurityFilter {

  private final @NotNull MultiValueMap<Class<?>, Predicate<Object>> ruleMap;

  protected AbstractSecurityFilter() {
    this.ruleMap = new LinkedMultiValueMap<>();
  }


  @Override
  @SuppressWarnings("unchecked")
  public <TYPE> ISecurityFilter rule(@Nullable Class<TYPE> typeClass,
                                     @Nullable Predicate<TYPE> typePredicate) {
    //Null check
    Objects.requireNonNull(typeClass);
    Objects.requireNonNull(typePredicate);

    //Add rule with cast.
    this.ruleMap.add(typeClass, o -> typePredicate.test((TYPE) o));
    log.info("Added rule to type={}.", typeClass.getSimpleName());

    return this;
  }

  @Override
  public <TYPE> boolean check(@Nullable TYPE type) {
    try {
      this.checkWithException(type);
      return true;
    } catch (final SecurityException exception) {
      return false;
    }
  }

  @Override
  public <TYPE> void checkWithException(@Nullable TYPE type) throws SecurityException {
    //Null check
    Objects.requireNonNull(type);

    final Class<?> typeClass = type.getClass();
    @Nullable final List<Predicate<Object>> predicateList = this.ruleMap.get(typeClass);

    if (predicateList == null || predicateList.isEmpty()) {
      log.warn("No rules are defined for type={}.", typeClass);
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
}
