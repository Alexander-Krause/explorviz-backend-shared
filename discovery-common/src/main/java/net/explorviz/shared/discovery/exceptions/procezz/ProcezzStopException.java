package net.explorviz.shared.discovery.exceptions.procezz;

import net.explorviz.shared.discovery.model.Procezz;

public class ProcezzStopException extends Exception {

  private static final long serialVersionUID = 1L;

  private Procezz faultyProcezz;

  public ProcezzStopException(final String message, final Throwable cause, final Procezz p) {
    super(message, cause);
    this.faultyProcezz = p;
  }

  public ProcezzStopException(final String message, final Throwable cause) {
    super(message, cause);
  }

  public Procezz getFaultyProcezz() {
    return faultyProcezz;
  }

}
