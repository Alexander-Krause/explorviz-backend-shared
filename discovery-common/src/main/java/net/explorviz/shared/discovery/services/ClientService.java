package net.explorviz.shared.discovery.services;

import java.util.List;
import java.util.Map;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.MessageBodyWriter;
import net.explorviz.shared.discovery.exceptions.GenericNoConnectionException;
import net.explorviz.shared.discovery.exceptions.agent.AgentInternalErrorException;
import net.explorviz.shared.discovery.exceptions.agent.AgentNoConnectionException;
import net.explorviz.shared.discovery.exceptions.mapper.ResponseUtil;
import net.explorviz.shared.discovery.exceptions.procezz.ProcezzGenericException;
import net.explorviz.shared.discovery.model.Agent;
import net.explorviz.shared.discovery.model.Procezz;
import org.glassfish.jersey.client.ClientProperties;
import org.glassfish.jersey.client.HttpUrlConnectorProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClientService {

  private static final Logger LOGGER = LoggerFactory.getLogger(ClientService.class);
  private static final String MEDIA_TYPE = "application/vnd.api+json";

  private static final String LOGGER_MESSAGE =
      "Connection to {} failed, probably offline or wrong IP. "
          + "Check IP in WEB-INF/classes/explorviz.properties. Error Message: {}";

  private final ClientBuilder clientBuilder = ClientBuilder.newBuilder();

  public <T> void registerProviderReader(final MessageBodyReader<T> providerReader) {
    clientBuilder.register(providerReader);
  }

  public <T> void registerProviderWriter(final MessageBodyWriter<T> providerWriter) {
    clientBuilder.register(providerWriter);
  }

  public <T> T doGETRequest(final Class<T> type, final String url,
      final Map<String, Object> queryParameter)
      throws ProcessingException, WebApplicationException {
    final Client client = this.clientBuilder.build();

    final GenericType<T> genericType = new GenericType<>(type);

    if (queryParameter == null) {
      return client.target(url).request(MEDIA_TYPE).get(genericType);
    } else {

      WebTarget target = client.target(url);

      for (final Map.Entry<String, Object> queryParam : queryParameter.entrySet()) {
        target = target.queryParam(queryParam.getKey(), queryParam.getValue());
      }

      return target.request(MEDIA_TYPE).get(genericType);
    }

  }

  public Agent doAgentPatchRequest(final Agent updatedAgent, final String url)
      throws AgentInternalErrorException, AgentNoConnectionException {

    final Client client = buildClient();

    try {
      final Response agentResponse = client.target(url).request(MEDIA_TYPE)
          .build("PATCH", Entity.entity(updatedAgent, MEDIA_TYPE))
          .property(HttpUrlConnectorProvider.SET_METHOD_WORKAROUND, true).invoke();

      if (agentResponse.getStatus() == Response.Status.OK.getStatusCode()) {
        return agentResponse.readEntity(Agent.class);
      } else {
        final String errorResponse = agentResponse.readEntity(String.class);
        throw new AgentInternalErrorException(errorResponse, new Exception());
      }

    } catch (final ProcessingException e) {
      LOGGER.warn(LOGGER_MESSAGE, url, e.getMessage());
      throw new AgentNoConnectionException(ResponseUtil.ERROR_NO_AGENT_CONNECTION_TIMEOUT, e);
    }
  }

  // TODO aufbauen wie AgentPatch
  public <T> Response doPatch(final T t, final String url) throws ProcessingException {
    return this.clientBuilder.build().target(url).request(MEDIA_TYPE)
        .build("PATCH", Entity.entity(t, MEDIA_TYPE))
        .property(HttpUrlConnectorProvider.SET_METHOD_WORKAROUND, true).invoke();
  }

  public Agent postAgent(final Agent agent, final String url)
      throws ProcezzGenericException, GenericNoConnectionException {
    final Client client = buildClient();

    try {
      final Response response =
          client.target(url).request(MEDIA_TYPE).post(Entity.entity(agent, MEDIA_TYPE));

      if (response.getStatus() == Response.Status.OK.getStatusCode()) {
        return response.readEntity(Agent.class);
      } else {
        final String errorResponse = response.readEntity(String.class);
        throw new ProcezzGenericException(errorResponse, new Exception());
      }
    } catch (final ProcessingException e) {
      LOGGER.warn(LOGGER_MESSAGE, url, e.getMessage());
      throw new GenericNoConnectionException(ResponseUtil.ERROR_NO_AGENT_CONNECTION_TIMEOUT, e);
    }
  }

  public List<Procezz> postProcezzList(final List<Procezz> procezzList, final String url)
      throws ProcezzGenericException, GenericNoConnectionException {
    final Client client = buildClient();

    final GenericType<List<Procezz>> genericType = new GenericType<List<Procezz>>() {};

    try {
      final Response response =
          client.target(url).request(MEDIA_TYPE).post(Entity.entity(procezzList, MEDIA_TYPE));

      if (response.getStatus() == Response.Status.OK.getStatusCode()) {
        return response.readEntity(genericType);
      } else {
        final String errorResponse = response.readEntity(String.class);
        throw new ProcezzGenericException(errorResponse, new Exception());
      }
    } catch (final ProcessingException e) {
      LOGGER.warn(LOGGER_MESSAGE, url, e.getMessage());
      throw new GenericNoConnectionException(ResponseUtil.ERROR_NO_AGENT_CONNECTION_TIMEOUT, e);
    }
  }

  private Client buildClient() {
    final Client client = this.clientBuilder.build();
    client.property(ClientProperties.CONNECT_TIMEOUT, 4000);
    client.property(ClientProperties.READ_TIMEOUT, 4000);
    return client;
  }

}
