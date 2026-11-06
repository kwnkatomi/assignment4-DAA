package org.example;

public class TwoRowEditDistance implements EditDistance {

    private final Metrics metrics;

    public TwoRowEditDistance() {
        this(new Metrics());
    }

    public TwoRowEditDistance(Metrics metrics) {
        this.metrics = metrics;
    }

    @Override
    public int distance(String a, String b) {
        if (b.length() > a.length()) {
            String temp = a;
            a = b;
            b = temp;
        }

        int n = a.length();
        int m = b.length();

        int[] previous = new int[m + 1];
        int[] current = new int[m + 1];

        metrics.addAllocatedCells(2L * (m + 1));

        for (int j = 0; j <= m; j++) {
            previous[j] = j;
        }

        for (int i = 1; i <= n; i++) {
            current[0] = i;

            for (int j = 1; j <= m; j++) {

                metrics.incrementDpCellUpdates();

                if (a.charAt(i - 1) == b.charAt(j - 1)) {
                    current[j] = previous[j - 1];
                } else {

                    int delete = previous[j];
                    int insert = current[j - 1];
                    int replace = previous[j - 1];

                    current[j] = 1 + Math.min(
                            delete,
                            Math.min(insert, replace)
                    );
                }
            }

            int[] temp = previous;
            previous = current;
            current = temp;
        }

        return previous[m];
    }
}
