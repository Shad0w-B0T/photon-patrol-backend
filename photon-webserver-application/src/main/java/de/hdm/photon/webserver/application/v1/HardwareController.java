package de.hdm.photon.webserver.application.v1;


import de.hdm.photon.logic.hardware.HardwareManager;
import de.hdm.photon.logic.hardware.IHardwareManager;
import de.hdm.photon.schema.exception.FormatMismatchException;
import de.hdm.photon.schema.exception.EntityAlreadyPresentException;
import de.hdm.photon.schema.exception.EntityNotPresentException;
import de.hdm.photon.schema.hardware.IHardware;
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
import org.jetbrains.annotations.Nullable;
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

import java.util.Date;
import java.util.List;


@RestController
@Log4j2
@RequestMapping("/api/v1/hardware")
@Tag(name="Hardware Endpoint", description="Manipulate Hardware.")
public final class HardwareController extends AbstractWebController {
  @Autowired
  private IHardwareManager hardwareManager;

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
  public @NotNull ResponseEntity<List<IHardware>> getHardware() {
    return this.body(200, this.hardwareManager.getHardwareList());
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
  public @NotNull ResponseEntity<IHardware> getSpecificHardware(@PathVariable(name="id") final long hardwareId) throws EntityNotPresentException {
    return this.body(200, this.hardwareManager.getHardware(hardwareId));
  }


  //-------------------------------------------------------POST---------------------------------------------------------

  /**
   * Creates a new hardware.
   * see {@link HardwareManager#createHardware()}
   *
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
  public @NotNull ResponseEntity<IHardware> createHardware() throws EntityNotPresentException, EntityAlreadyPresentException, FormatMismatchException {
    return this.body(201, this.hardwareManager.createHardware());
  }

  //-------------------------------------------------------PUT----------------------------------------------------------

  /**
   * Update the settings of a specified hardware.
   *
   * @param hardwareId    The ID of the hardware to retrieve.
   * @param macAddress    change the macAddress.
   * @param hardwareAdded change the date of when the hardware was added.
   * @param macAdded      change the macAdded.
   * @param tokenIsActive change the state of the token.
   * @return ResponseEntity containing the updated state of a token.
   * @throws EntityNotPresentException If the hardware specified by hardwareId does not exist.
   */
  @PutMapping("/{id}")
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
  public @NotNull ResponseEntity<IHardware> updateHardware(@PathVariable(name="id") final long hardwareId,
                                                           @RequestBody @Nullable String macAddress,
                                                           @RequestBody @Nullable Date hardwareAdded,
                                                           @RequestBody @Nullable Date macAdded,
                                                           @RequestBody final boolean tokenIsActive)
      throws EntityNotPresentException, FormatMismatchException, EntityAlreadyPresentException {
    return this.body(200,
        this.hardwareManager.updateHardware(hardwareId, macAddress, hardwareAdded, macAdded, tokenIsActive));
  }

//Todo:Docs
  @PutMapping("/{id}/toggle")
  @ResponseBody
  @Operation(
      summary="",
      description="",
      responses={
          @ApiResponse(
              responseCode="200",
              description="parameters were successfully changed.",
              content={
                  @Content(
                      mediaType=MediaType.APPLICATION_JSON_VALUE,
                     schema=@Schema(implementation=IHardware.class)
                  )
              })
      })
  public @NotNull ResponseEntity<IHardware> toggleHardware(@PathVariable(name="id") final long hardwareId)
      throws EntityNotPresentException {
    return this.body(200, this.hardwareManager.toggleHardwareActive(hardwareId));
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
                      array=@ArraySchema(schema=@Schema(implementation=IControllerMessage.class))
                  )
              })
      })
  public @NotNull ResponseEntity<IControllerMessage> deleteSpecificHardware(@PathVariable(name="id") final long hardwareId)
      throws EntityNotPresentException {
    if (this.hardwareManager.deleteHardware(hardwareId)) {
      return this.successMessage(200, String.format("Successfully deleted hardware %s.", hardwareId));
    }

    return this.errorMessage(500, String.format("Error while deleting hardware %s.", hardwareId));
  }

}
