package net.explorviz.shared.querying;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;

/**
 * Classes that implement the {@link Queryable} interface can answer a set of entities corresponding
 * to query. Such a a query defines
 * 
 * <ul>
 * <li>if and how the resulting data should be paginated
 * <li>if and by which attributes the resulting data should be filtered
 * </ul>
 * 
 * <p>
 * Using this querying mechanic instead of fetching the entirety of the data and processing the
 * results afterwards is in general more efficient.
 * </p>
 *
 * @param <T> the type of the resulting objects
 */
public class Query<T> {

  private static final String PAGENUM = "page[number]";
  private static final String PAGESIZE = "page[size]";
  private static final String FILTER_PREFIX = "filter[";

  private final int pageSize;
  private final int pageNumber;
  private final Map<String, List<String>> filterAttributes;


  /**
   * Creates a new query object in accordance to the given parameters. Should be handled by the
   * factory method {@link Query#fromParameterMap(MultivaluedMap)}
   * 
   * @param pageSize size of the page or -1 if no pagination is wanted
   * @param pageNumber index of the page or -1 if not pagination is wanted
   * @param filterAttributes map that specifies the filters
   */
  private Query(int pageSize, int pageNumber, Map<String, List<String>> filterAttributes) {
    super();
    this.pageSize = pageSize;
    this.pageNumber = pageNumber;
    this.filterAttributes = filterAttributes;
  }

  /**
   * Size of each page. If no pagination is wanted, this method returns -1.
   * 
   * @return the requested size of a page or -1, if no pagination is intended.
   */
  public int getPageSize() {
    return pageSize;
  }

  /**
   * Number of requested the page. If no pagination is wanted, this method returns -1.
   * 
   * @return the index of the page to return or -1, if no pagination is intended.
   */
  public int getPageNumber() {
    return pageNumber;
  }

  /**
   * Map that specifies how results should be filtered. Each entry of the map consists of the name
   * of the attribute to filter by and the value to filter for. If no filtering should be performed,
   * this map is empty.
   * <p/>
   * If more than one value is specified for a key, resulting objects should satisfy both (i.e.
   * conjunction, not disjunction)
   * 
   * @return a map specifying if and how to filter.
   */
  public Map<String, List<String>> getFilters() {
    return filterAttributes;
  }



  /**
   * Checks whether the result should be paginated.
   * 
   * @return {@code True} iff the result must be paginated. Returns false if either
   *         {@link #getPageNumber()} returns a number smaller than 0 or {@link #getPageSize()}
   *         returns a number equal or smaller to 0.
   */
  public boolean doPaginate() {
    return getPageSize() > 0 && getPageNumber() >= 0;
  }

  /**
   * Checks whether the query should be filtered.
   * 
   * @return {@code True} iff the attributes to filter by are empty.
   */
  public boolean doFilter() {
    return !getFilters().isEmpty();
  }


  /**
   * Returns a Query based on the raw query parameters of a HTTP request. The parameters can be
   * obtain by {@link ContainerRequestContext#getUriInfo()} and
   * {@link UriInfo#getQueryParameters(boolean)}
   * 
   * @param <T> type of the entity this query is for
   * @param paramters the raw HTTP query parameters
   * @return a query object corresponding to the given parameters
   */
  public static <T> Query<T> fromParameterMap(MultivaluedMap<String, String> paramters) {


    Map<String, List<String>> attributes = new HashMap<>();

    // Extract filters. Filter parameters have the form "filter[attribute]=value"
    paramters.entrySet().stream().filter(e -> e.getKey().startsWith(FILTER_PREFIX)).forEach(e -> {
      String name = e.getKey().substring(e.getKey().indexOf('[') + 1, e.getKey().indexOf(']'));
      attributes.put(name.toLowerCase(), e.getValue());
    });

    int pageNumber =
        paramters.containsKey(PAGESIZE) ? Integer.parseInt(paramters.get(PAGENUM).get(0)) : -1;
    int pageSize =
        paramters.containsKey(PAGESIZE) ? Integer.parseInt(paramters.get(PAGESIZE).get(0)) : -1;

    return new Query<T>(pageSize, pageNumber, attributes);

  }

}


