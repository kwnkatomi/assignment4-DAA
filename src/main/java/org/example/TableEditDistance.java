package org.example;

public class TableEditDistance implements EditDistance {

    private final Metrics metrics;

    public TableEditDistance() {
        this(new Metrics());
    }

    public TableEditDistance(Metrics metrics) {
        this.metrics = metrics;
    }

    @Override
    public int distance(String a, String b) {

        int n = a.length();
        int m = b.length();

        int[][] dp = new int[n + 1][m + 1];

        metrics.addAllocatedCells((long) (n + 1) * (m + 1));

        for (int i = 0; i <= n; i++) {
            dp[i][0] = i;
        }

        for (int j = 0; j <= m; j++) {
            dp[0][j] = j;
        }

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {

                metrics.incrementDpCellUpdates();
                if (a.charAt(i - 1) == b.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    int delete = dp[i - 1][j];
                    int insert = dp[i][j - 1];
                    int replace = dp[i - 1][j - 1];

                    dp[i][j] = 1 + Math.min(
                            delete,
                            Math.min(insert, replace)
                    );
                }
            }
        }

        return dp[n][m];
    }
}
