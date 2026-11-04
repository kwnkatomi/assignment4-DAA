package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TwoRowEditDistanceTest {

    private final EditDistance algorithm =
            new TwoRowEditDistance();

    @Test
    void testReferenceValues() {
        assertEquals(3, algorithm.distance("kitten", "sitting"));
        assertEquals(2, algorithm.distance("flaw", "lawn"));
        assertEquals(5, algorithm.distance("intention", "execution"));
    }

    @Test
    void testEmptyStrings() {
        assertEquals(0, algorithm.distance("", ""));
        assertEquals(3, algorithm.distance("", "abc"));
        assertEquals(3, algorithm.distance("abc", ""));
    }

    @Test
    void testIdenticalStrings() {
        assertEquals(0, algorithm.distance("abc", "abc"));
    }

    @Test
    void testSingleEdit() {
        assertEquals(1, algorithm.distance("cat", "cut"));
        assertEquals(1, algorithm.distance("cat", "cats"));
        assertEquals(1, algorithm.distance("cats", "cat"));
    }

    @Test
    void testDifferentLengthStrings() {
        assertEquals(3, algorithm.distance("a", "abcd"));
        assertEquals(3, algorithm.distance("abcd", "a"));
    }
}
