package net.explorviz.shared.exceptions.mapper;

import javax.inject.Inject;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import net.explorviz.shared.exceptions.ErrorObjectHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Top-level exception mapper that prevents exception bleeding to the outside world.
 * {@link WebApplicationException} and its sub classes are catched and transferred to a JSON-API
 * compliant error object.
 */
public class WebApplicationExceptionMapper implements ExceptionMapper<WebApplicationException> {

  private static final Logger LOGGER = LoggerFactory.getLogger(WebApplicationExceptionMapper.class);

  private final ErrorObjectHelper errorObjectHelper;

  @Inject
  public WebApplicationExceptionMapper(final ErrorObjectHelper errorObjectHelper) {
    this.errorObjectHelper = errorObjectHelper;
  }

  @Override
  public Response toResponse(final WebApplicationException exception) {

    final int httpStatus = exception.getResponse().getStatus();

    LOGGER.error("Error occured: HTTP Status={}", httpStatus, exception);

    final String errorString = this.errorObjectHelper.createErrorObjectString(httpStatus,
        "An error occured", exception.getMessage());

    Response.ResponseBuilder
        response = Response.status(httpStatus).header("Content-Type", "application/json")
        .entity(errorString);

    if (httpStatus == 401) {
      response.header("WWW-Authenticate", "realm='ExplorViz Secured'");
    }

    return response.build();
  }
}
