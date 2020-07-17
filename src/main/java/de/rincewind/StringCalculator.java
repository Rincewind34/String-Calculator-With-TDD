package de.rincewind;

import java.util.Arrays;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class StringCalculator {

	public static final String DEFAULT_SEPARATOR = ",";
	public static final String CUSTOM_SEPARATOR_INDICATION = "//";
	public static final int MAXIMAL_NUMBER_COUNTED = 1000;

	private int timesCalled;

	public int add(String input) {
		this.timesCalled++;
		return new Invoker(input).calculate();
	}

	public int getCalledCount() {
		return this.timesCalled;
	}

	private static class Invoker {

		private String separationRegex;

		private String input;

		public Invoker(String input) {
			this.input = input;
			this.setSeparationString(StringCalculator.DEFAULT_SEPARATOR);
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
				this.parseSeparationPattern(this.readCustomSeparatorPattern());
				this.stripCustomSeparatorSection();
			}
		}

		private String readCustomSeparatorPattern() {
			String separatorSection = this.input.substring(0, this.findEndOfSeparatorSection());
			String separatorPattern = separatorSection.substring(StringCalculator.CUSTOM_SEPARATOR_INDICATION.length());
			return separatorPattern;
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
			return Arrays.stream(numbers).filter(n -> n <= StringCalculator.MAXIMAL_NUMBER_COUNTED).sum();
		}

		private void parseSeparationPattern(String separationPattern) {
			if (Invoker.isPatternSingleCharShortcut(separationPattern)) {
				this.setSeparationString(separationPattern);
			} else if (!separationPattern.startsWith("[")) {
				throw new InvalidSingleCharSeparator();
			} else {
				this.parseBracketedSeparator(separationPattern);
			}
		}

		private static boolean isPatternSingleCharShortcut(String separationPattern) {
			return separationPattern.length() == 1;
		}

		private void parseBracketedSeparator(String separationPattern) {
			separationPattern = Invoker.stripFirstAndLastSeparatorBrackets(separationPattern);

			// The default limit in String#split is zero; as a special setting that would remove trailing empty entries
			final int SPLIT_AS_OFTEN_AS_POSSIBLE = -1;
			String[] separators = separationPattern.split(Pattern.quote("]["), SPLIT_AS_OFTEN_AS_POSSIBLE);

			if (Invoker.isAnySeparatorEmpty(separators)) {
				throw new EmptySeparatorBrackets();
			}

			this.setSeparationString(separators);
		}

		private static boolean isAnySeparatorEmpty(String[] separators) {
			return Arrays.stream(separators).anyMatch(String::isEmpty);
		}

		private void setSeparationString(String... separationPattern) {
			this.separationRegex = Arrays.stream(separationPattern).map(Pattern::quote).collect(Collectors.joining("|")) + "|\n";
		}

		private static String stripFirstAndLastSeparatorBrackets(String separationPattern) {
			return separationPattern.substring(1, separationPattern.length() - 1);
		}

	}

	public static class NoNegatives extends RuntimeException {

		private static final long serialVersionUID = -51367231639435453L;

		public NoNegatives(String numbers) {
			super("no negatives allowed: " + numbers);
		}

	}

	public static class InvalidSingleCharSeparator extends RuntimeException {

		private static final long serialVersionUID = -8142676040236064515L;

	}

	public static class EmptySeparatorBrackets extends RuntimeException {

		private static final long serialVersionUID = -7320138582938197618L;

	}

}
