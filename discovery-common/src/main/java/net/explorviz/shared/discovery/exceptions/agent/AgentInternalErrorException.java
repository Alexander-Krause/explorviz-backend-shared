package net.explorviz.shared.discovery.exceptions.agent;

public class AgentInternalErrorException extends Exception {

  private static final long serialVersionUID = 1L;

  public AgentInternalErrorException(final String message, final Throwable cause) {
    super(message, cause);
  }

}
