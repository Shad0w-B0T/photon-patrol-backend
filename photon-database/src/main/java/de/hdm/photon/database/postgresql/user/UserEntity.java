package de.hdm.photon.database.postgresql.user;

import de.hdm.photon.schema.user.IUser;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;


/**
 * Entity representing a user in the database.
 */
@Entity
@Table(name="PhotonUser")
@NoArgsConstructor
@Getter
@Accessors(fluent=true)
public final class UserEntity implements IUser {

  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  private Long id;

  @Column(name="username", nullable=false)
  @Setter
  private String username;

  @Column(name="password", nullable=false, length=2048)
  @Setter
  private String password;

  @Column(name="salt", nullable=false, length=1024)
  @Setter
  private String salt;

  /**
   * Constructs a new UserEntity with the specified username, password, and salt.
   *
   * @param username the username of the user
   * @param password the password of the user
   * @param salt     the salt for the password
   */
  public UserEntity(@Nullable final String username,
                    @Nullable final String password,
                    @Nullable final String salt) {
    //Null check
    Objects.requireNonNull(username);
    Objects.requireNonNull(password);
    Objects.requireNonNull(salt);

    this.username = username;
    this.password = password;
    this.salt = salt;
  }

  /**
   * Returns the ID of the user.
   *
   * @return the user's ID
   */
  @Override
  public long id() {
    return this.id;
  }
}
