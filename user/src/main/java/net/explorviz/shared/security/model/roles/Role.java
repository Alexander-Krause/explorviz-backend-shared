package net.explorviz.shared.security.model.roles;

import com.github.jasminb.jsonapi.annotations.Type;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import xyz.morphia.annotations.Entity;
import xyz.morphia.annotations.Id;

@Type("role")
@Entity("roles")
public class Role {

  @Id
  @com.github.jasminb.jsonapi.annotations.Id
  private String name;

  public Role() {
    // For MongoDB
  }

  public Role( final String name) {
    this.name = name;
  }

  

  public String getName() {
    return this.name;
  }



  public void setName(final String name) {
    this.name = name;
  }

  @Override
  public boolean equals(final Object obj) {
    if (obj == null) {
      return false;
    }
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof Role)) {
      return false;
    }

    final Role other = (Role) obj;

    return new EqualsBuilder().append(this.name, other.getName()).build();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder().append(this.name).build();
  }

  @Override
  public String toString() {
    return "{" + this.name + "}";
  }



}
