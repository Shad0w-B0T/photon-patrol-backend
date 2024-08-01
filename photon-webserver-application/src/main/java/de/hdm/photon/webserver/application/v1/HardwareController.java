package de.hdm.photon.webserver.application.v1;


import de.hdm.photon.logic.hardware.IHardwareManager;
import de.hdm.photon.schema.exception.FormatMismatchException;
import de.hdm.photon.schema.exception.EntityAlreadyPresentException;
import de.hdm.photon.schema.exception.EntityNotPresentException;
import de.hdm.photon.schema.hardware.IHardware;
import de.hdm.photon.schema.hardware.ImmutableHardware;
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


/**
 * Controller for handling hardware-related operations.
 */
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
              }
          )
      }
  )
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
                      schema=@Schema(implementation=IHardware.class)
                  )
              }
          ),
          @ApiResponse(
              responseCode="404",
              description="Hardware not found.",
              content={
                  @Content(
                      mediaType=MediaType.APPLICATION_JSON_VALUE,
                      schema=@Schema(implementation=IControllerMessage.class)
                  )
              }
          )
      }
  )
  public @NotNull ResponseEntity<IHardware> getSpecificHardware(@PathVariable(name="id") final long hardwareId)
      throws EntityNotPresentException {
    return this.body(200, this.hardwareManager.getHardware(hardwareId));
  }

  //-------------------------------------------------------POST---------------------------------------------------------

  /**
   * Creates a new hardware.
   *
   * @return ResponseEntity containing the newly created hardware.
   */
  @PostMapping
  @ResponseBody
  @Operation(
      summary="Create new hardware.",
      description="Method to create a new hardware.",
      responses={
          @ApiResponse(
              responseCode="201",
              description="Hardware was successfully created.",
              content={
                  @Content(
                      mediaType=MediaType.APPLICATION_JSON_VALUE,
                      schema=@Schema(implementation=IHardware.class)
                  )
              }
          )
      }
  )
  public @NotNull ResponseEntity<IHardware> createHardware() {
    return this.body(201, this.hardwareManager.createHardware());
  }

  //-------------------------------------------------------PUT----------------------------------------------------------

  /**
   * Update the settings of a specified hardware.
   *
   * @param hardwareId The ID of the hardware to update.
   * @param hardware   The hardware parameters to update.
   * @return ResponseEntity containing the updated hardware.
   * @throws EntityNotPresentException     If the hardware specified by hardwareId does not exist.
   * @throws FormatMismatchException       If the format of the input is incorrect.
   * @throws EntityAlreadyPresentException If the hardware already exists.
   */
  @PutMapping("/{id}")
  @ResponseBody
  @Operation(
      summary="Update hardware settings.",
      description="Method to update the settings of a specific hardware.",
      responses={
          @ApiResponse(
              responseCode="200",
              description="Parameters were successfully changed.",
              content={
                  @Content(
                      mediaType=MediaType.APPLICATION_JSON_VALUE,
                      schema=@Schema(implementation=IHardware.class)
                  )
              }
          )
      }
  )
  public @NotNull ResponseEntity<IHardware> updateHardware(
      @PathVariable(name="id") final long hardwareId,
      @RequestBody @NotNull ImmutableHardware hardware)
      throws EntityNotPresentException, FormatMismatchException, EntityAlreadyPresentException {
    return this.body(200, this.hardwareManager.updateHardware(
        hardwareId,
        hardware.macAddress(),
        hardware.hardwareAdded(),
        hardware.macAdded(),
        hardware.tokenIsActive()));
  }

  /**
   * Toggles the active state of a specified hardware.
   *
   * @param hardwareId The ID of the hardware to toggle.
   * @return ResponseEntity containing the updated hardware.
   * @throws EntityNotPresentException If the hardware specified by hardwareId does not exist.
   */
  @PutMapping("/{id}/toggle")
  @ResponseBody
  @Operation(
      summary="Toggle hardware active state.",
      description="Method to toggle the active state of a specific hardware.",
      responses={
          @ApiResponse(
              responseCode="200",
              description="Active state was successfully toggled.",
              content={
                  @Content(
                      mediaType=MediaType.APPLICATION_JSON_VALUE,
                      schema=@Schema(implementation=IHardware.class)
                  )
              }
          )
      }
  )
  public @NotNull ResponseEntity<IHardware> toggleHardware(@PathVariable(name="id") final long hardwareId)
      throws EntityNotPresentException {
    return this.body(200, this.hardwareManager.toggleHardwareActive(hardwareId));
  }

  //-------------------------------------------------------DELETE-------------------------------------------------------

  /**
   * Delete a specified hardware.
   *
   * @param hardwareId The ID of the hardware to delete.
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
                      schema=@Schema(implementation=IControllerMessage.class)
                  )
              }
          ),
          @ApiResponse(
              responseCode="500",
              description="Error while deleting hardware.",
              content={
                  @Content(
                      mediaType=MediaType.APPLICATION_JSON_VALUE,
                      schema=@Schema(implementation=IControllerMessage.class)
                  )
              }
          )
      }
  )
  public @NotNull ResponseEntity<IControllerMessage> deleteHardware(@PathVariable(name="id") final long hardwareId)
      throws EntityNotPresentException {
    if (this.hardwareManager.deleteHardware(hardwareId)) {
      return this.successMessage(200, String.format("Successfully deleted hardware %s.", hardwareId));
    }

    return this.errorMessage(500, String.format("Error while deleting hardware %s.", hardwareId));
  }
}
