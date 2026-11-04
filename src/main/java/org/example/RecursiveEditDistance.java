package org.example;

public class RecursiveEditDistance implements EditDistance {

    @Override
    public int distance(String a, String b) {
        return calculate(a, b, a.length(), b.length());
    }

    private int calculate(String a, String b, int n, int m) {

        if (n == 0) {
            return m;
        }

        if (m == 0) {
            return n;
        }

        if (a.charAt(n - 1) == b.charAt(m - 1)) {
            return calculate(a, b, n - 1, m - 1);
        }

        int delete = calculate(a, b, n - 1, m);
        int insert = calculate(a, b, n, m - 1);
        int replace = calculate(a, b, n - 1, m - 1);

        return 1 + Math.min(delete, Math.min(insert, replace));
    }
}
