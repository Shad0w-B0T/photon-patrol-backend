package de.hdm.photon.database.postgresql.hardware;

import de.hdm.photon.schema.hardware.IHardware;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
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
  private Long id;

  @Column(nullable=false, name="key", length=512)
  private String key;

  @Column(name="mac_address", length=100)
  private String macAddress;

  @Column(name="hardware_added")
  private Date hardwareAdded;

  @Column(name="mac_added")
  private Date macAdded;

  @Column(name="key_is_active")
  private boolean keyIsActive;

}
