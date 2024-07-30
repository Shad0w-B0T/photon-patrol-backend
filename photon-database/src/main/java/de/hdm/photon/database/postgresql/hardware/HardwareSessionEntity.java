package de.hdm.photon.database.postgresql.hardware;

import de.hdm.photon.schema.hardware.IHardwareSession;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.jetbrains.annotations.NotNull;

import java.util.Date;


@Entity
@Table(name="PhotonHardwareLog",
    /*
     * The combination of the IP address and the timestamp has to be unique.
     * Otherwise, it's not possible to trace anything back.
    */
    uniqueConstraints={@UniqueConstraint(columnNames={"ip_address", "ip_timestamp"})})
@Getter
@Accessors(fluent=true)
public final class HardwareSessionEntity implements IHardwareSession {
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  private Long id;

  @Column(nullable=false, name="ip_address", length=100)
  @Setter
  private String ipAddress;

  @Column(nullable=false, name="ip_timestamp")
  @Setter
  private Date timestamp;

  @ManyToOne(cascade=CascadeType.REMOVE)
  @JoinColumn(name="hardware_id", nullable=false)
  public HardwareEntity hardwareEntity;

  //Empty Constructor
  public HardwareSessionEntity() {
  }

  //Constructor
  public HardwareSessionEntity(@NotNull final HardwareEntity hardwareEntity,
                               @NotNull final String ipAddress,
                               @NotNull final Date timestamp) {
    this.hardwareEntity = hardwareEntity;
    this.ipAddress = ipAddress;
    this.timestamp = timestamp;
  }

  @Override
  public long id() {
    return this.id;
  }
}
