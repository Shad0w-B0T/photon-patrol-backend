package de.hdm.photon.database.postgresql.hardware.session;

import de.hdm.photon.database.postgresql.AbstractEntity;
import de.hdm.photon.database.postgresql.hardware.HardwareEntity;
import de.hdm.photon.schema.hardware.session.IHardwareSession;
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
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.jetbrains.annotations.NotNull;

import java.util.Date;


/**
 * Entity representing a hardware session in the database.
 */
@Entity
@Table(name="PhotonHardwareSession",
    /*
     * The combination of the IP address and the timestamp has to be unique.
     * Otherwise, it's not possible to trace anything back.
    */
    uniqueConstraints={@UniqueConstraint(columnNames={"ip_address", "ip_timestamp"})})
@NoArgsConstructor
@Getter
@Accessors(fluent=true)
public final class HardwareSessionEntity extends AbstractEntity implements IHardwareSession {
  @Column(nullable=false, name="ip_address", length=100)
  @Setter
  private String ipAddress;

  @Column(nullable=false, name="ip_timestamp")
  @Setter
  private Date timestamp;

  @ManyToOne(cascade={CascadeType.REMOVE, CascadeType.DETACH})
  @JoinColumn(name="hardware_id")
  public HardwareEntity hardwareEntity;

  /**
   * Constructs a HardwareSessionEntity with the specified values.
   *
   * @param hardwareEntity the associated hardware entity
   * @param ipAddress      the IP address
   * @param timestamp      the timestamp of the session
   */
  //Constructor
  public HardwareSessionEntity(@NotNull final HardwareEntity hardwareEntity,
                               @NotNull final String ipAddress,
                               @NotNull final Date timestamp) {
    this.hardwareEntity = hardwareEntity;
    this.ipAddress = ipAddress;
    this.timestamp = timestamp;
  }
}
