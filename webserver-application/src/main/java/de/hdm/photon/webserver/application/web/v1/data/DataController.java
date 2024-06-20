package de.hdm.photon.webserver.application.web.v1.data;

import de.hdm.photon.database.influxdb.connection.IInfluxConnection;
import de.hdm.photon.schema.hardware.IHardware;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.log4j.Log4j2;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@Log4j2
@RequestMapping("/api/v1/measurements")
@Tag(name="Measurements Endpoint", description="Get measurements.")
public final class DataController {

  @Autowired
  private IInfluxConnection influxConnection;

  //-------------------------------------------------------GET----------------------------------------------------------

  /**
   * Get all data after a specific Unix timestamp.
   *
   * @param unixAfter Unix timestamp in milliseconds
   * @return ResponseEntity containing a list of measurement names
   */
  @GetMapping
  @ResponseBody
  @Operation(
      summary="Get all measurements after a Unix timestamp",
      description="Returns a list of measurement names stored in InfluxDB that have data points after the specified timestamp.",
      responses={
          @ApiResponse(
              responseCode="200",
              description="List of measurement names",
              content={
                  @Content(
                      mediaType=MediaType.APPLICATION_JSON_VALUE,
                      array=@ArraySchema(schema=@Schema(implementation=String.class))
                  )
              }
          )
      }
  )
  public ResponseEntity<List<String>> getMeasurements(@RequestParam(name="after", defaultValue="-1") final long unixAfter) {
    //Get with param
    if (unixAfter>0) {
      return ResponseEntity.ok(this.influxConnection.getMeasurementsAfter(unixAfter));
    }

    return ResponseEntity.ok(this.influxConnection.getMeasurements());
  }
}
