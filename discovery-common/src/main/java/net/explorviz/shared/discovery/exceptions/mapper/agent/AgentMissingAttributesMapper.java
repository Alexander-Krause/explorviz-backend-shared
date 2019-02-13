package net.explorviz.shared.discovery.exceptions.mapper.agent;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import net.explorviz.shared.discovery.exceptions.ErrorObjectHelper;
import net.explorviz.shared.discovery.exceptions.agent.AgentMissingAttributesException;
import net.explorviz.shared.discovery.exceptions.mapper.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AgentMissingAttributesMapper
    implements ExceptionMapper<AgentMissingAttributesException> {

  private static final Logger LOGGER = LoggerFactory.getLogger(AgentMissingAttributesMapper.class);

  @Override
  public Response toResponse(final AgentMissingAttributesException exception) {

    LOGGER.error("Error occured while patching agent. Error: {}", exception.getMessage());

    final byte[] errorObject = ErrorObjectHelper.getInstance().createSerializedErrorArray(
        ResponseUtil.HTTP_STATUS_UNPROCESSABLE_ENTITY,
        ResponseUtil.ERROR_AGENT_MISSING_ATTRIBUTES_TITLE, exception.getMessage());
    return Response.status(422).entity(errorObject).build();
  }

}
