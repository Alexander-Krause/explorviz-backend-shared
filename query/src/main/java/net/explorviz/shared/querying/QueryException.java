package net.explorviz.shared.querying;

/**
 * Thrown if a {@link Query} could not be performed successfully.
 *
 */
@SuppressWarnings("serial")
public class QueryException extends Exception {

  public QueryException() {
    super();
    // TODO Auto-generated constructor stub
  }

  public QueryException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
    super(arg0, arg1, arg2, arg3);
    // TODO Auto-generated constructor stub
  }

  public QueryException(String arg0, Throwable arg1) {
    super(arg0, arg1);
    // TODO Auto-generated constructor stub
  }

  public QueryException(String arg0) {
    super(arg0);
    // TODO Auto-generated constructor stub
  }

  public QueryException(Throwable arg0) {
    super(arg0);
    // TODO Auto-generated constructor stub
  }



}
