package net.explorviz.shared.exceptions.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.io.IOException;
import javax.ws.rs.core.Response;
import net.explorviz.shared.exceptions.ErrorObjectHelper;
import net.explorviz.shared.exceptions.JsonApiErrorObjectHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GeneralExceptionMapperTest {

  private GeneralExceptionMapper exceptionMapper;

  @BeforeEach
  public void setUp() {
    final ErrorObjectHelper errorObjHelper = new JsonApiErrorObjectHelper();
    this.exceptionMapper = new GeneralExceptionMapper(errorObjHelper);
  }

  /**
   * Test if {@link GeneralExceptionMapper} returns status code 500.
   */
  @Test
  public void testHttpStatusCode() {
    final Response r = this.exceptionMapper.toResponse(new IOException());
    assertEquals(500, r.getStatus());
  }

  /**
   * Test if {@link GeneralExceptionMapper} hides the exception and its message to prevent exception
   * bleeding.
   */
  @Test
  public void testExceptionBleeding() {

    final String testBleeding = "does it bleed?";

    final Response r = this.exceptionMapper.toResponse(new IOException(testBleeding));

    final String errorObj = r.getEntity().toString();

    assertFalse(errorObj.contains(testBleeding));
  }
}
