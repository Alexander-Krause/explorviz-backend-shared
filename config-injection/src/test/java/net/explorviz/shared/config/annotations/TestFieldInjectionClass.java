package net.explorviz.shared.config.annotations;

public class TestFieldInjectionClass {

  @Config("unittest.field.string")
  public String fieldTestString;

  @Config("unittest.field.boolean")
  public boolean fieldTestBoolean;

  @Config("unittest.field.int")
  public int fieldTestInt;

}
