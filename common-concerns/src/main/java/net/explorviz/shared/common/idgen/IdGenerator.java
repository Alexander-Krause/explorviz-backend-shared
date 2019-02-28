package net.explorviz.shared.common.idgen;

import javax.inject.Inject;

public class IdGenerator {


  @Inject
  private ServiceIdGenerator serviceIdGenerator;

  @Inject
  private EntityIdGenerator entityIdGenerator;


  private String serviceId;

  private String prefix;

  public IdGenerator(String servicePrefix) {
    this.prefix = servicePrefix;
    this.serviceId = serviceIdGenerator.getServiceId();
  }

  public String generateId() {
    String entityId = entityIdGenerator.getId();

    return prefix + serviceId + "-" + entityId;
  }

}
