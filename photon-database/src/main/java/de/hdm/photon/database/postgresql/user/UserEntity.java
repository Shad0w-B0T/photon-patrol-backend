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
import lombok.experimental.Accessors;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;


@Entity
@Table(name="PhotonUser")
@NoArgsConstructor
@Getter
@Accessors(fluent=true)
public final class UserEntity implements IUser {
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  private long id;

  @Column(name="username", nullable=false)
  private String username;

  @Column(name="password", nullable=false, length=2048)
  private String password;

  @Column(name="salt", nullable=false, length=1024)
  private String salt;

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
}
