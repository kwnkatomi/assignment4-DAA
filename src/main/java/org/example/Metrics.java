package org.example;

public class Metrics{

    private long recursiveCalls;
    private long dpCellUpdates;
    private long comparisons;
    private long allocatedCells;

    public void incrementRecursiveCalls() {
        recursiveCalls++;
    }

    public void incrementDpCellUpdates() {
        dpCellUpdates++;
    }

    public void incrementComparisons() {
        comparisons++;
    }

    public void addAllocatedCells(long cells) {
        allocatedCells += cells;
    }

    public long getRecursiveCalls() {
        return recursiveCalls;
    }

    public long getDpCellUpdates() {
        return dpCellUpdates;
    }

    public long getComparisons() {
        return comparisons;
    }

    public long getAllocatedCells() {
        return allocatedCells;
    }

    public void reset() {
        recursiveCalls = 0;
        dpCellUpdates = 0;
        comparisons = 0;
        allocatedCells = 0;
    }
}
