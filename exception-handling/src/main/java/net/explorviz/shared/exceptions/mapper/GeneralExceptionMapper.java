package net.explorviz.shared.exceptions.mapper;

import javax.inject.Inject;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import net.explorviz.shared.exceptions.ErrorObjectHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Final top-level generic exception mapper that prevents exception bleeding to the outside world.
 * {@link Throwable} and its sub classes are catched and transferred to a JSON-API compliant error
 * object.
 */
public class GeneralExceptionMapper implements ExceptionMapper<Throwable> {

  private static final Logger LOGGER = LoggerFactory.getLogger(GeneralExceptionMapper.class);

  private static final String CONTENT_TYPE = "Content-Type";
  private static final String MEDIA_TYPE = "application/json";

  private static final int HTTP_ERROR_CODE = 500;

  private final ErrorObjectHelper errorObjectHelper;

  @Inject
  public GeneralExceptionMapper(final ErrorObjectHelper errorObjectHelper) {
    this.errorObjectHelper = errorObjectHelper;
  }

  @Override
  public Response toResponse(final Throwable exception) {

    LOGGER.error("Error occured: HTTP Status={}", HTTP_ERROR_CODE, exception);

    final String errorString =
        this.errorObjectHelper.createErrorObjectString(HTTP_ERROR_CODE, "Unknown Server Error.",
            "Unknown Server Error. Please note the time and contact your administrator."); // NOPMD

    return Response.status(HTTP_ERROR_CODE).header(CONTENT_TYPE, MEDIA_TYPE).entity(errorString)
        .build();
  }
}
