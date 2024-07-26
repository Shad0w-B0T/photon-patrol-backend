package de.hdm.photon.webserver.application.security;

import de.hdm.photon.schema.exception.EntityAlreadyPresentException;
import de.hdm.photon.schema.exception.EntityNotPresentException;
import de.hdm.photon.schema.hardware.IHardware;
import de.hdm.photon.webserver.application.controller.AbstractWebController;
import de.hdm.photon.webserver.application.controller.IControllerMessage;
import de.hdm.photon.webserver.logic.user.IUserManager;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.criteria.CriteriaBuilder.In;
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
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Log4j2
@RequestMapping("/api")
@Tag(name="", description="")
public final class LoginController extends AbstractWebController {

  @Autowired
  private IUserManager userManager;

  @Autowired
  private AuthenticationManager authenticationManager;

  @GetMapping("/state")
  @ResponseBody
  @Operation(
      summary="",
      description="",
      responses={
          @ApiResponse(
              responseCode="200",
              description="",
              content={
                  @Content(
                      mediaType=MediaType.APPLICATION_JSON_VALUE,
                      array=@ArraySchema(schema=@Schema(implementation=IHardware.class))
                  )
              })
      })
  public ResponseEntity<InfoResponse> state(@Nullable final HttpServletRequest request) {
    final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    boolean isAuthenticated = authentication != null
        && authentication.isAuthenticated() &&
        !(authentication instanceof AnonymousAuthenticationToken);

    return ResponseEntity.ok(new InfoResponse(!this.userManager.getPersistentUsers().isEmpty(), isAuthenticated));
  }

  @PostMapping("/login")
  @ResponseBody
  @Operation(
      summary="",
      description="",
      responses={
          @ApiResponse(
              responseCode="200",
              description="",
              content={
                  @Content(
                      mediaType=MediaType.APPLICATION_JSON_VALUE,
                      array=@ArraySchema(schema=@Schema(implementation=IHardware.class))
                  )
              })
      })
  public ResponseEntity<IControllerMessage> login(@RequestBody @NotNull LoginRequest loginRequest,
                                                  @NotNull final HttpServletRequest request,
                                                  @NotNull final HttpServletResponse response) {
    final Authentication authenticationRequest =
        UsernamePasswordAuthenticationToken.unauthenticated(loginRequest.username(), loginRequest.password());
    try {
      final Authentication authenticationResponse =
          this.authenticationManager.authenticate(authenticationRequest);

      if (authenticationResponse.isAuthenticated()) {
        SecurityContextHolder.getContext().setAuthentication(authenticationResponse);
        request.getSession().setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, SecurityContextHolder.getContext());

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

  @PostMapping("/register")
  @ResponseBody
  @Operation(
      summary="",
      description="",
      responses={
          @ApiResponse(
              responseCode="200",
              description="",
              content={
                  @Content(
                      mediaType=MediaType.APPLICATION_JSON_VALUE,
                      array=@ArraySchema(schema=@Schema(implementation=IHardware.class))
                  )
              })
      })
  public ResponseEntity<IControllerMessage> register(@RequestBody @NotNull RegisterRequest registerRequest) throws EntityAlreadyPresentException {
    this.userManager.createPersistentUser(registerRequest.username, registerRequest.password);

    return this.successMessage(200, "Register successful");
  }

  @PostMapping("/logout")
  @ResponseBody
  @Operation(
      summary="",
      description="",
      responses={
          @ApiResponse(
              responseCode="200",
              description="",
              content={
                  @Content(
                      mediaType=MediaType.APPLICATION_JSON_VALUE,
                      array=@ArraySchema(schema=@Schema(implementation=IHardware.class))
                  )
              })
      })
  public ResponseEntity<IControllerMessage> logout(HttpServletRequest request, HttpServletResponse response) {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();

    if (auth != null) {
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

  public record InfoResponse(boolean registered, boolean validSession) {
  }

  public record LoginRequest(String username, String password) {
  }

  public record RegisterRequest(String username, String password) {
  }

}
