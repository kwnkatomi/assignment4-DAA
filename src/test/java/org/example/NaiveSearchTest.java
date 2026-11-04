package org.example;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class NaiveSearchTest{
    private final StringSearch searcher = new NaiveSearch();

    @Test
    void testReferenceValues() {

        assertEquals(
                List.of(10),
                searcher.search(
                        "ABABDABACDABABCABAB",
                        "ABABCABAB"
                )
        );

        assertEquals(
                List.of(0, 1, 2, 3),
                searcher.search("AAAAA", "AA")
        );

        assertEquals(
                List.of(0, 2, 4),
                searcher.search("ABABABA", "ABA")
        );
    }

    @Test
    void testNoMatches() {
        assertEquals(
                List.of(),
                searcher.search("ABCDEFG", "XYZ")
        );
    }

    @Test
    void testPatternLongerThanText() {
        assertEquals(
                List.of(),
                searcher.search("ABC", "ABCDEFG")
        );
    }

    @Test
    void testEmptyPatternThrows() {
        assertThrows(
                IllegalArgumentException.class,
                () -> searcher.search("HELLO", "")
        );
    }

    @Test
    void testEmptyText() {
        assertEquals(
                List.of(),
                searcher.search("", "ABC")
        );
    }

    @Test
    void testEntireTextMatches() {
        assertEquals(
                List.of(0),
                searcher.search("HELLO", "HELLO")
        );
    }
}
