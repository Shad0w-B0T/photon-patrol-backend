package de.hdm.photon.database.postgresql.hardware;

import de.hdm.photon.schema.hardware.IHardware;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Date;


@Entity
@Table(name="hardware_entity")
@Getter
@Accessors(fluent=true)
public final class HardwareEntity implements IHardware {

  @Id
  @Column(nullable=false)
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  private long id;

  @Column(nullable=false, name="key", length=512)
  private String key;

  @Column(name="mac_address", length=100)
  @Setter
  private String macAddress;

  @Column(name="hardware_added")
  private Date hardwareAdded;

  @Column(name="mac_added")
  @Setter
  private Date macAdded;

  @Column(name="key_is_active")
  @Setter
  private boolean keyIsActive;

  //Empty Constructor
  public HardwareEntity() {
  }

  //Constructor
  public HardwareEntity(final long id,
                        final String key,
                        final String macAddress,
                        final Date hardwareAdded,
                        final Date macAdded,
                        final boolean keyIsActive) {
    this.id = id;
    this.key = key;
    this.macAddress = macAddress;
    this.hardwareAdded = hardwareAdded;
    this.macAdded = macAdded;
    this.keyIsActive = keyIsActive;
  }
}
