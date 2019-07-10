package net.explorviz.shared.querying;

public interface Queryable<T> {

  /**
   * Performs the given query and returns the resulting objects.
   * 
   * @param query the query to execute
   * @return Returns a {@link QueryResult} object which encapsulates the actual results with some
   *         meta information
   * 
   * @throws QueryException if the query could not be executed
   */
  QueryResult<T> query(Query<T> query) throws QueryException;

}
