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
public class UnregisteredTypeExceptionHandler implements ExceptionMapper<UnregisteredTypeException> {

  private static final Logger LOGGER = LoggerFactory.getLogger(UnregisteredTypeExceptionHandler.class);


  private static final String CONTENT_TYPE = "Content-Type";
  private static final String MEDIA_TYPE = "application/json";
  private ErrorObjectHelper errorObjectHelper;
  
  private final int STATUS = 400;

  @Inject
  public UnregisteredTypeExceptionHandler(final ErrorObjectHelper errorObjectHelper) {
    this.errorObjectHelper = errorObjectHelper;
  }

  @Override
  public Response toResponse(final UnregisteredTypeException exception) {

  

    LOGGER.error("Error occured: HTTP Status={}", STATUS, exception);

    final String errorString = errorObjectHelper.createErrorObjectString(STATUS,
        "Bad Request", "Unkown JSON API Type");

    return Response.status(STATUS).header(CONTENT_TYPE, MEDIA_TYPE)
        .entity(errorString).build();
  }
}