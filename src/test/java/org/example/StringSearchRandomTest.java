package org.example;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StringSearchRandomTest {

    private final StringSearch naive = new NaiveSearch();
    private final StringSearch kmp = new KMPSearch();

    private String randomString(Random random, int length,
                                String alphabet) {

        StringBuilder result = new StringBuilder();

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(alphabet.length());
            result.append(alphabet.charAt(index));
        }

        return result.toString();
    }

    @Test
    void test100RandomTexts() {

        Random random = new Random(42);

        for (int test = 0; test < 100; test++) {

            int textLength = random.nextInt(101);
            int patternLength = 1 + random.nextInt(15);

            String alphabet = (test % 2 == 0) ? "ABCD" : "AB";

            String text = randomString(
                    random, textLength, alphabet
            );

            String pattern = randomString(
                    random, patternLength, alphabet
            );

            List<Integer> expected =
                    naive.search(text, pattern);

            List<Integer> actual =
                    kmp.search(text, pattern);

            assertEquals(
                    expected,
                    actual,
                    "Mismatch for text: " + text
                            + " and pattern: " + pattern
            );
        }
    }

    @Test
    void testOverlappingPatterns() {

        assertEquals(
                naive.search("AAAAAAAAAA", "AAA"),
                kmp.search("AAAAAAAAAA", "AAA")
        );

        assertEquals(
                naive.search("ABABABABAB", "ABAB"),
                kmp.search("ABABABABAB", "ABAB")
        );
    }
}
