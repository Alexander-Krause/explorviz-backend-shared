package net.explorviz.shared.config.annotations;

public class TestConstructorTopLevelInjectionClass {

  public String testStringForConstructorTopLevelInjection;

  @Config("unittest.constructor.toplevel.string")
  public TestConstructorTopLevelInjectionClass(String testString) {
    this.testStringForConstructorTopLevelInjection = testString;
  }

}
