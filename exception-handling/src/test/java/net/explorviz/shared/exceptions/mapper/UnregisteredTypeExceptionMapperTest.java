package net.explorviz.shared.exceptions.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.github.jasminb.jsonapi.exceptions.UnregisteredTypeException;
import javax.ws.rs.core.Response;
import net.explorviz.shared.exceptions.ErrorObjectHelper;
import net.explorviz.shared.exceptions.JsonApiErrorObjectHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UnregisteredTypeExceptionMapperTest {

  private UnregisteredTypeExceptionMapper exceptionMapper;

  @BeforeEach
  public void setUp() {
    final ErrorObjectHelper errorObjHelper = new JsonApiErrorObjectHelper();
    this.exceptionMapper = new UnregisteredTypeExceptionMapper(errorObjHelper);
  }


  /**
   * Test if {@link InvalidJsonApiResourceExceptionMapper} returns status code 400.
   */
  @Test
  public void testHttpStatusCode() {
    final Response r = this.exceptionMapper.toResponse(new UnregisteredTypeException("mytype"));
    assertEquals(400, r.getStatus());
  }


  /**
   * Test if {@link UnregisteredTypeExceptionMapper} hides the exception and its message to prevent
   * exception bleeding.
   */
  @Test
  public void testExceptionBleeding() {

    final String testBleeding = "does it bleed?";

    final Response r = this.exceptionMapper.toResponse(new UnregisteredTypeException(testBleeding));

    final String errorObj = r.getEntity().toString();

    assertFalse(errorObj.contains(testBleeding));
  }


  /**
   * Test if {@link UnregisteredTypeExceptionMapper} returns description for error.
   */
  @Test
  public void testDescription() {
    final Response r = this.exceptionMapper.toResponse(new UnregisteredTypeException("test"));
    assertTrue(
        r.getEntity().toString().contains(UnregisteredTypeExceptionMapper.getDefaultErrorDetail()));
  }


  /**
   * Test if {@link UnregisteredTypeExceptionMapper} returns title for error.
   */
  @Test
  public void testTitle() {
    final Response r = this.exceptionMapper.toResponse(new UnregisteredTypeException("test"));
    assertTrue(
        r.getEntity().toString().contains(UnregisteredTypeExceptionMapper.getDefaultErrorTitle()));
  }


}
