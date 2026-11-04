package org.example;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class KMPSearchTest {

    private final KMPSearch searcher = new KMPSearch();

    @Test
    void testLpsReferenceValues() {

        assertArrayEquals(
                new int[]{0, 1, 2, 0, 1, 2, 3, 3},
                searcher.lps("AAACAAAA")
        );

        assertArrayEquals(
                new int[]{0, 0, 1, 2, 0, 1, 2, 3, 4},
                searcher.lps("ABABCABAB")
        );
    }

    @Test
    void testSearchReferenceValues() {

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

    @Test
    void testRepeatedPattern() {
        assertEquals(
                List.of(0, 3, 6),
                searcher.search("ABCABCABC", "ABC")
        );
    }
}
