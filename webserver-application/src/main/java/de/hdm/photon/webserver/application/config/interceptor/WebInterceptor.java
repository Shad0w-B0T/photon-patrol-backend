package de.hdm.photon.webserver.application.config.interceptor;

import de.hdm.photon.logic.heartbeat.IHeartbeatInfo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Optional;


/**
 * A Spring MVC interceptor for logging web request processing time.
 * This interceptor logs the duration of the request processing and its status after the completion of the request.
 */
@Log4j2
@Component
public final class WebInterceptor implements HandlerInterceptor {

  @Autowired
  private IHeartbeatInfo heartbeatInfo;

  private final static @NotNull String PROCESS_TIMESTAMP_FIELD = "process-begin";

  /**
   * Executed before the controller handles the request.
   *
   * @param request  The HTTP request being processed.
   * @param response The HTTP response being generated.
   * @param handler  The handler for the request (controller method).
   * @return true if the execution chain should proceed with the next interceptor or the handler itself.
   */
  @Override
  public boolean preHandle(@NotNull final HttpServletRequest request,
                           @NotNull final HttpServletResponse response,
                           @NotNull final Object handler) {
    // Set the current timestamp as the start time for request processing
    request.setAttribute(PROCESS_TIMESTAMP_FIELD, System.currentTimeMillis());
    return true;
  }

  /**
   * Executed after the request has been processed by the controller.
   *
   * @param request   The HTTP request being processed.
   * @param response  The HTTP response being generated.
   * @param handler   The handler for the request (controller method).
   * @param exception Any exception that occurred during processing, or null if none.
   */
  @Override
  public void afterCompletion(@NotNull final HttpServletRequest request,
                              @NotNull final HttpServletResponse response,
                              @NotNull final Object handler,
                              @Nullable final Exception exception) {
    // Calculate the duration of request processing
    final long duration = Optional
        .ofNullable(request.getAttribute(PROCESS_TIMESTAMP_FIELD))
        .map(o -> (long) o)
        .map(startTime -> System.currentTimeMillis()-startTime)
        .orElse(-1L);

    //Reset heartbeat timer.
    this.heartbeatInfo.reset();

    // Log the request processing duration and status
    log.info("[{}]'{}' took {} ms. (status={})",
        request.getMethod(),
        request.getRequestURI(),
        duration,
        response.getStatus());
  }
}