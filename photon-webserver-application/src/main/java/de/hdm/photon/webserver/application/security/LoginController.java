package de.hdm.photon.webserver.application.security;

import de.hdm.photon.schema.exception.EntityAlreadyPresentException;
import de.hdm.photon.schema.user.ImmutableUserCredentials;
import de.hdm.photon.webserver.application.controller.AbstractWebController;
import de.hdm.photon.webserver.application.controller.IControllerMessage;
import de.hdm.photon.webserver.logic.user.IUserManager;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for handling login-related operations.
 */
@RestController
@Log4j2
@RequestMapping("/api")
@Tag(name="LoginController", description="Controller for managing login operations")
public final class LoginController extends AbstractWebController {

  @Autowired
  private IUserManager userManager;

  @Autowired
  private AuthenticationManager authenticationManager;

  /**
   * Endpoint to check the state of the current session and user registration.
   *
   * @return ResponseEntity containing InfoResponse with user registration and session validity status.
   */
  @GetMapping("/state")
  @ResponseBody
  @Operation(
      summary="Check session state",
      description="Checks if there is a valid session and if any user is registered.",
      responses={
          @ApiResponse(
              responseCode="200",
              description="State information retrieved successfully",
              content=@Content(
                  mediaType=MediaType.APPLICATION_JSON_VALUE,
                  array=@ArraySchema(schema=@Schema(implementation=InfoResponse.class))
              )
          )
      }
  )
  public ResponseEntity<InfoResponse> state() {
    final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    boolean isAuthenticated = authentication != null
        && authentication.isAuthenticated()
        && !(authentication instanceof AnonymousAuthenticationToken);

    final boolean anyUser = !this.userManager.getPersistentUsers().isEmpty();

    return ResponseEntity.ok(new InfoResponse(anyUser, isAuthenticated));
  }

  /**
   * Endpoint for user login.
   *
   * @param userCredentials User credentials.
   * @param request         HTTP request.
   * @param response        HTTP response.
   * @return ResponseEntity containing IControllerMessage indicating login success or failure.
   */
  @PostMapping("/login")
  @ResponseBody
  @Operation(
      summary="User login",
      description="Logs in a user with provided credentials.",
      responses={
          @ApiResponse(
              responseCode="200",
              description="Login successful",
              content=@Content(
                  mediaType=MediaType.APPLICATION_JSON_VALUE,
                  array=@ArraySchema(schema=@Schema(implementation=IControllerMessage.class))
              )
          ),
          @ApiResponse(
              responseCode="401",
              description="Invalid username or password",
              content=@Content(
                  mediaType=MediaType.APPLICATION_JSON_VALUE,
                  array=@ArraySchema(schema=@Schema(implementation=IControllerMessage.class))
              )
          )
      }
  )
  public ResponseEntity<IControllerMessage> login(@RequestBody @NotNull ImmutableUserCredentials userCredentials,
                                                  @NotNull final HttpServletRequest request,
                                                  @NotNull final HttpServletResponse response) {
    return this.validateAuth(userCredentials.username(), userCredentials.password(), request, response);
  }

  /**
   * Endpoint for user registration.
   *
   * @param userCredentials User credentials.
   * @param request         HTTP request.
   * @param response        HTTP response.
   * @return ResponseEntity containing IControllerMessage indicating registration and login success or failure.
   * @throws EntityAlreadyPresentException if the user already exists.
   */
  @PostMapping("/register")
  @ResponseBody
  @Operation(
      summary="User registration",
      description="Registers a new user with provided credentials.",
      responses={
          @ApiResponse(
              responseCode="200",
              description="Registration and login successful",
              content=@Content(
                  mediaType=MediaType.APPLICATION_JSON_VALUE,
                  array=@ArraySchema(schema=@Schema(implementation=IControllerMessage.class))
              )
          )
      }
  )
  public ResponseEntity<IControllerMessage> register(@RequestBody @NotNull ImmutableUserCredentials userCredentials,
                                                     @NotNull final HttpServletRequest request,
                                                     @NotNull final HttpServletResponse response) throws EntityAlreadyPresentException {
    this.userManager.createPersistentUser(userCredentials.username(), userCredentials.password());
    return this.validateAuth(userCredentials.username(), userCredentials.password(), request, response);
  }

