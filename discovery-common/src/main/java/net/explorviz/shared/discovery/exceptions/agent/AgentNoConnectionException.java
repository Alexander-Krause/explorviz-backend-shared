package net.explorviz.shared.discovery.exceptions.agent;

public class AgentNoConnectionException extends Exception {

  private static final long serialVersionUID = 1L;

  public AgentNoConnectionException(final String message, final Throwable cause) {
    super(message, cause);
  }

}
