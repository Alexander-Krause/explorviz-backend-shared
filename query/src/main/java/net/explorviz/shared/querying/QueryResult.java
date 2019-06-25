package net.explorviz.shared.querying;

import java.util.Collection;

/**
 * Result of a performed {@link Query} by a {@link Queryable}.
 *
 * Contains the actual results and additional metadata.
 *
 * @param <T>
 */
public abstract class QueryResult<T> {

  private Query<T> query;
  private Collection<T> resultData;

  /**
   * Creates a new QueryResult.
   * 
   * @param query the performed query
   * @param result the actual results
   */
  public QueryResult(Query<T> query, Collection<T> result) {
    this.query = query;
    this.resultData = result;
  }


  /**
   * 
   * @return the original query that was performed
   */
  public Query<T> getQuery() {
    return query;
  }

  /**
   * 
   * @return the actual data the query returned
   */
  public Collection<T> getData() {
    return resultData;
  }

  /**
   * 
   * @return total amount of rows/entities returned.
   */
  public int getN() {
    return resultData.size();
  }


  /**
   * 
   * @return index of the next page
   */
  public abstract Integer getNextPage();

  /**
   * 
   * @return index of the previous page
   */
  public abstract Integer getPreviousPage();



}
