package net.explorviz.shared.exceptions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.jasminb.jsonapi.ErrorUtils;
import com.github.jasminb.jsonapi.JSONAPIDocument;
import com.github.jasminb.jsonapi.ResourceConverter;
import com.github.jasminb.jsonapi.exceptions.DocumentSerializationException;
import com.github.jasminb.jsonapi.models.errors.Errors;
import java.io.IOException;
import java.util.Collections;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class JsonApiErrorObjectHelperTest {

  private JsonApiErrorObjectHelper errorObjectHelper;
  private ObjectMapper objectMapper;
  private ResourceConverter converter;

  @BeforeEach
  public void setUp() {
    this.errorObjectHelper = new JsonApiErrorObjectHelper();
    this.objectMapper = new ObjectMapper();
    this.converter = new ResourceConverter();
  }

  /**
   * Test if {@link JsonApiErrorObjectHelper} returns valid JSON-API error object.
   */
  @Test
  public void testIfErrorObjectIsValidJsonApi() {

    // Take error String of ErrorObjectHelper
    final String errorString = this.errorObjectHelper.createErrorObjectString("test", "test");


    // Marshall to JsonNode via Jackson
    JsonNode errorJson = null;
    try {
      errorJson = this.objectMapper.readTree(errorString);
    } catch (final IOException e) {
      fail("Marshalling of error String to JsonNode failed", e);
    }

    // Parse to Json-Api library Errors object
    Errors errorsObj = null;
    try {
      errorsObj = ErrorUtils.parseError(this.objectMapper, errorJson, Errors.class);
    } catch (final JsonProcessingException e) {
      fail("Json-Api ErrorUtils could not parse error Json Node", e);
    }


    // Create Json-Api library representation of Json-Api generic documents
    final JSONAPIDocument<?> document =
        JSONAPIDocument.createErrorDocument(Collections.singleton(errorsObj.getErrors().get(0)));

    // Recreate String from Json-Api document representation
    String serializedErrorString = "";
    try {
      serializedErrorString = new String(this.converter.writeDocument(document));
    } catch (final DocumentSerializationException e) {
      fail("Could not serialize the deserialized error String", e);
    }

    // Compare to initial error String
    assertEquals(errorString, serializedErrorString, "Default error code was not 500");
  }

  /**
   * Test if {@link JsonApiErrorObjectHelper} returns error status code 500 on default creation.
   */
  @Test
  public void testDefaultStatusCode() {
    final String errorString = this.errorObjectHelper.createErrorObjectString("test", "test");

    // Marshall to JsonNode via Jackson
    JsonNode errorJson = null;
    try {
      errorJson = this.objectMapper.readTree(errorString);
    } catch (final IOException e) {
      fail("Marshalling of error String to JsonNode failed", e);
    }

    assertEquals(500, errorJson.get("errors").get(0).get("status").asInt(),
        "Default status code was not 500");
  }

  /**
   * Test if {@link JsonApiErrorObjectHelper} returns the passed parameters as attributes.
   */
  @Test
  public void testDefaultErrorProperties() {

    final int statusCode = 400;
    final String errorTitle = "test-error-title";
    final String errorDetails = "test error details.";

    final String errorString =
        this.errorObjectHelper.createErrorObjectString(statusCode, errorTitle, errorDetails);

    // Marshall to JsonNode via Jackson
    JsonNode errorJson = null;
    try {
      errorJson = this.objectMapper.readTree(errorString);
    } catch (final IOException e) {
      fail("Marshalling of error String to JsonNode failed", e);
    }

    final JsonNode errorObj = errorJson.get("errors").get(0);
    final int serializedStatusCode = errorObj.get("status").asInt();
    final String serializedTitle = errorObj.get("title").asText();
    final String serializedDetails = errorObj.get("detail").asText();

    assertEquals(statusCode, serializedStatusCode, "Wrong status code after serialization.");
    assertEquals(errorTitle, serializedTitle, "Wrong title after serialization.");
    assertEquals(errorDetails, serializedDetails, "Wrong details after serialization.");
  }

}
