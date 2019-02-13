package net.explorviz.shared.discovery.exceptions.mapper.procezz;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import net.explorviz.shared.discovery.exceptions.ErrorObjectHelper;
import net.explorviz.shared.discovery.exceptions.mapper.ResponseUtil;
import net.explorviz.shared.discovery.exceptions.procezz.ProcezzMonitoringSettingsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProcezzMonitoringSettingsMapper
    implements ExceptionMapper<ProcezzMonitoringSettingsException> {

  private static final Logger LOGGER =
      LoggerFactory.getLogger(ProcezzMonitoringSettingsMapper.class);

  @Override
  public Response toResponse(final ProcezzMonitoringSettingsException exception) {

    LOGGER.error("Error occured while patching procezz. Error: {}", exception.toString());

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
