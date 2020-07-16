package de.rincewind;

import java.util.regex.Pattern;

public class StringCalculator {

	public static final String SEPARATOR = ",";
	private static final String SPLIT_PATTERN = Pattern.quote(StringCalculator.SEPARATOR) + "|\n";

	public int add(String numbers) {
		if (numbers.isEmpty()) {
			return 0;
		} else {
			return StringCalculator.sumNumbers(numbers);
		}
	}

	private static int sumNumbers(String numbers) {
		int sum = 0;
		String[] numbersArray = numbers.split(StringCalculator.SPLIT_PATTERN);
		
		for (int i = 0; i < numbersArray.length; i++) {
			sum += Integer.parseInt(numbersArray[i]);
		}
		
		return sum;
	}

}
