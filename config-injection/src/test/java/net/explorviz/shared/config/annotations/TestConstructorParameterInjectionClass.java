package net.explorviz.shared.config.annotations;

public class TestConstructorParameterInjectionClass {

  public String testStringForConstructorParameterInjection;
  public boolean testBooleanForConstructorParameterInjection;
  public int testIntForConstructorParameterInjection;

  public TestConstructorParameterInjectionClass(
      @Config("unittest.constructor.parameter.string") String testString,
      @Config("unittest.constructor.parameter.boolean") boolean testBool,
      @Config("unittest.constructor.parameter.int") int testInt) {

    this.testStringForConstructorParameterInjection = testString;
    this.testBooleanForConstructorParameterInjection = testBool;
    this.testIntForConstructorParameterInjection = testInt;
  }

}
