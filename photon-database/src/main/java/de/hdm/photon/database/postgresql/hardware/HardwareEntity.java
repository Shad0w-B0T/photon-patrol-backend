package de.hdm.photon.database.postgresql.hardware;

import de.hdm.photon.schema.hardware.IHardware;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Date;
import java.util.Set;


@Entity
@Table(name="PhotonHardware")
@Getter
@Accessors(fluent=true)
public final class HardwareEntity implements IHardware {

  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  private Long id;

  @Column(nullable=false, name="token", length=512)
  private String token;

  @Column(name="mac_address", length=100)
  @Setter
  private String macAddress;

  @Column(name="hardware_added")
  @Setter
  private Date hardwareAdded;

  @Column(name="mac_added")
  @Setter
  private Date macAdded;

  @Column(name="token_is_active")
  @Setter
  private boolean tokenIsActive;

  /**
   * "@ManyToOne / @OneToMany"
   * Lesen: Weise eine HardwareEntity (one) to LoggingEntity (many) zu.
   * Man ist in der HardwareEntity Klasse und weist diese der LoggingEntity zu.
   */
  @OneToMany(cascade=CascadeType.DETACH)
  @JoinColumn(name = "hardware_id")
  private Set<HardwareSessionEntity> loggingEntity;

  //Empty Constructor
  public HardwareEntity() {
  }

  //Constructor
  public HardwareEntity(@NotNull final String token,
                        @Nullable final String macAddress,
                        @NotNull final Date hardwareAdded,
                        @Nullable final Date macAdded,
                        final boolean tokenIsActive) {
    this.token = token;
    this.macAddress = macAddress;
    this.hardwareAdded = hardwareAdded;
    this.macAdded = macAdded;
    this.tokenIsActive = tokenIsActive;
  }

  @Override
  public long id() {
    return this.id;
  }
}
