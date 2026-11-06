package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InputGeneratorTest {

    @Test
    void testFixedSeed() {

        InputGenerator first = new InputGenerator(42);
        InputGenerator second = new InputGenerator(42);

        assertEquals(
                first.randomString(100, "ABCD"),
                second.randomString(100, "ABCD")
        );
    }

    @Test
    void testRepeatedString() {

        InputGenerator generator = new InputGenerator(42);

        assertEquals(
                "AAAAA",
                generator.repeatedString(5, 'A')
        );
    }

    @Test
    void testIdenticalPair() {

        InputGenerator generator = new InputGenerator(42);

        String[] pair = generator.identicalPair(10);

        assertEquals(pair[0], pair[1]);
        assertEquals(10, pair[0].length());
    }

    @Test
    void testDifferentPair() {

        InputGenerator generator = new InputGenerator(42);

        String[] pair = generator.differentPair(10);

        assertEquals("AAAAAAAAAA", pair[0]);
        assertEquals("BBBBBBBBBB", pair[1]);
    }

    @Test
    void testWorstCaseSearch() {

        InputGenerator generator = new InputGenerator(42);

        String[] input = generator.worstCaseSearch(10, 5);

        assertEquals("AAAAAAAAAA", input[0]);
        assertEquals("AAAAB", input[1]);
    }

    @Test
    void testRandomStringLength() {

        InputGenerator generator = new InputGenerator(42);

        String result = generator.randomString(100, "ABCD");

        assertEquals(100, result.length());
    }

    @Test
    void testInvalidInputs() {

        InputGenerator generator = new InputGenerator(42);

        assertThrows(
                IllegalArgumentException.class,
                () -> generator.randomString(-1, "ABCD")
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> generator.randomString(10, "")
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> generator.worstCaseSearch(5, 10)
        );
    }
}
