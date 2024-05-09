package de.hdm.photon.database.postgresql.logging;

import de.hdm.photon.schema.logging.ILogging;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
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
  @Column(nullable=false)
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  private Long id;

  @Column(nullable=false, name="ip_address", length=100)
  private String ipAddress;

  @Column(nullable=false, name="ip_timestamp")
  private Date timestampOfIp;

}
