package net.explorviz.shared.exceptions.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.github.jasminb.jsonapi.exceptions.InvalidJsonApiResourceException;
import javax.ws.rs.core.Response;
import net.explorviz.shared.exceptions.ErrorObjectHelper;
import net.explorviz.shared.exceptions.JsonApiErrorObjectHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class InvalidJsonApiResourceExceptionMapperTest {

  private InvalidJsonApiResourceExceptionMapper exceptionMapper;

  @BeforeEach
  public void setUp() {
    final ErrorObjectHelper errorObjHelper = new JsonApiErrorObjectHelper();
    this.exceptionMapper = new InvalidJsonApiResourceExceptionMapper(errorObjHelper);
  }

  /**
   * Test if {@link InvalidJsonApiResourceExceptionMapper} returns status code 400.
   */
  @Test
  public void testHttpStatusCode() {
    final Response r = this.exceptionMapper.toResponse(new InvalidJsonApiResourceException());
    assertEquals(400, r.getStatus());
  }
}
