package de.hdm.photon.webserver.application.web.v1.hardware;


import de.hdm.photon.schema.exception.auth.FormatMismatchException;
import de.hdm.photon.schema.hardware.IHardware;
import de.hdm.photon.webserver.application.web.response.IResponseMessage;
import de.hdm.photon.webserver.logic.hardware.IHardwareManager;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.log4j.Log4j2;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springdoc.core.service.RequestBodyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import de.hdm.photon.schema.exception.entity.EntityNotPresentException;
import de.hdm.photon.schema.exception.entity.EntityAlreadyPresentException;

import java.util.Date;
import java.util.List;


@RestController
@Log4j2
@RequestMapping("/api/v1/hardware")
@Tag(name = "Hardware Endpoint", description = "Manipulate Hardware.")
public final class HardwareController {
  @Autowired
  private IHardwareManager hardwareManager;
  @Autowired
  private RequestBodyService requestBodyBuilder;

  //-------------------------------------------------------GET----------------------------------------------------------
  /**
   * Retrieves all hardware.
   *
   * @return ResponseEntity containing a list of hardware.
   */
  @GetMapping
  @ResponseBody
  @Operation(
      summary="Get all hardware.",
      description="Method to get all hardware.",
      responses={
          @ApiResponse(
              responseCode="200",
              description="List of all hardware was successfully retrieved.",
              content={
                  @Content(
                      mediaType=MediaType.APPLICATION_JSON_VALUE,
                      array=@ArraySchema(schema=@Schema(implementation=IHardware.class))
                  )
              })
      })
  public @NotNull ResponseEntity<List<IHardware>> getHardware(){
    return ResponseEntity.ok(this.hardwareManager.getHardwareList());
  }



  /**
   * Retrieves a specific hardware.
   *
   * @param hardwareId The ID of the hardware to retrieve.
   * @return ResponseEntity containing the specific hardware.
   * @throws EntityNotPresentException If the hardware specified by hardwareId does not exist.
   */
  @GetMapping("/{id}")
  @ResponseBody
  @Operation(
      summary="Get a specific hardware.",
      description="Method to get a specific hardware.",
      responses={
          @ApiResponse(
              responseCode="200",
              description="List of a specific hardware was successfully retrieved.",
              content={
                  @Content(
                      mediaType=MediaType.APPLICATION_JSON_VALUE,
                      array=@ArraySchema(schema=@Schema(implementation=IHardware.class))
                  )
              })
      })
  public @NotNull ResponseEntity<IHardware> getSpecificHardware(@PathVariable final long hardwareId) throws EntityNotPresentException{
    return ResponseEntity.ok(this.hardwareManager.getHardware(hardwareId));
  }



  /**
   * Retrieves the metric of a specific hardware.
   *
   * @param hardwareId The ID of the hardware to retrieve.
   * @return ResponseEntity containing a list of metrics from the specific hardware.
   * @throws EntityNotPresentException If the hardware specified by hardwareId does not exist.
   */
  @GetMapping("/{id}/metric")
  @ResponseBody
  @Operation(
      summary="Get all metric of hardware.",
      description="Method to get all metric of a specific hardware.",
      responses={
          @ApiResponse(
              responseCode="200",
              description="List of all metric was successfully retrieved.",
              content={
                  @Content(
                      mediaType=MediaType.APPLICATION_JSON_VALUE,
                      array=@ArraySchema(schema=@Schema(implementation=IHardware.class))
                  )
              })
      })
  public @NotNull ResponseEntity<List<IHardware>> getMetricOfHardware(@PathVariable final long hardwareId)throws EntityNotPresentException{
   //return ResponseEntity.ok(this.hardwareManager.);
    return null;
  }



  /**
   * Retrieves a specific metric of a hardware.
   *
   * @param hardwareId The ID of the hardware to retrieve.
   * @param specificMetric to retrieve the specific metric of the hardware.
   * @return ResponseEntity containing specific metric of a hardware.
   * @throws EntityNotPresentException If the hardware specified by hardwareId does not exist.
   */
  @GetMapping("/{id}/metric/{metrics}")
  @ResponseBody
  @Operation(
      summary="Get a specific metric of hardware.",
      description="Method to get a specific metric of a hardware.",
      responses={
          @ApiResponse(
              responseCode="200",
              description="List of a specific metric was successfully retrieved.",
              content={
                  @Content(
                      mediaType=MediaType.APPLICATION_JSON_VALUE,
                      array=@ArraySchema(schema=@Schema(implementation=IHardware.class))
                  )
              })
      })
  public @NotNull ResponseEntity<List<IHardware>> getSpecificMetricOfHardware(@PathVariable final long hardwareId,
                                                                              @PathVariable final long specificMetric)throws EntityNotPresentException{
    return null;
  }