  /**
   * Endpoint for user logout.
   *
   * @param request  HTTP request.
   * @param response HTTP response.
   * @return ResponseEntity containing IControllerMessage indicating logout success or failure.
   */
  @PostMapping("/logout")
  @ResponseBody
  @Operation(
      summary="User logout",
      description="Logs out the current user.",
      responses={
          @ApiResponse(
              responseCode="200",
              description="Logout successful",
              content=@Content(
                  mediaType=MediaType.APPLICATION_JSON_VALUE,
                  array=@ArraySchema(schema=@Schema(implementation=IControllerMessage.class))
              )
          ),
          @ApiResponse(
              responseCode="401",
              description="No current session",
              content=@Content(
                  mediaType=MediaType.APPLICATION_JSON_VALUE,
                  array=@ArraySchema(schema=@Schema(implementation=IControllerMessage.class))
              )
          )
      }
  )
  public ResponseEntity<IControllerMessage> logout(HttpServletRequest request, HttpServletResponse response) {
    final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (authentication != null) {
      // Invalidate session
      request.getSession().invalidate();

      // Remove the session cookie
      Cookie cookie = new Cookie("JSESSIONID", null);
      cookie.setPath("/");
      cookie.setHttpOnly(true);
      cookie.setMaxAge(0);
      response.addCookie(cookie);

      // Clear the security context
      SecurityContextHolder.clearContext();

      return this.successMessage(200, "Logout successful");
    }
    return this.errorMessage(401, "No current session");
  }


  /**
   * A record that represents an information response with registration and session validity status.
   *
   * @param registered   Indicates if the user is registered.
   * @param validSession Indicates if the current session is valid.
   */
  @Schema(description="Represents an information response with registration and session validity status.")
  public record InfoResponse(
      @Schema(description="Indicates if the user is registered.", example="true")
      boolean registered,
      @Schema(description="Indicates if the current session is valid.", example="true")
      boolean validSession
  ) {
  }

  /**
   * Validates user authentication using the provided username and password.
   *
   * @param username The username of the user attempting to authenticate. Can be null.
   * @param password The password of the user attempting to authenticate. Can be null.
   * @param request  The HTTP servlet request. Must not be null.
   * @param response The HTTP servlet response. Must not be null.
   * @return A {@link ResponseEntity} containing an {@link IControllerMessage} indicating the result of the authentication attempt.
   */
  private ResponseEntity<IControllerMessage> validateAuth(@Nullable final String username,
                                                          @Nullable final String password,
                                                          @NotNull final HttpServletRequest request,
                                                          @NotNull final HttpServletResponse response) {

    final Authentication authenticationRequest =
        UsernamePasswordAuthenticationToken.unauthenticated(username, password);
    try {
      final Authentication authenticationResponse =
          this.authenticationManager.authenticate(authenticationRequest);

      if (authenticationResponse.isAuthenticated()) {
        SecurityContextHolder.getContext().setAuthentication(authenticationResponse);
        request.getSession().setAttribute(
            HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
            SecurityContextHolder.getContext()
        );

        final String sessionId = request.getSession().getId();
        final Cookie sessionCookie = new Cookie("JSESSIONID", sessionId);
        sessionCookie.setHttpOnly(true);
        sessionCookie.setPath("/");
        response.addCookie(sessionCookie);
        return this.successMessage(200, "Login successful");
      }
    } catch (Exception e) {
      return this.successMessage(401, "Invalid username or password");
    }

    return this.successMessage(401, "Invalid username or password");
  }
}
