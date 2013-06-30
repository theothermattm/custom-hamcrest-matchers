custom-hamcrest-matchers
========================

Some custom [Hamcrest](https://code.google.com/p/hamcrest/) matchers I find useful.

## [IsEqualToWithNonNullExpectedResult](https://github.com/theothermattm/custom-hamcrest-matchers/blob/master/src/main/java/com/theothermattm/matchers/IsEqualToWithNonNullExpectedResult.java)

Extension of the Hamcrest core [IsEqual](http://hamcrest.org/JavaHamcrest/javadoc/1.3/org/hamcrest/core/IsEqual.html) matcher that will check the expected argument to make
sure that in addition to normal equality, that the expected result isn't
null.

This can be useful for eliminating false-positives in tests where a property
in a source object is being mapped to a property in a target object.

When you are testing these type of mappings, your source object fixtures (in
most cases) should be a non-null value so that the source-to-target mapping
can be tested appropriately. However, if the test fixture's source property
inadvertently gets set to null, you may get a false-positive on your tests
because you are asserting that null is equal to null. Oh, no! Bad news.

For example, if you have common classes which build test fixtures and want to
ensure that you are not asserting that null equals null because a test
fixture creation method was inadvertently changed.

This is perhaps best illustrated with an example:
```
// given 
MyTestFixture fixture = MyTestFixtureCreator.createTestFixture();
	 
// when 
MyResult result = myObjectUnderTest.process(fixture);
	 
// then 
// this matcher will ensure that your test fixture creator class
// won't have inadvertently set your source property to null
// so that you get a false positive when null == null
assertThat(result.getTargetProperty(), isEqualToWithNonNullExpectedResult(fixture.getSourceProperty())));
```
