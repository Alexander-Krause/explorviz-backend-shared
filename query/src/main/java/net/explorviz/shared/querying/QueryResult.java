package net.explorviz.shared.querying;

import java.util.Collection;

/**
 * Result of a performed {@link Query} by a {@link Queryable}. Contains the actual results and
 * additional metadata.
 *
 * @param <T> the type of the objects returned
 */
public final class QueryResult<T> {

  private Query<T> query;
  private Collection<T> resultData;
  private long total;

  /**
   * Creates a new QueryResult.
   * 
   * @param query the performed query
   * @param result the actual results
   */
  public QueryResult(Query<T> query, Collection<T> result, long total) {
    this.query = query;
    this.resultData = result;
    this.total = total;
  }


  /**
   * The query used to obtain the results.
   * 
   * @return the original query that was performed
   */
  public Query<T> getQuery() {
    return query;
  }

  /**
   * The data which was returned by the persistence layer in response to the query.
   * 
   * @return the actual data the query returned
   */
  public Collection<T> getData() {
    return resultData;
  }

  /**
   * Amount of entry returned.
   * 
   * @return total amount of rows/entities returned.
   */
  public int getN() {
    if (resultData == null) {
      return -1;
    }
    return resultData.size();
  }


  /**
   * Index of the next page, if pagination was used in the corresponding query.
   * 
   * @return index of the next page or {@code null} if no pagination was done or there is no next
   *         page.
   */
  public Integer getNextPage() {
    if (query.doPaginate() && resultData.size() > 0 && query.getPageNumber() != getLastPage()) {
      return query.getPageNumber() + 1;
    } else {
      return null;
    }
  }

  /**
   * Index of the previous page, if pagination was used in the corresponding query.
   * 
   * @return index of the previous page or {@code null} if no pagination was done or there is no
   *         previous page.
   */
  public Integer getPreviousPage() {
    if (query.doPaginate() && query.getPageNumber() > 0) {
      return query.getPageNumber() - 1;
    } else {
      return null;
    }
  }


  public Integer getLastPage() {
    if (query.doPaginate()) {
      return (int) Math.ceil((double) total / query.getPageSize()) - 1;
    } else {
      return null;
    }
  }


}
