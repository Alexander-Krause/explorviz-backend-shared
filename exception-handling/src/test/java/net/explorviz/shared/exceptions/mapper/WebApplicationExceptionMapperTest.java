package net.explorviz.shared.exceptions.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import net.explorviz.shared.exceptions.ErrorObjectHelper;
import net.explorviz.shared.exceptions.JsonApiErrorObjectHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class WebApplicationExceptionMapperTest {

  private WebApplicationExceptionMapper exceptionMapper;

  @BeforeEach
  public void setUp() {
    final ErrorObjectHelper errorObjHelper = new JsonApiErrorObjectHelper();
    this.exceptionMapper = new WebApplicationExceptionMapper(errorObjHelper);
  }

  /**
   * Test if {@link WebApplicationExceptionMapper} returns default title for error.
   */
  @Test
  public void testDefaultTitle() {
    final Response r = this.exceptionMapper.toResponse(new NotFoundException());
    assertTrue(r.getEntity().toString().contains("An error occured"));
  }


  /**
   * Test if {@link WebApplicationExceptionMapper} returns status code of catched type of web
   * application exceltion. In this case 404 for {@link NotFoundException}.
   */
  @Test
  public void testPassingOfStatusCode() {
    final Response r = this.exceptionMapper.toResponse(new NotFoundException());
    assertEquals(404, r.getStatus());
  }

  /**
   * Test if {@link NotFoundExceptionMapper} contains the exception message.
   */
  @Test
  public void testPassingOfExceptionMessage() {

    final String testBleeding = "does it bleed?";

    final Response r = this.exceptionMapper.toResponse(new NotFoundException(testBleeding));

    final String errorObj = r.getEntity().toString();

    assertTrue(errorObj.contains(testBleeding));
  }
}
