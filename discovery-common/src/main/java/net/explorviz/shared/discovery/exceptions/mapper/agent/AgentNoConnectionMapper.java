package net.explorviz.shared.discovery.exceptions.mapper.agent;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import net.explorviz.shared.discovery.exceptions.ErrorObjectHelper;
import net.explorviz.shared.discovery.exceptions.agent.AgentNoConnectionException;
import net.explorviz.shared.discovery.exceptions.mapper.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AgentNoConnectionMapper implements ExceptionMapper<AgentNoConnectionException> {

  private static final Logger LOGGER = LoggerFactory.getLogger(AgentNoConnectionMapper.class);

  @Override
  public Response toResponse(final AgentNoConnectionException exception) {

    LOGGER.error("Error occured while patching agent. No Connection. Error: {}",
        exception.getMessage());

    final byte[] errorObject = ErrorObjectHelper.getInstance().createSerializedErrorArray(
        ResponseUtil.HTTP_STATUS_UNPROCESSABLE_ENTITY, ResponseUtil.ERROR_NO_AGENT_CONNECTION_TITLE,
        exception.getMessage());
    return Response.status(422).entity(errorObject).build();
  }

}
