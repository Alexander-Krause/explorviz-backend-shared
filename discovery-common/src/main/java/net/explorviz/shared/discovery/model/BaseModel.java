package net.explorviz.shared.discovery.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.github.jasminb.jsonapi.annotations.Id;
import com.github.jasminb.jsonapi.annotations.Type;

// Needed for cyclical serialization
@JsonIdentityInfo(generator = ObjectIdGenerators.StringIdGenerator.class, property = "id")
@Type("base-model")
public class BaseModel {

  @JsonProperty("name")
  protected String name;

  @JsonProperty("last-discovery-time")
  protected long lastDiscoveryTime;

  @JsonProperty("is-hidden")
  protected boolean hidden;

  @JsonProperty("error-occured")
  protected boolean errorOccured;

  @JsonProperty("error-message")
  protected String errorMessage = "";

  @Id
  protected String id;

  public boolean isHidden() {
    return hidden;
  }

  public void setHidden(final boolean hidden) {
    this.hidden = hidden;
  }

  public String getId() {
    return id;
  }

  public void setId(final String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(final String name) {
    this.name = name;
  }

  public long getLastDiscoveryTime() {
    return lastDiscoveryTime;
  }

  public void setLastDiscoveryTime(final long lastDiscoveryTime) {
    this.lastDiscoveryTime = lastDiscoveryTime;
  }

  public boolean isErrorOccured() {
    return errorOccured;
  }

  public void setErrorOccured(final boolean errorOccured) {
    this.errorOccured = errorOccured;
  }

  public String getErrorMessage() {
    return errorMessage;
  }

  public void setErrorMessage(final String errorMessage) {
    this.errorMessage = errorMessage;
  }

}
