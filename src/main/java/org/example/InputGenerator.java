package org.example;

import java.util.Random;

public class InputGenerator {
    private final Random random;

    public InputGenerator(long seed) {
        this.random = new Random(seed);
    }

    public String randomString(int length, String alphabet) {

        if (length < 0) {
            throw new IllegalArgumentException(
                    "Length cannot be negative"
            );
        }

        if (alphabet == null || alphabet.isEmpty()) {
            throw new IllegalArgumentException(
                    "Alphabet cannot be empty"
            );
        }

        StringBuilder result = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(alphabet.length());
            result.append(alphabet.charAt(index));
        }

        return result.toString();
    }

    public String repeatedString(int length, char character) {

        if (length < 0) {
            throw new IllegalArgumentException(
                    "Length cannot be negative"
            );
        }

        return String.valueOf(character).repeat(length);
    }

    public String[] identicalPair(int length) {
        String value = repeatedString(length, 'A');

        return new String[]{value, value};
    }

    public String[] differentPair(int length) {
        return new String[]{
                repeatedString(length, 'A'),
                repeatedString(length, 'B')
        };
    }

    public String[] randomPair(int length) {
        return new String[]{
                randomString(length, "ABCD"),
                randomString(length, "ABCD")
        };
    }

    public String[] worstCaseSearch(int textLength, int patternLength) {
        if (patternLength < 2 || patternLength > textLength) {
            throw new IllegalArgumentException(
                    "Pattern length must be between 2 and text length"
            );
        }

        String text = repeatedString(textLength, 'A');

        String pattern = repeatedString(patternLength - 1, 'A') + "B";

        return new String[]{text, pattern};
    }

    public String[] randomSearch(int textLength, int patternLength) {

        if (patternLength < 1 || patternLength > textLength) {
            throw new IllegalArgumentException(
                    "Invalid pattern length"
            );
        }

        return new String[]{
                randomString(textLength, "ABCD"),
                randomString(patternLength, "ABCD")
        };
    }
}
