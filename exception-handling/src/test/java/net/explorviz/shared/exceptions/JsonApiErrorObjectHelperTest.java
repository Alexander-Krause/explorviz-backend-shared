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
    String errorString = this.errorObjectHelper.createErrorObjectString("test", "test");


    // Marshall to JsonNode via Jackson
    JsonNode errorJson = null;
    try {
      errorJson = objectMapper.readTree(errorString);
    } catch (IOException e) {
      fail("Marshalling of error String to JsonNode failed", e);
    }

    // Parse to Json-Api library Errors object
    Errors errorsObj = null;
    try {
      errorsObj = ErrorUtils.parseError(objectMapper, errorJson, Errors.class);
    } catch (JsonProcessingException e) {
      fail("Json-Api ErrorUtils could not parse error Json Node", e);
    }


    // Create Json-Api library representation of Json-Api generic documents
    JSONAPIDocument<?> document =
        JSONAPIDocument.createErrorDocument(Collections.singleton(errorsObj.getErrors().get(0)));

    // Recreate String from Json-Api document representation
    String serializedErrorString = "";
    try {
      serializedErrorString = new String(converter.writeDocument(document));
    } catch (DocumentSerializationException e) {
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
    String errorString = this.errorObjectHelper.createErrorObjectString("test", "test");

    // Marshall to JsonNode via Jackson
    JsonNode errorJson = null;
    try {
      errorJson = objectMapper.readTree(errorString);
    } catch (IOException e) {
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

    int statusCode = 400;
    String errorTitle = "test-error-title";
    String errorDetails = "test error details.";

    String errorString =
        this.errorObjectHelper.createErrorObjectString(statusCode, errorTitle, errorDetails);

    // Marshall to JsonNode via Jackson
    JsonNode errorJson = null;
    try {
      errorJson = objectMapper.readTree(errorString);
    } catch (IOException e) {
      fail("Marshalling of error String to JsonNode failed", e);
    }

    JsonNode errorObj = errorJson.get("errors").get(0);
    int serializedStatusCode = errorObj.get("status").asInt();
    String serializedTitle = errorObj.get("title").asText();
    String serializedDetails = errorObj.get("detail").asText();

    assertEquals(statusCode, serializedStatusCode, "Wrong status code after serialization.");
    assertEquals(errorTitle, serializedTitle, "Wrong title after serialization.");
    assertEquals(errorDetails, serializedDetails, "Wrong details after serialization.");
  }

}
