package net.explorviz.shared.querying;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;

/**
 * Filters request that contain invalid paginations parameters. The type of pagination in use is
 * page-based. Pages are defined by the page number (i.e. the index of the page) and the page size
 * (i.e. the amount of objects to return). The query parameters are {@code page[number]} and
 * {@code page[size]} respectively.
 * <p/>
 * If a request contains query parameters matching the reserved keywords for pagination, they are
 * subject to this filter. This means the query parameters for pagination cannot be reused for other
 * purposes if this filter is active.
 * <p/>
 * If the pagination parameters are given but do not contain valid data, the request is aborted and
 * a 400 will be sent back to the client.
 * 
 *
 */
public class PaginationParameterFilter implements ContainerRequestFilter {

  // Names of the parameters are defined by JSON:API
  private static final String PAGENUM = "page[number]";
  private static final String PAGESIZE = "page[size]";

  private static final int MAX_PAGE_SIZE = 100;


  @Override
  public void filter(ContainerRequestContext requestContext) throws IOException {
    Map<String, List<String>> queryParams = requestContext.getUriInfo().getQueryParameters(true);

    // If none set, skip
    if (queryParams.get(PAGENUM) == null && queryParams.get(PAGESIZE) == null) {
      return;
    }



    // Both or none have to be present
    if ((queryParams.get(PAGENUM) != null && queryParams.get(PAGESIZE) == null)
        || (queryParams.get(PAGENUM) == null && queryParams.get(PAGESIZE) != null)) {
      throw new BadRequestException(
          String.format("Both %s and %s have to be specified", PAGESIZE, PAGENUM));
    }

    // Both have to be positive integers
    int num, len;
    try {
      num = Integer.parseInt(queryParams.get(PAGENUM).get(0));
      len = Integer.parseInt(queryParams.get(PAGESIZE).get(0));
      if (len <= 0 || num < 0) {

      }
    } catch (NumberFormatException e) {
      throw new BadRequestException(
          String.format("%s must be greater than zero, %s must be a positive integer",
              PAGESIZE,
              PAGENUM));
    }



  }

}
