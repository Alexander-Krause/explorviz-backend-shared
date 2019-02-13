package net.explorviz.shared.discovery.exceptions.mapper;

public class ResponseUtil {

  public static final int HTTP_STATUS_UNPROCESSABLE_ENTITY = 422;

  public static final String UNEXCPECTED_ERROR_USER_NOTIFICATION =
      "This should not happen at this stage. Notify a developer. ";

  public static final String ERROR_INTERNAL_AGENT = "An internal agent error occured.";
  public static final String ERROR_NO_AGENT_TITLE = "Unknown agent.";
  public static final String ERROR_NO_AGENT_DETAIL =
      "The request contained an unknown agent. This shouldn't happen if you are using the frontend.";

  public static final String ERROR_NO_IDS_TITLE = "Could not obtain unique Ids";
  public static final String ERROR_NO_IDS_DETAIL =
      "Agent could not obtain uniqure Ids from Backend. Backend is probably offline or timeout occured.";

  public static final String ERROR_NO_CONNECTION_TITLE = "No connection.";
  public static final String ERROR_NO_CONNECTION_DETAIL =
      "Couldn't reach service, probably offline or timeout.";

  public static final String ERROR_NO_AGENT_CONNECTION_TITLE = "No connection to agent.";
  public static final String ERROR_NO_AGENT_CONNECTION_DETAIL =
      "Backend couldn't reach agent, probably offline. Re-registration of agent is possible if it is restarted.";

  public static final String ERROR_AGENT_FLAG_DETAIL =
      "Agent couldn't inject necessary information into the execution command. Validate User- (if not declared: OS) execution command";

  public static final String ERROR_NO_AGENT_CONNECTION_TIMEOUT =
      "Backend couldn't reach agent, because of timeout or no connection. Re-registration of agent is possible if it is restarted.";

  public static final String ERROR_AGENT_MISSING_ATTRIBUTES_TITLE =
      "The passed agent misses mandatory attributes.";
  public static final String ERROR_AGENT_MISSING_ATTRIBUTES_DETAIL =
      "The request contained an agent with missing IP or PORT attribute.";

  public static final String PROCEZZ_STARTED = "Agent started procezz. ";
  public static final String PROCEZZ_NOT_FOUND_IN_LIST = "Procezz was not found in list. ";

  public static final String ERROR_PROCEZZ_START =
      "Agent couldn't start Procezz. Check User- (if not declared: OS) Exec CMD.";
  public static final String ERROR_PROCEZZ_START_NOT_FOUND =
      "Couldn't find it in new procezzList from OS. Check User- (if not declared: OS) Exec CMD. The execution command might be wrong";
  public static final String ERROR_PROCEZZ_START_STOPPED =
      "Agent couldn't start procezz, since it is flagged as stopped. This should not happen. Please contact an developer.";

  public static final String ERROR_PROCEZZ_TYPE_INCOMPATIBLE_COMP =
      "ProcezzManagementType of two procezzes does not match. ";

  public static final String ERROR_PROCEZZ_TYPE_NOT_FOUND_TITLE =
      "ProcezzManagementType not found.";
  public static final String ERROR_PROCEZZ_TYPE_NOT_FOUND_DETAIL =
      "No ProcezzManagementType found for the passed key, therefore it cannot be re-found after restart. ";

  public static final String ERROR_PROCEZZ_STOP = "Agent couldn't stop Procezz.";

  public static final String ERROR_PROCEZZ_FLAG_NOT_FOUND =
      "Agent couldn't find a procezz for the passed agent flag.";
  public static final String ERROR_PROCEZZ_ID_NOT_FOUND =
      "Agent couldn't find a procezz for the passed internal Id. This should not happen";

}
