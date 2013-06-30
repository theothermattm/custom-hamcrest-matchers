package com.theothermattm.matchers;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.core.IsEqual;

/**
 * Extension of {@link IsEqual} that will check the expected argument to make
 * sure that in addition to normal equality, that the expected result isn't
 * null.
 * 
 * This can be useful for eliminating false-positives in tests where a property
 * in a source object is being mapped to a property in a target object.
 * 
 * When you are testing these type of mappings, your source object fixtures (in
 * most cases) should be a non-null value so that the source-to-target mapping
 * can be tested appropriately. However, if the test fixture's source property
 * inadvertently gets set to null, you may get a false-positive on your tests
 * because you are asserting that null is equal to null. Oh, no! Bad news.
 * 
 * For example, if you have common classes which build test fixtures and want to
 * ensure that you are not asserting that null equals null because a test
 * fixture creation method was inadvertently changed.
 * 
 * This is perhaps best illustrated with an example:
 * <pre>
 * 		// given 
 * 		MyTestFixture fixture = MyTestFixtureCreator.createTestFixture();
 * 	 
 * 		// when 
 * 		MyResult result = myObjectUnderTest.process(fixture);
 * 	 
 * 		// then 
 *      // this matcher will ensure that your test fixture creator class
 *      // won't have inadvertently set your source property to null
 *      // so that you get a false positive when null == null
 * 		assertThat(result.getTargetProperty(),
 * 			 isEqualToWithNonNullExpectedResult(fixture.getSourceProperty())));
 * </pre>
 * 
 * @author mattm
 * 
 * @param <T>
 */
public class IsEqualToWithNonNullExpectedResult<T> extends IsEqual<T> {

	public IsEqualToWithNonNullExpectedResult(T equalArg) {
		super(equalArg);
	}

	public boolean matches(Object arg) {

		boolean matches = super.matches(arg);

		/*
		 * If the given expected argument is null, fail the matcher so that you
		 * can call attention to the fact that your test data might not be setup
		 * right
		 */
		if (arg == null) {
			matches = false;
		}

		return matches;
	}

	public void describeTo(Description description) {
		super.describeTo(description);
		description
				.appendText(" however, the expected argument should also not be null. Did you setup your expected data correctly?");
	}

	/**
	 * Is the value equal to another value, as tested by the
	 * {@link java.lang.Object#equals} invokedMethod? AND the value is not null.
	 */
	@Factory
	public static <T> Matcher<T> isEqualToWithNonNullExpectedResult(T operand) {
		return new IsEqualToWithNonNullExpectedResult<T>(operand);
	}

}
