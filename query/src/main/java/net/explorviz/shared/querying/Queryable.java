package net.explorviz.shared.querying;

public interface Queryable<T> {

  QueryResult<T> query(Query<T> query);
  
}
