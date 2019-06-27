package net.explorviz.shared.querying;

import com.github.jasminb.jsonapi.JSONAPIDocument;
import com.github.jasminb.jsonapi.Link;
import com.github.jasminb.jsonapi.ResourceConverter;
import com.github.jasminb.jsonapi.exceptions.DocumentSerializationException;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Provider
@Produces("application/vnd.api+json")
public class PaginationJsonApiWriter<T> implements MessageBodyWriter<QueryResult<T>> {
  // Names of the parameters are defined by JSON:API
  private static final String PAGENUM = "page[number]";
  private static final String PAGELEN = "page[size]";
  private static final String NEXT_LINK = "next";
  private static final String PREV_LINK = "prev";
  private static final String FIRST_LINK = "first";

  private static final Logger LOGGER = LoggerFactory.getLogger(PaginationJsonApiWriter.class);

  @Context
  private HttpServletRequest httpRequest;

  private final ResourceConverter converter;

  @Inject
  public PaginationJsonApiWriter(final ResourceConverter converter) {
    this.converter = converter;
  }

  @Override
  public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations,
      MediaType mediaType) {
    // TODO Auto-generated method stub
    return true;
  }

  @Override
  public void writeTo(QueryResult<T> results, Class<?> type, Type genericType,
      Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, Object> httpHeaders,
      OutputStream entityStream) throws IOException, WebApplicationException {

    JSONAPIDocument<List<T>> document =
        new JSONAPIDocument<List<T>>(new ArrayList<T>(results.getData()));

    if (results.getQuery().doPaginate()) {
      addLinks(results, document);
    }


    try {
      entityStream.write(this.converter.writeDocumentCollection(document));
    } catch (final DocumentSerializationException e) {
      if (LOGGER.isErrorEnabled()) {
        LOGGER.error("Error when serializing object of type" + type + ": ", e);
      }
    } finally {
      entityStream.flush();
      entityStream.close();
    }

  }

  private void addLinks(QueryResult<T> results, JSONAPIDocument<List<T>> document) {
    // Next Link
    if (results.getNextPage() != null) {
      StringBuffer nextUrlBuffer = httpRequest.getRequestURL();
      nextUrlBuffer.append("?").append(PAGENUM).append("=").append(results.getNextPage());
      nextUrlBuffer.append("&")
          .append(PAGELEN)
          .append("=")
          .append(results.getQuery().getPageSize());

      String nextLink = nextUrlBuffer.toString();
      document.addLink(NEXT_LINK, new Link(nextLink));
    }

    // Prev Link
    if (results.getPreviousPage() != null) {
      StringBuffer prevUrlBuffer = httpRequest.getRequestURL();
      prevUrlBuffer.append("?").append(PAGENUM).append("=").append(results.getPreviousPage());
      prevUrlBuffer.append("&")
          .append(PAGELEN)
          .append("=")
          .append(results.getQuery().getPageSize());

      String prevLink = prevUrlBuffer.toString();
      document.addLink(PREV_LINK, new Link(prevLink));
    }

    // First Link
    StringBuffer firstUrlBuffer = httpRequest.getRequestURL();
    firstUrlBuffer.append("?").append(PAGENUM).append("=").append(0);
    firstUrlBuffer.append("&")
        .append(PAGELEN)
        .append("=")
        .append(results.getQuery().getPageSize());

    String firstLink = firstUrlBuffer.toString();
    document.addLink(FIRST_LINK, new Link(firstLink));
  }


}
