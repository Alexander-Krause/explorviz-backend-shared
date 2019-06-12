package net.explorviz.shared.exceptions.mapper;

import com.github.jasminb.jsonapi.exceptions.InvalidJsonApiResourceException;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import net.explorviz.shared.exceptions.ErrorObjectHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Exception mapper for {@link InvalidJsonApiResourceException}.
 */
public class InvalidJsonApiResourceExceptionMapper
    implements ExceptionMapper<InvalidJsonApiResourceException> {

  private static final Logger LOGGER =
      LoggerFactory.getLogger(InvalidJsonApiResourceExceptionMapper.class);

  private static final String CONTENT_TYPE = "Content-Type";
  private static final String MEDIA_TYPE = "application/json";
  
  private static final int HTTP_STATUS = 400;

  private final ErrorObjectHelper errorObjectHelper;

  @Inject
  public InvalidJsonApiResourceExceptionMapper(final ErrorObjectHelper errorObjectHelper) {
    this.errorObjectHelper = errorObjectHelper;
  }

  @Override
  public Response toResponse(final InvalidJsonApiResourceException exception) {

    LOGGER.error("Error occured: HTTP Status={}", HTTP_STATUS, exception);

    final String errorString = this.errorObjectHelper.createErrorObjectString(HTTP_STATUS,
        "Bad Request", exception.getMessage());

    return Response.status(HTTP_STATUS).header(CONTENT_TYPE, MEDIA_TYPE).entity(errorString)
        .build();
  }
}
