package net.explorviz.shared.landscape.model.helper;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.github.jasminb.jsonapi.annotations.Id;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import net.explorviz.shared.common.idgen.IdGenerator;

/**
 * Base Model for all other data model entities.
 */
@SuppressWarnings("serial")
@JsonIdentityInfo(generator = ObjectIdGenerators.StringIdGenerator.class, property = "id")
public class BaseEntity implements Serializable {

  private static IdGenerator idGenerator;
  
  /*
   * This attribute can be used by extensions to insert custom properties to any meta-model object.
   * Non primitive types (your custom model class) must be annotated with type annotations, e.g., as
   * shown in any model entity
   */
  private final Map<String, Object> extensionAttributes = new HashMap<>();

  @Id
  private String id;
  
  /**
   * Base constructor for all entities within a landscape/replay.
   */
  public BaseEntity() { 
    
    if (idGenerator == null) {
      throw new IllegalStateException("No id generator set. Call BaseEntity.initialize() first");
    }
    
    this.id = idGenerator.generateId();
    
  }

  
  public static void initialize(IdGenerator idGenerator) {
    BaseEntity.idGenerator = idGenerator;
  }
  
  public String getId() {
    return this.id;
  }
  
 

  public void updateId() {
    this.id = idGenerator.generateId();
  }

 

  public Map<String, Object> getExtensionAttributes() {
    return this.extensionAttributes;
  }

}
