package de.rincewind;

import static org.junit.Assert.assertEquals;

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
		assertEquals(0, this.calculator.add(""));
	}

	@Test
	public void add_oneDigit() throws Exception {
		assertEquals(1, this.calculator.add("1"));
	}

	@Test
	public void add_twoNumbers() throws Exception {
		assertEquals(3, this.calculator.add("1,2"));
	}

	@Test
	public void add_fourNumbers() throws Exception {
		assertEquals(18, this.calculator.add("1,2,5,10"));
	}

	@Test
	public void add_newLineAsSeparator() throws Exception {
		assertEquals(7, this.calculator.add("4\n3"));
	}

	@Test
	public void add_multipleSeparators() throws Exception {
		assertEquals(21, this.calculator.add("4\n3,6\n8"));
	}

	@Test
	public void add_customSeparator() throws Exception {
		assertEquals(3, this.calculator.add("//;\n1;2"));
	}

}
