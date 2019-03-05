package net.explorviz.shared.common.idgen;

import javax.inject.Inject;

public class IdGenerator {


  @Inject
  private ServiceIdGenerator serviceIdGenerator;

  @Inject
  private EntityIdGenerator entityIdGenerator;

  private String serviceId;

  private String prefix;

  @ConfigValues(@Config("service.prefix"))
  public IdGenerator(String servicePrefix) {
    this.prefix = servicePrefix;
  }

  public String generateId() {
    if (serviceId == null || "".equals(serviceId)) {
      this.serviceId = serviceIdGenerator.getServiceId();
    }
    String entityId = entityIdGenerator.getId();

    return prefix + serviceId + "-" + entityId;
  }

}
