package de.hdm.photon.webserver.application.v1;

import de.hdm.photon.logic.measurement.IMeasurementManager;
import de.hdm.photon.schema.exception.EntityNotPresentException;
import de.hdm.photon.schema.measurement.ITimePeriod;
import de.hdm.photon.webserver.application.controller.AbstractWebController;
import de.hdm.photon.webserver.application.controller.IControllerMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.log4j.Log4j2;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * {@code MeasurementController} is a Spring REST controller that handles HTTP requests related to measurements.
 * It provides endpoints for retrieving and deleting measurement data from InfluxDB.
 * <p>
 * This controller interacts with the {@link IMeasurementManager} to perform operations on measurement data.
 * </p>
 */
@RestController
@Log4j2
@RequestMapping("/api/v1/measurement")
@Tag(name="Measurements Endpoint", description="Get and delete measurements.")
public final class MeasurementController extends AbstractWebController {

  @Autowired
  private IMeasurementManager measurementManager;

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
      return this.body(200, this.measurementManager.getMeasurements(unixAfter));
    }

    return this.body(200, this.measurementManager.getMeasurements());
  }

  /**
   * Get all measurement names for a specific hardware after a specific Unix timestamp.
   *
   * @param hardware the hardware identifier
   * @param after    Unix timestamp in milliseconds
   * @return ResponseEntity containing a list of measurement names
   * @throws EntityNotPresentException if the hardware is not present
   */
  @GetMapping("/{hardware}")
  @ResponseBody
  @Operation(
      summary="Get all measurements for specific hardware after a Unix timestamp",
      description="Returns a list of measurement names stored in InfluxDB for the specified hardware that have data points after the specified timestamp.",
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
  public ResponseEntity<List<String>> getMeasurements(@PathVariable @NotNull String hardware,
                                                      @RequestParam(name="after", defaultValue="-1") final long after) throws EntityNotPresentException {
    //Get with param
    return this.body(200, this.measurementManager.getMeasurementsNameList(hardware, after));
  }

  /**
   * Get specific measurement data for a specific hardware after a specific Unix timestamp, grouped by a specific interval.
   *
   * @param hardware    the hardware identifier
   * @param measurement the measurement name
   * @param groupBy     grouping interval in milliseconds
   * @param after       Unix timestamp in milliseconds
   * @return ResponseEntity containing the measurement data
   * @throws EntityNotPresentException if the hardware or measurement is not present
   */
  @GetMapping("/{hardware}/{measurement}")
  @ResponseBody
  @Operation(
      summary="Get specific measurement data for specific hardware after a Unix timestamp, grouped by a specific interval",
      description="Returns the measurement data stored in InfluxDB for the specified hardware and measurement that have data points after the specified timestamp, grouped by the specified interval.",
      responses={
          @ApiResponse(
              responseCode="200",
              description="Measurement data",
              content={
                  @Content(
                      mediaType=MediaType.APPLICATION_JSON_VALUE,
                      schema=@Schema(implementation=ITimePeriod.class)
                  )
              }
          )
      }
  )
  public ResponseEntity<ITimePeriod<?>> getMeasurements(@PathVariable @NotNull String hardware,
                                                        @PathVariable @NotNull String measurement,
                                                        @RequestParam(name="groupBy", defaultValue="-1") final long groupBy,
                                                        @RequestParam(name="after", defaultValue="-1") final long after) throws EntityNotPresentException {
    //Get with param
    return this.body(200, this.measurementManager.getMeasurementsOfKey(hardware, measurement, groupBy, after));
  }

  //-------------------------------------------------------DELETE-------------------------------------------------------

  /**
   * Delete all measurements for a specific hardware.
   *
   * @param hardware the hardware identifier
   * @return ResponseEntity containing a success or error message
   * @throws EntityNotPresentException if the hardware is not present
   */
  @DeleteMapping("/{hardware}")
  @ResponseBody
  @Operation(
      summary="Delete all measurements for specific hardware",
      description="Deletes all measurement data stored in InfluxDB for the specified hardware.",
      responses={
          @ApiResponse(
              responseCode="200",
              description="Success or error message",
              content={
                  @Content(
                      mediaType=MediaType.APPLICATION_JSON_VALUE,
                      schema=@Schema(implementation=IControllerMessage.class)
                  )
              }
          )
      }
  )
  public ResponseEntity<IControllerMessage> deleteMeasurements(@PathVariable @NotNull String hardware) throws EntityNotPresentException {
    //Get with param
    if (this.measurementManager.deleteMeasurements(hardware)) {
      return this.successMessage(200, "Successfully deleted hardware="+hardware+".");
    }

    return this.errorMessage(500, "Something went wrong while deleting hardware="+hardware+".");
  }

  /**
   * Delete specific measurement data for a specific hardware.
   *
   * @param hardware    the hardware identifier
   * @param measurement the measurement name
   * @return ResponseEntity containing a success or error message
   * @throws EntityNotPresentException if the hardware or measurement is not present
   */
  @DeleteMapping("/{hardware}/{measurement}")
  @ResponseBody
  @Operation(
      summary="Delete specific measurement data for specific hardware",
      description="Deletes the specified measurement data stored in InfluxDB for the specified hardware.",
      responses={
          @ApiResponse(
              responseCode="200",
              description="Success or error message",
              content={
                  @Content(
                      mediaType=MediaType.APPLICATION_JSON_VALUE,
                      schema=@Schema(implementation=IControllerMessage.class)
                  )
              }
          )
      }
  )
  public ResponseEntity<IControllerMessage> deleteMeasurements(@PathVariable @NotNull String hardware,
                                                               @PathVariable @NotNull String measurement) throws EntityNotPresentException {
    //Get with param
    if (this.measurementManager.deleteMeasurements(hardware, measurement)) {
      return this.successMessage(200, "Successfully deleted points="+measurement+" from hardware="+hardware+".");
    }

    return this.errorMessage(500, "Something went wrong while deleting points="+measurement+" from hardware="+hardware+".");
  }
}
