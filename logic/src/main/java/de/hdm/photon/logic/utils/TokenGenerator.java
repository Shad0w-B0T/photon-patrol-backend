package de.hdm.photon.logic.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;


@NoArgsConstructor(access=AccessLevel.PRIVATE)
@Component
public final class TokenGenerator {
  //Store alphabet chars
  private final static @NotNull String ALPHABET = "abcdefghijklmnopqrstuvwxyz0123456789";

  /**
   * Generate random string from alphabet.
   *
   * @param length to generate as string.
   * @return generated string.
   * @throws IllegalArgumentException if number is negative.
   */
  public static @NotNull String randomString(final int length) {
    //Check
    if (length<0) {
      throw new IllegalArgumentException("Length must be positive.");
    }

    final StringBuilder stringBuilder = new StringBuilder();
    final Random random = ThreadLocalRandom.current();

    while (stringBuilder.length()<length) {
      stringBuilder.append(ALPHABET.charAt(random.nextInt(ALPHABET.length())));
    }

    return stringBuilder.toString();
  }
}
