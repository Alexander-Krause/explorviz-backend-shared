package net.explorviz.shared.security.model.roles;

import com.github.jasminb.jsonapi.annotations.Id;
import com.github.jasminb.jsonapi.annotations.Type;
import java.util.ArrayList;
import java.util.List;


@Type("role")
public class Role {

  /**
   * User's with this role have elevated access rights and may perform
   * administrative actions.
   */
  public static final String ADMIN_NAME = "admin";

  public static final Role ADMIN = new Role(ADMIN_NAME);

  /**
   * Restrictive role for basic users.
   */
  public static final String USER_NAME = "user";

  public static final Role USER = new Role(USER_NAME);


  public static List<Role> ROLES = new ArrayList<Role>(){{
    add(ADMIN);
    add(USER);
  }};

  /**
   * Checks whether a role with the given name exists.
   * @param roleName Name of the role to check
   * @return {@code true} iff a role with the given name exists
   */
  public static boolean exists(String roleName) {
    return ROLES.contains(roleName);
  }


  @Id
  private String name;

  /**
   * Creates a new roles.
   */
  private Role(String name) {
    this.name = name;
  }

  // Jackson
  public Role(){}


  public String getName() {
    return name;
  }
}
