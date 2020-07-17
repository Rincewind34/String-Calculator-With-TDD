package de.rincewind;

import java.util.Arrays;
import java.util.stream.Collectors;

public class StringCalculator {

	public static final String DEFAULT_SEPARATOR = ",";
	public static final String CUSTOM_SEPARATOR_INDICATION = "//";

	public int add(String input) {
		return new Invoker(input).calculate();
	}

	private static class Invoker {

		private String separationRegex;

		private String input;

		public Invoker(String input) {
			this.input = input;
			this.setSeparationPattern(StringCalculator.DEFAULT_SEPARATOR);
		}

		public int calculate() {
			this.readAndStripSeparator();

			if (this.input.isEmpty()) {
				return 0;
			} else {
				return this.sumRemainingInput();
			}
		}

		private void readAndStripSeparator() {
			if (this.input.startsWith(StringCalculator.CUSTOM_SEPARATOR_INDICATION)) {
				this.setSeparationPattern(this.readCustomSeparator());
				this.stripCustomSeparatorSection();
			}
		}

		private String readCustomSeparator() {
			String separatorSection = this.input.substring(0, this.findEndOfSeparatorSection());
			String separator = separatorSection.substring(StringCalculator.CUSTOM_SEPARATOR_INDICATION.length());
			return separator;
		}

		private void stripCustomSeparatorSection() {
			this.input = this.input.substring(this.findEndOfSeparatorSection() + 1);
		}

		private int findEndOfSeparatorSection() {
			return this.input.indexOf('\n');
		}

		private int sumRemainingInput() {
			int[] numbers = this.parseCurrentInput();
			Invoker.assertNoNegatives(numbers);
			return Invoker.sumNumbers(numbers);
		}

		private int[] parseCurrentInput() {
			String[] numbersArray = this.input.split(this.separationRegex);
			int[] result = new int[numbersArray.length];

			for (int i = 0; i < numbersArray.length; i++) {
				result[i] = Integer.parseInt(numbersArray[i]);
			}

			return result;
		}

		private static void assertNoNegatives(int[] numbers) {
			String joinedNegatives = Arrays.stream(numbers).filter(n -> n < 0).mapToObj(Integer::toString)
					.collect(Collectors.joining(","));

			if (!joinedNegatives.isEmpty()) {
				throw new NoNegatives(joinedNegatives);
			}
		}

		private static int sumNumbers(int[] numbers) {
			return Arrays.stream(numbers).sum();
		}

		private void setSeparationPattern(String separationPattern) {
			this.separationRegex = separationPattern + "|\n";
		}

	}

	public static class NoNegatives extends RuntimeException {

		private static final long serialVersionUID = -51367231639435453L;

		public NoNegatives(String numbers) {
			super("no negatives allowed: " + numbers);
		}

	}

}