  //-------------------------------------------------------POST---------------------------------------------------------

  /**
   * Creates a new hardware.
   *
   * @param loggingId The ID of the loggingEntity to retrieve.
   * @param token set the token.
   * @param macAddress set the macAddress.
   * @param hardwareAdded set the date of when the hardware was added.
   * @param macAdded set the macAdded.
   * @param tokenIsActive set the state of the token.
   * @return ResponseEntity containing the newly created hardware.
   * @throws EntityNotPresentException If the hardware specified by hardwareId does not exist.
   */
  @PostMapping
  @ResponseBody
  @Operation(
      summary="Create new hardware.",
      description="Method to create a new hardware.",
      responses={
          @ApiResponse(
              responseCode="200",
              description="Hardware was successfully created.",
              content={
                  @Content(
                      mediaType=MediaType.APPLICATION_JSON_VALUE,
                      array=@ArraySchema(schema=@Schema(implementation=IHardware.class))
                  )
              })
      })
  public @NotNull ResponseEntity<IHardware> createNewHardware(@PathVariable final long loggingId,
                                                              @RequestBody @Nullable String token,
                                                              @RequestBody @Nullable String macAddress,
                                                              @RequestBody @Nullable Date hardwareAdded,
                                                              @RequestBody @Nullable Date macAdded,
                                                              @RequestBody boolean tokenIsActive)
      throws EntityNotPresentException, EntityAlreadyPresentException, FormatMismatchException {
      return ResponseEntity.ok(this.hardwareManager.createHardware(loggingId, token, macAddress, hardwareAdded,
          macAdded, tokenIsActive));
  }

  //-------------------------------------------------------PUT----------------------------------------------------------

  /**
   * Update the settings of a specified hardware.
   *
   * @param hardwareId The ID of the hardware to retrieve.
   * @param macAddress change the macAddress.
   * @param hardwareAdded change the date of when the hardware was added.
   * @param macAdded change the macAdded.
   * @param tokenIsActive change the state of the token.
   * @return ResponseEntity containing the updated state of a token.
   * @throws EntityNotPresentException If the hardware specified by hardwareId does not exist.
   */
  @PutMapping("/{id}/edit")
  @ResponseBody
  @Operation(
      summary="update hardware stats.",
      description="Method to change the stats of a specific hardware.",
      responses={
          @ApiResponse(
              responseCode="200",
              description="parameters were successfully changed.",
              content={
                  @Content(
                      mediaType=MediaType.APPLICATION_JSON_VALUE,
                      array=@ArraySchema(schema=@Schema(implementation=IHardware.class))
                  )
              })
      })
  public @NotNull ResponseEntity<IHardware> updateHardware(@PathVariable final long hardwareId,
                                                           @RequestBody @Nullable String macAddress,
                                                           @RequestBody @Nullable Date hardwareAdded,
                                                           @RequestBody @Nullable Date macAdded,
                                                           @RequestBody final boolean tokenIsActive)
      throws EntityNotPresentException, FormatMismatchException, EntityAlreadyPresentException {
    return ResponseEntity.ok(this.hardwareManager.updateHardware(hardwareId, macAddress, hardwareAdded, macAdded,
        tokenIsActive));
  }
  //-------------------------------------------------------DELETE-------------------------------------------------------

  /**
   * Delete a specified hardware.
   *
   * @param hardwareId The ID of the hardware to retrieve.
   * @return ResponseEntity containing a response message indicating the result of the operation.
   * @throws EntityNotPresentException If the hardware specified by hardwareId does not exist.
   */
  @DeleteMapping("/{id}")
  @ResponseBody
  @Operation(
      summary="Delete specific hardware.",
      description="Method to delete a specific hardware.",
      responses={
          @ApiResponse(
              responseCode="200",
              description="Hardware was successfully deleted.",
              content={
                  @Content(
                      mediaType=MediaType.APPLICATION_JSON_VALUE,
                      array=@ArraySchema(schema=@Schema(implementation=IHardware.class))
                  )
              })
      })
  public @NotNull ResponseEntity<Boolean> deleteSpecificHardware(@PathVariable final long hardwareId)
      throws EntityNotPresentException {
    return ResponseEntity.ok(this.hardwareManager.deleteHardware(hardwareId));
  }

}
