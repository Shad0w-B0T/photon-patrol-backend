package de.hdm.photon.webserver.logic.user;

import de.hdm.photon.database.postgresql.user.UserEntity;
import de.hdm.photon.database.postgresql.user.UserRepository;
import de.hdm.photon.logic.security.io.ISecurityFilter;
import de.hdm.photon.logic.security.io.SecurityFilter;
import de.hdm.photon.logic.utils.TokenGenerator;
import de.hdm.photon.schema.exception.EntityAlreadyPresentException;
import de.hdm.photon.schema.exception.EntityNotPresentException;
import de.hdm.photon.schema.exception.FormatMismatchException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Objects;


public abstract class AbstractUserHelper implements IUserHelper {

  private final @NotNull UserRepository userRepository;
  private final @NotNull PasswordEncoder passwordEncoder;
  private final @NotNull ISecurityFilter passwordFilter;

  protected AbstractUserHelper(@Nullable final UserRepository userRepository,
                               @Nullable final PasswordEncoder passwordEncoder) {
    //Null check
    Objects.requireNonNull(userRepository);
    Objects.requireNonNull(passwordEncoder);

    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
    this.passwordFilter = new SecurityFilter()
        .rule(String.class, string -> string.length()>=8 && string.length()<=128);
  }

  @Override
  public @NotNull List<UserEntity> getPersistentUsers() {
    return this.userRepository.findAll();
  }

  @Override
  public @NotNull UserEntity lookupPersistentUser(@Nullable String username) throws EntityNotPresentException {
    //Null check
    Objects.requireNonNull(username);
    return this.getUser(username);
  }

  @Override
  public boolean lookupPersistentUser(@Nullable String username,
                                      @Nullable String password) throws EntityNotPresentException {
    //Null check
    Objects.requireNonNull(username);
    Objects.requireNonNull(password);

    final UserEntity userEntity = this.getUser(username);
    final String hashedPassword = this.passwordEncoder.encode(password);

    return userEntity.password().equals(hashedPassword);
  }

  @Override
  public @NotNull UserEntity createPersistentUser(@Nullable String username,
                                                  @Nullable String password)
      throws EntityAlreadyPresentException, FormatMismatchException {
    //Null check
    Objects.requireNonNull(username);
    Objects.requireNonNull(password);

    if (this.userRepository.findByUsername(username).isPresent()) {
      throw new EntityAlreadyPresentException("User with username="+username+" already present.");
    }

    //Single user service
    if (!this.getPersistentUsers().isEmpty()) {
      throw new EntityAlreadyPresentException("Application is set as single user service.");
    }

    //Check passwort
    if(!this.passwordFilter.check(password)) {
      throw new FormatMismatchException("Passwort does not match requirements.");
    }

    final String salt = TokenGenerator.randomString(128);
    final String hashedPassword = this.passwordEncoder.encode(password);
    final UserEntity userEntity = new UserEntity(username, hashedPassword, salt);

    return this.userRepository.save(userEntity);
  }

  @Override
  public @NotNull UserEntity deletePersistentUser(@Nullable String username) throws EntityNotPresentException {
    //Null check
    Objects.requireNonNull(username);

    final UserEntity userEntity = this.getUser(username);

    this.userRepository.delete(userEntity);

    return userEntity;
  }

  private @NotNull UserEntity getUser(@Nullable final String username) throws EntityNotPresentException {
    return this.userRepository.findByUsername(username)
        .orElseThrow(() -> new EntityNotPresentException("No user found"));
  }
}
