package de.hdm.photon.webserver.logic.security;

import de.hdm.photon.schema.exception.PasswordException;
import jakarta.validation.constraints.NotNull;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;


@Log4j2
public abstract class AbstractPasswordManager implements IPasswordManager {

  private final static @NotNull String FALLBACK_PASSWORD_STORAGE = "./security/pass.key";
  private static final Logger log = LogManager.getLogger(AbstractPasswordManager.class);

  private final @NotNull MessageDigest messageDigest;
  private final @NotNull String passwordStorage;

  protected AbstractPasswordManager(@Nullable final String hashName,
                                    @Nullable final String passwordStorage) {
    //Null check
    Objects.requireNonNull(hashName);

    final MessageDigest messageDigest;
    try {
      this.messageDigest = MessageDigest.getInstance(hashName);
      log.info("Initialized digest={} as application password digest.", hashName);
    } catch (final NoSuchAlgorithmException exception) {
      throw new RuntimeException(exception);
    }

    //Use fallback storage if config storage is null.
    this.passwordStorage = passwordStorage == null ? FALLBACK_PASSWORD_STORAGE : passwordStorage;
    //Create parent folder of storage
    if (new File(this.passwordStorage).getParentFile().mkdirs()) {
      log.info("Created parent folder={} for security master password.", this.passwordStorage);
    }
  }

  @Override
  public boolean match() throws PasswordException {
    return false;
  }

  @Override
  public boolean store(@Nullable String passphrase) throws PasswordException {
    //Null check
    Objects.requireNonNull(passphrase);

    return false;
  }

  private @NotNull String hash(@NotNull final String passphrase) {
    final byte[] hashPhrase = this.messageDigest.digest(passphrase.getBytes());
    final String hashString = new String(hashPhrase);

    return hashString;
  }
}
