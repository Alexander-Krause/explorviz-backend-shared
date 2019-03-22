package net.explorviz.shared.landscape.model.helper;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.github.jasminb.jsonapi.LongIdHandler;
import com.github.jasminb.jsonapi.annotations.Id;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import javax.inject.Inject;
import net.explorviz.shared.common.idgen.EntityIdGenerator;
import net.explorviz.shared.common.idgen.IdGenerator;
import net.explorviz.shared.common.idgen.ServiceIdGenerator;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;

/**
 * Base Model for all other data model entities.
 */
@SuppressWarnings("serial")
@JsonIdentityInfo(generator = ObjectIdGenerators.StringIdGenerator.class, property = "id")
public class BaseEntity implements Serializable {

  private static final AtomicLong ID_GENERATOR = new AtomicLong();
  
  private static IdGenerator _ID_GENERATOR;
  
  public static void initialize(IdGenerator idGenerator) {
    _ID_GENERATOR = idGenerator;
  }

  @Id
  private String id;

  /*
   * This attribute can be used by extensions to insert custom properties to any meta-model object.
   * Non primitive types (your custom model class) must be annotated with type annotations, e.g., as
   * shown in any model entity
   */
  private final Map<String, Object> extensionAttributes = new HashMap<>();

  
  public BaseEntity() { 
    
    if (_ID_GENERATOR == null) {
      throw new IllegalStateException("No id generator set. Call BaseEntity.initialize() first");
    }
    
    this.id = _ID_GENERATOR.generateId();
    
  }

  public String getId() {
    return this.id;
  }
  
 

  public void updateId() {
    this.id = _ID_GENERATOR.generateId();
  }

 

  public Map<String, Object> getExtensionAttributes() {
    return this.extensionAttributes;
  }

}
