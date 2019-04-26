package net.explorviz.shared.landscape.model.store;

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

  public Timestamp(final String id, final long timestampValue, final int requests) {
    super(id);
    this.setTimestamp(timestampValue);
    this.setTotalRequests(requests);
  }

  public Timestamp(final String id) {
    super(id);
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
