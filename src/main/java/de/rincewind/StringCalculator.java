package de.rincewind;

import java.util.regex.Pattern;

public class StringCalculator {

	public static final String SEPARATOR = ",";
	private static final String QUOTED_SEPARATOR = Pattern.quote(StringCalculator.SEPARATOR);

	public int add(String numbers) {
		if (numbers.isEmpty()) {
			return 0;
		} else {
			return StringCalculator.sumNumbers(numbers);
		}
	}

	private static int sumNumbers(String numbers) {
		String[] numbersArray = numbers.split(StringCalculator.QUOTED_SEPARATOR);

		if (numbersArray.length == 1) {
			return Integer.parseInt(numbersArray[0]);
		} else {
			return Integer.parseInt(numbersArray[0]) + Integer.parseInt(numbersArray[1]);
		}
	}

}
