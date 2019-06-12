package net.explorviz.shared.exceptions;

/**
 * Interface contract for ErrorObjectHelper, which return error objects with respect to different
 * schemata.
 */
public interface ErrorObjectHelper {

  /**
   * Create an error object with a default HTTP status code.
   *
   * @param errorTitle
   * @param errorDetail
   * @return Stringified error with respect to a certain schema, e.g., Json-Api
   */
  public String createErrorObjectString(final String errorTitle, final String errorDetail);


  /**
   * Create an error object with a passed HTTP status code.
   *
   * @param httpStatus
   * @param errorTitle
   * @param errorDetail
   * @return Stringified error with respect to a certain schema, e.g., Json-Api
   */
  public String createErrorObjectString(final int httpStatus, final String errorTitle,
      final String errorDetail);

}
