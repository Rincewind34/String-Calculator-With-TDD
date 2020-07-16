package de.rincewind;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class StringCalculatorTest {

	private StringCalculator calculator;

	@Before
	public void setup() {
		this.calculator = new StringCalculator();
	}

	@Test
	public void add_emptyString() throws Exception {
		Assert.assertEquals(0, this.calculator.add(""));
	}

	@Test
	public void add_oneDigit() throws Exception {
		Assert.assertEquals(1, this.calculator.add("1"));
	}

}
