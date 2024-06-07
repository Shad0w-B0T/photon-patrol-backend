package de.hdm.photon.webserver.application.web.v1.hardware;


import de.hdm.photon.schema.hardware.IHardware;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.log4j.Log4j2;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@Log4j2
@RequestMapping("/api/v1/hardware")
@Tag(name = "Hardware Endpoint", description = "Manipulate Hardware.")
public class HardwareController {
  

  //-------------------------------------------------------GET----------------------------------------------------------

  @GetMapping
  @ResponseBody
  @Operation(
      summary="Get all hardware.",
      description="Method to get all hardware.",
      responses={
          @ApiResponse(
              responseCode="200",
              description="List of hardware was successfully retrieved.",
              content={
                  @Content(
                      mediaType=MediaType.APPLICATION_JSON_VALUE,
                      array=@ArraySchema(schema=@Schema(implementation=IHardware.class))
                  )
              })
      })
  public @NotNull ResponseEntity<List<IHardware>> getHardware(){
    return null;
  }


  //-------------------------------------------------------POST---------------------------------------------------------


  //-------------------------------------------------------PUT----------------------------------------------------------

  //-------------------------------------------------------DELETE-------------------------------------------------------


}
