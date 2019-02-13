package net.explorviz.shared.discovery.exceptions.mapper.procezz;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.jasminb.jsonapi.models.errors.Errors;
import java.io.IOException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import net.explorviz.shared.discovery.exceptions.ErrorObjectHelper;
import net.explorviz.shared.discovery.exceptions.procezz.ProcezzGenericException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProcezzGenericMapper implements ExceptionMapper<ProcezzGenericException> {

  private static final Logger LOGGER = LoggerFactory.getLogger(ProcezzGenericMapper.class);

  @Override
  public Response toResponse(final ProcezzGenericException exception) {

    LOGGER.error("Error occured while patching procezz. Error: {}", exception.toString());

    final ObjectMapper mapper = new ObjectMapper();
    byte[] errorObj;
    try {
      final Errors errorsArray = mapper.readValue(exception.getMessage(), Errors.class);
      errorObj =
          ErrorObjectHelper.getInstance().serializeErrorObject(errorsArray.getErrors().get(0));
    } catch (final IOException | IndexOutOfBoundsException e) {
      // If there is an error during parsing, simply return the non-parsed string for
      // user information
      return Response.status(422).entity(exception.getMessage()).build();
    }

    return Response.status(422).entity(errorObj).build();
  }

}
