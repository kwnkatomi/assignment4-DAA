package org.example;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EditDistanceRandomTest {

    private final EditDistance recursive =
            new RecursiveEditDistance();

    private final EditDistance table =
            new TableEditDistance();

    private final EditDistance twoRows =
            new TwoRowEditDistance();

    private String randomString(Random random, int length) {

        String alphabet = "abcd";
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(alphabet.length());
            result.append(alphabet.charAt(index));
        }

        return result.toString();
    }

    @Test
    void test100RandomPairs() {

        Random random = new Random(42);

        for (int test = 0; test < 100; test++) {

            int lengthA = random.nextInt(7);
            int lengthB = random.nextInt(7);

            String a = randomString(random, lengthA);
            String b = randomString(random, lengthB);

            int recursiveResult = recursive.distance(a, b);
            int tableResult = table.distance(a, b);
            int twoRowsResult = twoRows.distance(a, b);

            assertEquals(
                    recursiveResult,
                    tableResult,
                    "Table mismatch for: " + a + " / " + b
            );

            assertEquals(
                    recursiveResult,
                    twoRowsResult,
                    "Two-row mismatch for: " + a + " / " + b
            );
        }
    }
}
