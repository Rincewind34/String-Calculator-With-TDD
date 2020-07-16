package de.rincewind;

import org.junit.Test;

public class StringCalculatorTest {
	
	@Test
	public void successfulInit() throws Exception {
		new StringCalculator();
	}
	
	@Test
	public void add_emptyString() throws Exception {
		new StringCalculator().add("");
	}
	
}
