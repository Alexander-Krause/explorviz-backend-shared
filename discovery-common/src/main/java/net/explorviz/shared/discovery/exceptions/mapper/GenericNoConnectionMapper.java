package net.explorviz.shared.discovery.exceptions.mapper;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import net.explorviz.shared.discovery.exceptions.ErrorObjectHelper;
import net.explorviz.shared.discovery.exceptions.GenericNoConnectionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GenericNoConnectionMapper implements ExceptionMapper<GenericNoConnectionException> {

  private static final Logger LOGGER = LoggerFactory.getLogger(GenericNoConnectionMapper.class);

  @Override
  public Response toResponse(final GenericNoConnectionException exception) {

    LOGGER.error("Error occured, no Connection. Error: {}", exception.getMessage());

    final byte[] errorObject = ErrorObjectHelper.getInstance().createSerializedErrorArray(
        Response.Status.SERVICE_UNAVAILABLE.getStatusCode(), ResponseUtil.ERROR_NO_CONNECTION_TITLE,
        exception.getMessage());
    return Response.status(422).entity(errorObject).build();
  }

}
