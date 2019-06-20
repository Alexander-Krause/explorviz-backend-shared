package net.explorviz.shared.querying;

public interface Query<T> {

  default Integer getPageLength() {
    return null;
  }
  
  default Integer getPageIndex() {
    return null;
  }
  
}
