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

}
