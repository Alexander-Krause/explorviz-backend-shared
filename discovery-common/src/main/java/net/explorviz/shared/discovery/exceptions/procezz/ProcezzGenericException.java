package net.explorviz.shared.discovery.exceptions.procezz;

import net.explorviz.shared.discovery.model.Procezz;

public class ProcezzGenericException extends Exception {

  private static final long serialVersionUID = 1L;

  private Procezz faultyProcezz;

  public ProcezzGenericException(final String jsonErrorString, final Throwable cause,
      final Procezz p) {
    super(jsonErrorString, cause);
    this.faultyProcezz = p;
  }

  public ProcezzGenericException(final String jsonErrorString, final Throwable cause) {
    super(jsonErrorString, cause);
  }

  public Procezz getFaultyProcezz() {
    return faultyProcezz;
  }

}
