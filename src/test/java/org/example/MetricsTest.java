package org.example;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MetricsTest {

    @Test
    void testReset() {

        Metrics metrics = new Metrics();

        metrics.incrementRecursiveCalls();
        metrics.incrementRecursiveCalls();
        metrics.incrementComparisons();

        assertEquals(2, metrics.getRecursiveCalls());
        assertEquals(1, metrics.getComparisons());

        metrics.reset();

        assertEquals(0, metrics.getRecursiveCalls());
        assertEquals(0, metrics.getComparisons());
    }

    @Test
    void testRecursiveCounter() {

        Metrics metrics = new Metrics();

        RecursiveEditDistance algorithm =
                new RecursiveEditDistance(metrics);

        assertEquals(1, algorithm.distance("a", "b"));

        assertEquals(4, metrics.getRecursiveCalls());
    }

    @Test
    void testIdenticalStringCounter() {

        Metrics metrics = new Metrics();

        RecursiveEditDistance algorithm =
                new RecursiveEditDistance(metrics);

        assertEquals(0, algorithm.distance("abc", "abc"));
        assertEquals(4, metrics.getRecursiveCalls());
    }

    @Test
    void testTableMetrics() {

        Metrics metrics = new Metrics();

        TableEditDistance algorithm =
                new TableEditDistance(metrics);

        assertEquals(1, algorithm.distance("cat", "cut"));

        assertEquals(9, metrics.getDpCellUpdates());
        assertEquals(16, metrics.getAllocatedCells());
    }

    @Test
    void testTwoRowMetrics() {

        Metrics metrics = new Metrics();

        TwoRowEditDistance algorithm =
                new TwoRowEditDistance(metrics);

        assertEquals(1, algorithm.distance("cat", "cut"));

        assertEquals(9, metrics.getDpCellUpdates());
        assertEquals(8, metrics.getAllocatedCells());
    }


    @Test
    void testNaiveComparisonCounter() {

        Metrics metrics = new Metrics();
        NaiveSearch algorithm = new NaiveSearch(metrics);

        assertEquals(
                java.util.List.of(0, 1, 2, 3),
                algorithm.search("AAAAA", "AA")
        );

        assertEquals(8, metrics.getComparisons());
    }

    @Test
    void testKmpComparisonCounter() {

        Metrics metrics = new Metrics();
        KMPSearch algorithm = new KMPSearch(metrics);

        assertEquals(
                java.util.List.of(0, 1, 2, 3),
                algorithm.search("AAAAA", "AA")
        );

        assertEquals(6, metrics.getComparisons());
    }

    @Test
    void testKmpLpsCounter() {

        Metrics metrics = new Metrics();
        KMPSearch algorithm = new KMPSearch(metrics);

        assertArrayEquals(
                new int[]{0, 0, 1, 2},
                algorithm.lps("ABAB")
        );

        assertEquals(3, metrics.getComparisons());
    }


}
