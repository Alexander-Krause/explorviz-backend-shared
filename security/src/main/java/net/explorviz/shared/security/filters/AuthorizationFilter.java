package net.explorviz.shared.security.filters;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Priority;
import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.ext.Provider;
import net.explorviz.shared.security.TokenBasedSecurityContext;
import net.explorviz.shared.security.model.roles.Role;
import org.apache.commons.lang3.ArrayUtils;

/**
 * Custom {@link ContainerRequestFilter} that is used for JWT-based authentication and
 * authorization. If used in a web service, every request will be first processed by the
 * {@link AuthenticationFilter} and afterwards this filter.
 *
 * <p>
 * The AuthorizationFilter uses the prepared {@link TokenBasedSecurityContext} and the resource
 * class method annotations (e.g. {@link RolesAllowed} to determine if the user is authenticated and
 * is authorized to proceed with the resource class.
 * </p>
 */
@Provider
@Priority(Priorities.AUTHORIZATION)
public class AuthorizationFilter implements ContainerRequestFilter {

  private static final String NO_PERMISSION_MSG =
      "You don't have permissions to perform this action.";

  private static final String NOT_AUTHENTICATED_MSG =
      "Authentication is required to perform this action.";

  // Credit: https://github.com/cassiomolin/jersey-jwt

  @Context
  private ResourceInfo resourceInfo;

  @Override
  public void filter(final ContainerRequestContext requestContext) throws IOException { // NOPMD
    
    // Only apply authorization to explorviz resources
    if (!this.resourceInfo.getResourceClass().getCanonicalName().startsWith("net.explorviz")) {
      return;
    }

    final Method method = resourceInfo.getResourceMethod();
    final Class cls = resourceInfo.getResourceClass();


    if (method.getName().equals("apply")) {
      // TODO where does the apply message come from?
      // It is only called, if the request is not issued with curl but the frontend
      return;
    }

    // @DenyAll on the method takes precedence over @RolesAllowed and @PermitAll
    if (method.isAnnotationPresent(DenyAll.class)) {
      throw new ForbiddenException(NO_PERMISSION_MSG);
    }

    // @RolesAllowed on the method takes precedence over @PermitAll
    RolesAllowed rolesAllowed = method.getAnnotation(RolesAllowed.class);
    if (rolesAllowed != null) {
      performAuthorization(rolesAllowed.value(), requestContext);
      return;
    }

    // @PermitAll on the method takes precedence over @RolesAllowed on the class
    if (method.isAnnotationPresent(PermitAll.class)) {
      // Do nothing
      return;
    }

    // @DenyAll can't be attached to classes

    // @RolesAllowed on the class takes precedence over @PermitAll on the class
    rolesAllowed = resourceInfo.getResourceClass().getAnnotation(RolesAllowed.class);
    if (rolesAllowed != null) {
      performAuthorization(rolesAllowed.value(), requestContext);
    }

    // @PermitAll on the class, lowest precedence
    if (resourceInfo.getResourceClass().isAnnotationPresent(PermitAll.class)) {
      // Do nothing
      return;
    }


    // Authentication is required for non-annotated methods
    
    if (!isAuthenticated(requestContext)) {
      throw new NotAuthorizedException(NOT_AUTHENTICATED_MSG);
    }
    
  }

  private void performAuthorization(final String[] rolesAllowed,
      final ContainerRequestContext requestContext) {


    if (rolesAllowed.length > 0 && !isAuthenticated(requestContext)) {
      throw new NotAuthorizedException(NOT_AUTHENTICATED_MSG);
    }

    List<String> rolesAllowedList = new ArrayList<>(Arrays.asList(rolesAllowed));

    if (rolesAllowedList.stream().anyMatch(s -> s.contentEquals(Role.ANY))) {
      // Add all available roles to list of allowed roles
      rolesAllowedList.addAll(Role.ROLES.stream().map(Object::toString).collect(Collectors.toList()));
      rolesAllowedList.remove(Role.ANY);
    }

    for (final String role : rolesAllowedList) {
      if (requestContext.getSecurityContext().isUserInRole(role)) {
        // authorized => everything is good
        return;
      }
    }

    throw new NotAuthorizedException(NO_PERMISSION_MSG);
  }

  private boolean isAuthenticated(final ContainerRequestContext requestContext) {
    return requestContext.getSecurityContext().getUserPrincipal() != null;
  }
}
