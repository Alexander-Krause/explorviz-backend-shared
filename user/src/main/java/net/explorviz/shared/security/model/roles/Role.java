package net.explorviz.shared.security.model.roles;

import com.github.jasminb.jsonapi.annotations.Type;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import xyz.morphia.annotations.Entity;
import xyz.morphia.annotations.Id;


public class Role {

  /**
   * User's with this role have elevated access rights and may perform
   * administrative actions.
   */
  public static final String ADMIN = "admin";


  /**
   * Restrictive role for basic users.
   */
  public static final String USER = "user";

  public static List<String> ROLES = new ArrayList<String>(){{
    add(ADMIN);
    add(USER);
  }};

  /**
   * Checks whether the given role exists.
   * @param role Name of the role to check
   * @return {@code true} iff the role
   */
  public static boolean exists(String role) {
    return ROLES.contains(role);
  }


}
