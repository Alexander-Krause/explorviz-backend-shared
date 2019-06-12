package net.explorviz.shared.exceptions.mapper;

import com.github.jasminb.jsonapi.exceptions.UnregisteredTypeException;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import net.explorviz.shared.exceptions.ErrorObjectHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Exception mapper for handling {@link UnregisteredTypeException}.
 */
public class UnregisteredTypeExceptionMapper implements ExceptionMapper<UnregisteredTypeException> {

  private static final Logger LOGGER =
      LoggerFactory.getLogger(UnregisteredTypeExceptionMapper.class);

  private static final String CONTENT_TYPE = "Content-Type";
  private static final String MEDIA_TYPE = "application/json";

  private static final int HTTP_STATUS = 400;

  private static final String DEFAULT_ERROR_TITLE = "Bad Request";
  private static final String DEFAULT_ERROR_DETAIL = "Unkown JSON API Type";

  private final ErrorObjectHelper errorObjectHelper;

  @Inject
  public UnregisteredTypeExceptionMapper(final ErrorObjectHelper errorObjectHelper) {
    this.errorObjectHelper = errorObjectHelper;
  }

  @Override
  public Response toResponse(final UnregisteredTypeException exception) {

    LOGGER.error("Error occured: HTTP Status={}", HTTP_STATUS, exception);

    final String errorString = this.errorObjectHelper.createErrorObjectString(HTTP_STATUS,
        DEFAULT_ERROR_TITLE, DEFAULT_ERROR_DETAIL);

    return Response.status(HTTP_STATUS).header(CONTENT_TYPE, MEDIA_TYPE).entity(errorString)
        .build();
  }

  public static String getDefaultErrorTitle() {
    return DEFAULT_ERROR_TITLE;
  }

  public static String getDefaultErrorDetail() {
    return DEFAULT_ERROR_DETAIL;
  }
}
