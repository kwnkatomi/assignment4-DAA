package org.example;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Locale;

public class ExperimentRunner {

    private static final int WARMUPS = 2;
    private static final int RUNS = 5;
    private static final long SEED = 42;

    public static void main(String[] args) throws IOException {
        Path resultsDirectory = Path.of("results");
        Files.createDirectories(resultsDirectory);

        Path csvPath = resultsDirectory.resolve("results.csv");

        try (PrintWriter writer = new PrintWriter(Files.newBufferedWriter(csvPath))) {

            writer.println(
                    "algorithm,input,n,run,time_ms," +
                            "recursive_calls,dp_cell_updates," +
                            "comparisons,allocated_cells"
            );
            runEditDistanceExperiments(writer);
            runSearchExperiments(writer);
        }

        System.out.println("\nResults saved to: " + csvPath.toAbsolutePath());
    }

    private static void runEditDistanceExperiments(PrintWriter writer) {
        int[] recursiveSizes = {2, 4, 6, 8, 10};
        int[] dpSizes = {100, 200, 400, 800, 1600};

        System.out.println("Recursive Edit Distance");

        for (int n : recursiveSizes) {

            InputGenerator generator = new InputGenerator(SEED + n);

            String[] identical = generator.identicalPair(n);
            String[] different = generator.differentPair(n);
            String[] random = generator.randomPair(n);

            benchmarkEdit("Recursive", "Identical", n, identical, writer);

            benchmarkEdit("Recursive", "Different", n, different, writer);

            benchmarkEdit("Recursive", "Random", n, random, writer);
        }

        System.out.println("\nDynamic Programming");

        for (int n : dpSizes) {

            InputGenerator generator = new InputGenerator(SEED + n);

            String[] different = generator.differentPair(n);
            String[] random = generator.randomPair(n);

            for (String algorithm : new String[]{
                    "Table", "TwoRows"
            }) {
                benchmarkEdit(algorithm, "Different", n, different, writer);

                benchmarkEdit(algorithm, "Random", n, random, writer);
            }
        }
    }

    private static EditDistance createEditAlgorithm(
            String name, Metrics metrics) {

        return switch (name) {
            case "Recursive" ->
                    new RecursiveEditDistance(metrics);
            case "Table" ->
                    new TableEditDistance(metrics);
            case "TwoRows" ->
                    new TwoRowEditDistance(metrics);
            default ->
                    throw new IllegalArgumentException(
                            "Unknown algorithm: " + name
                    );
        };
    }

    private static void benchmarkEdit(
            String name,
            String inputType,
            int n,
            String[] input,
            PrintWriter writer) {

        for (int w = 0; w < WARMUPS; w++) {

            Metrics metrics = new Metrics();
            EditDistance algorithm = createEditAlgorithm(name, metrics);

            algorithm.distance(input[0], input[1]);
        }

        double[] times = new double[RUNS];
        Integer expectedAnswer = null;

        for (int run = 0; run < RUNS; run++) {

            Metrics metrics = new Metrics();

            EditDistance algorithm = createEditAlgorithm(name, metrics);

            long start = System.nanoTime();

            int answer = algorithm.distance(
                    input[0], input[1]
            );

            long end = System.nanoTime();

            if (expectedAnswer == null) {
                expectedAnswer = answer;
            } else if (answer != expectedAnswer) {
                throw new IllegalStateException(
                        "Inconsistent benchmark result"
                );
            }

            double timeMs = (end - start) / 1_000_000.0;
            times[run] = timeMs;

            writer.printf(Locale.US,
                    "%s,%s,%d,%d,%.6f,%d,%d,%d,%d%n",
                    name,
                    inputType,
                    n,
                    run + 1,
                    timeMs,
                    metrics.getRecursiveCalls(),
                    metrics.getDpCellUpdates(),
                    metrics.getComparisons(),
                    metrics.getAllocatedCells()
            );
        }

        Arrays.sort(times);
        double median = times[RUNS / 2];

        System.out.printf(Locale.US, "%-10s %-10s n=%-5d median=%.6f ms%n",
                name, inputType, n, median);
    }


    private static void runSearchExperiments(PrintWriter writer) {

        int[] sizes = {1000, 4000, 16000, 64000, 256000};
        int patternLength = 20;

        System.out.println("\nString Search Experiments");

        for (int n : sizes) {

            InputGenerator generator =
                    new InputGenerator(SEED + n);

            String[] random =
                    generator.randomSearch(n, patternLength);

            String[] worst =
                    generator.worstCaseSearch(n, patternLength);

            String[] overlapping = {
                    generator.repeatedString(n, 'A'),
                    generator.repeatedString(patternLength, 'A')
            };

            for (String algorithm : new String[]{"Naive", "KMP"}) {

                benchmarkSearch(algorithm, "Random",
                        n, random, writer);

                benchmarkSearch(algorithm, "WorstCase",
                        n, worst, writer);

                benchmarkSearch(algorithm, "Overlapping",
                        n, overlapping, writer);
            }
        }
    }

    private static StringSearch createSearchAlgorithm(
            String name, Metrics metrics) {

        return switch (name) {
            case "Naive" -> new NaiveSearch(metrics);
            case "KMP" -> new KMPSearch(metrics);
            default -> throw new IllegalArgumentException(
                    "Unknown search algorithm: " + name
            );
        };
    }

    private static void benchmarkSearch(
            String name,
            String inputType,
            int n,
            String[] input,
            PrintWriter writer) {

        for (int w = 0; w < WARMUPS; w++) {

            Metrics metrics = new Metrics();
            StringSearch algorithm =
                    createSearchAlgorithm(name, metrics);

            algorithm.search(input[0], input[1]);
        }

        double[] times = new double[RUNS];
        java.util.List<Integer> expected = null;

        for (int run = 0; run < RUNS; run++) {

            Metrics metrics = new Metrics();
            StringSearch algorithm =
                    createSearchAlgorithm(name, metrics);

            long start = System.nanoTime();

            java.util.List<Integer> matches =
                    algorithm.search(input[0], input[1]);

            long end = System.nanoTime();

            if (expected == null) {
                expected = matches;
            } else if (!matches.equals(expected)) {
                throw new IllegalStateException(
                        "Inconsistent search results"
                );
            }

            double timeMs = (end - start) / 1_000_000.0;
            times[run] = timeMs;

            writer.printf(Locale.US,
                    "%s,%s,%d,%d,%.6f,%d,%d,%d,%d%n",
                    name,
                    inputType,
                    n,
                    run + 1,
                    timeMs,
                    metrics.getRecursiveCalls(),
                    metrics.getDpCellUpdates(),
                    metrics.getComparisons(),
                    metrics.getAllocatedCells()
            );
        }

        Arrays.sort(times);
        double median = times[RUNS / 2];

        System.out.printf(Locale.US,
                "%-10s %-12s n=%-7d median=%.6f ms%n",
                name, inputType, n, median);
    }

}
