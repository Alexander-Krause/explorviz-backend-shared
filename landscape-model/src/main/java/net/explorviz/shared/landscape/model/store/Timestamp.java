package net.explorviz.shared.landscape.model.store;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.jasminb.jsonapi.annotations.Type;
import net.explorviz.shared.landscape.model.helper.BaseEntity;

/**
 * Model representing timestamps (a single software landscape for a specific UNIX timestamp in
 * milliseconds).
 */
@SuppressWarnings("serial")
@Type("timestamp")
public class Timestamp extends BaseEntity {

  private long timestamp;
  private int totalRequests;

  @JsonCreator
  public Timestamp(@JsonProperty("id") final String id,
      @JsonProperty("timestampValue") final long timestampValue,
      @JsonProperty("requests") final int requests) {
    // super(id);

    this.id = id;
    this.setTimestamp(timestampValue);
    this.setTotalRequests(requests);
  }

  public Timestamp(@JsonProperty("id") final String id) {
    this.id = id;
    // super(id);
    this.setTimestamp(System.currentTimeMillis());
  }

  public long getTimestamp() {
    return this.timestamp;
  }

  public void setTimestamp(final long timestamp) {
    this.timestamp = timestamp;
  }

  public int getTotalRequests() {
    return this.totalRequests;
  }

  public void setTotalRequests(final int totalRequests) {
    this.totalRequests = totalRequests;
  }

}
