package de.hdm.photon.webserver.application.web.v1.data;

import de.hdm.photon.logic.measurement.IMeasurementManager;
import de.hdm.photon.schema.exception.entity.EntityNotPresentException;
import de.hdm.photon.schema.measurement.IMeasurementTable;
import de.hdm.photon.schema.measurement.ITimePeriod;
import de.hdm.photon.schema.measurement.Measurement;
import de.hdm.photon.webserver.application.web.controller.AbstractWebController;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@Log4j2
@RequestMapping("/api/v1/measurement")
@Tag(name="Measurements Endpoint", description="Get measurements.")
public final class DataController extends AbstractWebController {

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


  @GetMapping("/{hardware}")
  @ResponseBody
  @Operation(
      summary="",
      description="",
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
  public ResponseEntity<IMeasurementTable> getMeasurements(@PathVariable @NotNull String hardware,
                                                           @RequestParam(name="after", defaultValue="-1") final long unixAfter,
                                                           @RequestParam(name="groupBy", defaultValue="-1") final long groupTime) {
    //Get with param
    if (unixAfter>0) {
      return this.body(200, this.measurementManager.getMeasurements(hardware, unixAfter, groupTime,
          List.of(Measurement.values())));
    }

    return this.body(200, this.measurementManager.getMeasurements(hardware, groupTime,
        List.of(Measurement.values())));
  }

  @GetMapping("/{hardware}/data")
  @ResponseBody
  @Operation(
      summary="",
      description="",
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

  @GetMapping("/{hardware}/data/{measurement}")
  @ResponseBody
  @Operation(
      summary="",
      description="",
      responses={
          @ApiResponse(
              responseCode="200",
              description="List values",
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
}
