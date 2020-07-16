package de.rincewind;

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
				this.readCustomSeparator();
				this.stripCustomSeparatorSection();
			}
		}

		private void readCustomSeparator() {
			String separatorSection = this.input.substring(0, this.input.indexOf('\n'));
			String separator = separatorSection.substring(StringCalculator.CUSTOM_SEPARATOR_INDICATION.length());
			this.setSeparationPattern(separator);
		}

		private void stripCustomSeparatorSection() {
			this.input = this.input.substring(this.input.indexOf('\n') + 1);
		}

		private int sumRemainingInput() {
			int sum = 0;
			String[] numbersArray = this.input.split(this.separationRegex);

			for (String number : numbersArray) {
				sum = sum + parseNumber(number);
			}

			return sum;
		}

		private static int parseNumber(String number) {
			int n = Integer.parseInt(number);

			if (n >= 0) {
				return n;
			} else {
				throw new NoNegatives();
			}
		}

		private void setSeparationPattern(String separationPattern) {
			this.separationRegex = separationPattern + "|\n";
		}

	}

	@SuppressWarnings("serial")
	public static class NoNegatives extends RuntimeException {

	}

}
