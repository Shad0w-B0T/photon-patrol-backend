package de.hdm.photon.database.postgresql.logging;

import de.hdm.photon.database.postgresql.hardware.HardwareEntity;
import de.hdm.photon.schema.logging.ILogging;
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

import java.util.Date;


@Entity
@Table(name="logging_entity",
    /*
     * The combination of the IP address and the timestamp has to be unique.
     * Otherwise, it's not possible to trace anything back.
    */
    uniqueConstraints={@UniqueConstraint(columnNames={"ip_address", "ip_timestamp"})})
@Getter
@Accessors(fluent=true)
public final class LoggingEntity implements ILogging {


  @Id
  @Column(name = "logging_id", nullable=false)
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  private long id;

  @Column(nullable=false, name="ip_address", length=100)
  @Setter
  private String ipAddress;

  @Column(nullable=false, name="ip_timestamp")
  @Setter
  private Date timestampOfIp;

  @ManyToOne
  @JoinColumn(name = "hardware_id", nullable = false)
  public HardwareEntity hardwareEntity;

  //Empty Constructor
  public LoggingEntity() {
  }

  //Constructor
  public LoggingEntity(final long id,
                       final String ipAddress,
                       final Date timestampOfIp) {
    this.id = id;
    this.ipAddress = ipAddress;
    this.timestampOfIp = timestampOfIp;
  }
}
