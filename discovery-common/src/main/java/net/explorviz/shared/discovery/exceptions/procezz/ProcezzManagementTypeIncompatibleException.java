package net.explorviz.shared.discovery.exceptions.procezz;

import net.explorviz.shared.discovery.model.Procezz;

public class ProcezzManagementTypeIncompatibleException extends Exception {

  private static final long serialVersionUID = 1L;

  private Procezz faultyProcezz;

  public ProcezzManagementTypeIncompatibleException(final String message, final Throwable cause,
      final Procezz p) {
    super(message, cause);
    this.faultyProcezz = p;
  }

  public ProcezzManagementTypeIncompatibleException(final String message, final Throwable cause) {
    super(message, cause);
  }

  public Procezz getFaultyProcezz() {
    return faultyProcezz;
  }

}
