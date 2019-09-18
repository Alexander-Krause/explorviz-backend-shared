package net.explorviz.shared.security.model.roles;


/**
 * This class contains the available role names as constants.
 */
public class RoleNames {

  /**
   * Role that denotes regular, unprivileged users.
   */
  public static final String USER = "user";

  /**
   * Role for user with privileges.
   */
  public static final String ADMIN = "admin";


  public static boolean exists(String name) {
    return name.contentEquals(USER) || name.contentEquals(ADMIN);
  }

}
