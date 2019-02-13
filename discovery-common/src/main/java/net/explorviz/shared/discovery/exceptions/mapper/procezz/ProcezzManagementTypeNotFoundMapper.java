package net.explorviz.shared.discovery.exceptions.mapper.procezz;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import net.explorviz.shared.discovery.exceptions.ErrorObjectHelper;
import net.explorviz.shared.discovery.exceptions.mapper.ResponseUtil;
import net.explorviz.shared.discovery.exceptions.procezz.ProcezzManagementTypeNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProcezzManagementTypeNotFoundMapper
    implements ExceptionMapper<ProcezzManagementTypeNotFoundException> {

  private static final Logger LOGGER =
      LoggerFactory.getLogger(ProcezzManagementTypeNotFoundMapper.class);

  @Override
  public Response toResponse(final ProcezzManagementTypeNotFoundException exception) {

    LOGGER.error("Error occured while patching procezz. Error: {}", exception.getMessage());

    if (exception.getFaultyProcezz() != null) {
      exception.getFaultyProcezz().setErrorOccured(true);
      exception.getFaultyProcezz().setErrorMessage(exception.getMessage());
    }

    final byte[] errorObject = ErrorObjectHelper.getInstance().createSerializedErrorArray(
        ResponseUtil.HTTP_STATUS_UNPROCESSABLE_ENTITY, ResponseUtil.ERROR_INTERNAL_AGENT,
        exception.getMessage());
    return Response.status(422).entity(errorObject).build();
  }

}
