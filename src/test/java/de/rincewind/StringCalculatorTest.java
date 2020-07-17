package de.rincewind;

import static org.junit.Assert.assertEquals;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class StringCalculatorTest {

	private StringCalculator calculator;

	@Before
	public void setup() {
		this.calculator = new StringCalculator();
	}

	private void assertCalculatorAdd(String input, int expected) {
		assertEquals(expected, this.calculator.add(input));
	}

	private void assertNoNegativesMessage(String input, String negatives) {
		try {
			this.calculator.add(input);
		} catch (StringCalculator.NoNegatives exception) {
			assertEquals("no negatives allowed: " + negatives, exception.getMessage());
		}
	}

	@Test
	public void getCalledCount_NoCalls() throws Exception {
		Assert.assertEquals(0, this.calculator.getCalledCount());
	}

	@Test
	public void getCalledCount_OneCall() throws Exception {
		this.calculator.add("");

		Assert.assertEquals(1, this.calculator.getCalledCount());
	}

	@Test
	public void getCalledCount_CalledWithNegatives() throws Exception {
		try {
			this.calculator.add("-1");
		} catch (StringCalculator.NoNegatives exception) {}

		Assert.assertEquals(1, this.calculator.getCalledCount());
	}

	@Test
	public void add_emptyString() throws Exception {
		assertCalculatorAdd("", 0);
	}

	@Test
	public void add_oneDigit() throws Exception {
		assertCalculatorAdd("1", 1);
	}

	@Test
	public void add_twoNumbers() throws Exception {
		assertCalculatorAdd("1,2", 3);
	}

	@Test
	public void add_fourNumbers() throws Exception {
		assertCalculatorAdd("1,2,5,10", 18);
	}

	@Test
	public void add_newLineAsSeparator() throws Exception {
		assertCalculatorAdd("4\n3", 7);
	}

	@Test
	public void add_multipleSeparators() throws Exception {
		assertCalculatorAdd("4\n3,6\n8", 21);
	}

	@Test
	public void add_customSeparator() throws Exception {
		assertCalculatorAdd("//;\n1;2", 3);
	}

	@Test
	public void add_customSeparatorWithRegexChar() throws Exception {
		assertCalculatorAdd("//+\n6+7", 13);
	}

	@Test
	public void add_customSeparatorAsSpace() throws Exception {
		assertCalculatorAdd("// \n6 7", 13);
	}

	/*
	 * This is a feature NOT requested but implemented to serve additional clarity
	 */
	@Test(expected = StringCalculator.InvalidSingleCharSeparator.class)
	public void add_customSeparatorInvalidMultipleChars() throws Exception {
		assertCalculatorAdd("//;;\n6;;7", 13);
	}
	
	@Test(expected = StringCalculator.InvalidSingleCharSeparator.class)
	public void add_customSeparatorNoChar() throws Exception {
		assertCalculatorAdd("//\n6\n7", 13);
	}
	
	@Test
	public void add_customSeparatorWithOptionalBrackets() throws Exception {
		assertCalculatorAdd("//[;]\n6;7", 13);
	}
	
	@Test
	public void add_customSeparatorOpeningBrackets() throws Exception {
		assertCalculatorAdd("//[\n6[7", 13);
	}
	
	@Test
	public void add_customSeparatorClosingBrackets() throws Exception {
		assertCalculatorAdd("//]\n6]7", 13);
	}
	
	@Test(expected = StringCalculator.EmptySeparatorBrackets.class)
	public void add_customSeparatorEmptyBrackets() throws Exception {
		assertCalculatorAdd("//[]\n6\n7", 13);
	}
	
	@Test
	public void add_customSeparatorMultipleChars() throws Exception {
		assertCalculatorAdd("//[***]\n4***2***6\n4", 16);
	}
	
	@Test
	public void add_customSeparatorMultipleSeparators() throws Exception {
		assertCalculatorAdd("//[;][%]\n4;2%6%4", 16);
	}
	
	@Test(expected = StringCalculator.EmptySeparatorBrackets.class)
	public void add_customSeparatorEmptyBracketsAtEnd() throws Exception {
		assertCalculatorAdd("//[;][]\n4;2;6;4", 16);
	}
	
	@Test(expected = StringCalculator.EmptySeparatorBrackets.class)
	public void add_customSeparatorMultipleEmptyBracketsAtEnd() throws Exception {
		assertCalculatorAdd("//[;][][]\n4;2;6;4", 16);
	}
	
	@Test(expected = StringCalculator.EmptySeparatorBrackets.class)
	public void add_customSeparatorEmptyBracketsInMid() throws Exception {
		assertCalculatorAdd("//[;][][%]\n4;2%6;4", 16);
	}
	
	@Test
	public void add_customSeparatorMultipleSeparatorsMultipleChars() throws Exception {
		assertCalculatorAdd("//[**][;;][%]\n4**2%6;;4", 16);
	}

	@Test(expected = StringCalculator.NoNegatives.class)
	public void add_noNegativesAllowed() throws Exception {
		this.calculator.add("-2");
	}

	@Test
	public void add_noNegativesAllowedMessage() throws Exception {
		assertNoNegativesMessage("-2", "-2");
	}

	@Test
	public void add_multipleNegativesMessage() throws Exception {
		assertNoNegativesMessage("-4,5,-7", "-4,-7");
	}

	@Test
	public void add_ignoreOver1000() throws Exception {
		assertCalculatorAdd("1001", 0);
	}

	@Test
	public void add_ignoreOver1000WithOtherNumberss() throws Exception {
		assertCalculatorAdd("1001,9,1070,0,1000", 1009);
	}

}
