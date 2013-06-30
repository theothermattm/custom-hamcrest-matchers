package com.theothermattm.matchers;

import static com.theothermattm.matchers.IsEqualToWithNonNullExpectedResult.isEqualToWithNonNullExpectedResult;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

/**
 * Unit tests for {@link IsEqualToWithNonNullExpectedResult}
 * 
 * @author mattm
 */
public class IsEqualToWithNonNullExpectedResultTest {

	@Test
	public void whenBothArgumentsAreNullTheMatcherFailsWithASpecificDescribeToMessage() {
		// given
		String argument = null;
		String expected = null;

		// when
		boolean exceptionThrown = false;
		try {
			assertThat(argument, isEqualToWithNonNullExpectedResult(expected));

			// then
		} catch (AssertionError e) {
			exceptionThrown = true;
			assertThat(
					"The assertion message should mention that the expected argument isn't null",
					e.getMessage(),
					containsString("expected argument should also not be null"));
		}
		assertThat(
				"When the expected argument is null, an assertion error should be thrown",
				exceptionThrown, is(true));
	}

	@Test(expected = AssertionError.class)
	public void whenTheResultArgumentIsNotNullButTheExpectedArgumentIsNullTheMatcherFails() {
		// given
		String argument = "not null value";
		String expected = null;

		// when
		assertThat(
				"when the expected result is null AND not equal to the actual, failure results due to inequality AND having a null expected result",
				argument, isEqualToWithNonNullExpectedResult(expected));

		// then
		// assertion error
	}

	@Test(expected = AssertionError.class)
	public void whenTheResultArgumentIsNullButTheExpectedArgumentIsNotNullTheMatcherFails() {
		// given
		String argument = null;
		String expected = "expected result";

		// when
		assertThat(
				"when the result argument is null, but the expected argument isn't, the default isEquals functionality should prevail and the matcher should fail",
				argument, isEqualToWithNonNullExpectedResult(expected));

		// then
		// assertion error
	}

	@Test
	public void whenBothArgumentsAreTheSameTheMatcherPasses() {
		// given
		String argument = "test";
		String expected = "test";

		// when
		assertThat(
				"when both arguments are the same, the default isEqualTo functionality should prevail and the matcher should pass",
				argument, isEqualToWithNonNullExpectedResult(expected));

		// then
		// success, they're the same!
	}
}
