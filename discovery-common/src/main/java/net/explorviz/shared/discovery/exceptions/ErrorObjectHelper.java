package net.explorviz.shared.discovery.exceptions;

import com.github.jasminb.jsonapi.JSONAPIDocument;
import com.github.jasminb.jsonapi.ResourceConverter;
import com.github.jasminb.jsonapi.exceptions.DocumentSerializationException;
import com.github.jasminb.jsonapi.models.errors.Error;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class ErrorObjectHelper {

  private static final Logger LOGGER = LoggerFactory.getLogger(ErrorObjectHelper.class);

  private static final Object LOCK = new Object();

  private static final Charset CHARSET = StandardCharsets.UTF_8;

  private static ErrorObjectHelper instance;

  private final ResourceConverter converter;

  private ErrorObjectHelper() {
    this.converter = new ResourceConverter();
  }

  public static ErrorObjectHelper getInstance() {
    synchronized (LOCK) {
      if (ErrorObjectHelper.instance == null) {
        ErrorObjectHelper.instance = new ErrorObjectHelper();
      }
      return ErrorObjectHelper.instance;
    }
  }

  public String createErrorArrayString(final String errorTitle, final String errorDetail) {
    return createErrorArrayString(0, errorTitle, errorDetail);
  }

  public String createErrorArrayString(final String httpStatus, final String errorTitle,
      final String errorDetail) {

    int possibleHttpStatus = 422;
    try {
      possibleHttpStatus = Integer.valueOf(httpStatus);
    } catch (final NumberFormatException e) {
      LOGGER.warn("Could not parse HTTP Status: {}", httpStatus);
    }

    return createErrorArrayString(possibleHttpStatus, errorTitle, errorDetail);
  }

  public String createErrorArrayString(final int httpStatus, final String errorTitle,
      final String errorDetail) {

    final Error error = createErrorObject(httpStatus, errorTitle, errorDetail);

    final JSONAPIDocument<?> document =
        JSONAPIDocument.createErrorDocument(Collections.singleton(error));

    try {
      return new String(converter.writeDocument(document));
    } catch (final DocumentSerializationException e) {
      LOGGER.error("Error occured while converting ErrorObject. Error: {}", e.toString());

      String httpStatusEntry = "";
      if (httpStatus != 0) {
        httpStatusEntry = String.valueOf(httpStatus);
      }

      // Fallback string
      return "{\"errors\": [ \"status\": \"" + httpStatusEntry + "\", \"title\": \"" + errorTitle
          + "\", \"detail\": \"" + errorDetail + "\"]}";
    }
  }

  public byte[] createSerializedErrorArray(final String errorTitle, final String errorDetail) {
    return createSerializedErrorArray(0, errorTitle, errorDetail);
  }

  public byte[] createSerializedErrorArray(final int httpStatus, final String errorTitle,
      final String errorDetail) {
    return serializeErrorObject(createErrorObject(httpStatus, errorTitle, errorDetail));
  }

  public byte[] serializeErrorObject(final Error error) {
    final JSONAPIDocument<?> document =
        JSONAPIDocument.createErrorDocument(Collections.singleton(error));
    try {
      return converter.writeDocument(document);
    } catch (final DocumentSerializationException e) {
      return createErrorArrayString(error.getStatus(), error.getTitle(), error.getDetail())
          .getBytes(CHARSET);
    }
  }

  public Error createErrorObject(final int httpStatus, final String errorTitle,
      final String errorDetail) {

    final Error error = new Error();

    if (httpStatus != 0) {
      error.setStatus(String.valueOf(httpStatus));
    }
    error.setTitle(errorTitle);
    error.setDetail(errorDetail);

    return error;
  }
}
