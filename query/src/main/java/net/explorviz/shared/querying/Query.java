package net.explorviz.shared.querying;

public interface Query<T> {

  Integer getPageLength();


  Integer getPageIndex();

  default boolean doPaginate() {
    return getPageLength() > 0 && getPageIndex() >= 0;
  }

}
