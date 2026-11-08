# DAA Assignment 4 — Dynamic Programming and String Algorithms

## Overview

This project implements and experimentally evaluates five algorithms for edit distance and string searching, based on a City Search & Spell-Check scenario.

The goal is to investigate algorithm correctness, time complexity, memory usage, and performance under different input conditions.

## Implemented Algorithms

### Edit Distance

- **Recursive Edit Distance:** Direct recursion without memoization.
- **Table Edit Distance:** Bottom-up dynamic programming using a full 2D table.
- **Two-Row Edit Distance:** Space-optimized dynamic programming using two arrays.

### String Search

- **Naive Search:** Checks every possible starting position.
- **Knuth–Morris–Pratt (KMP):** Uses the Longest Proper Prefix Suffix (LPS) array to avoid unnecessary comparisons.

Both search algorithms return all matching positions, including overlapping matches.

## Technologies

- Java 17
- Maven
- JUnit 5
- Python 3
- Pandas and Matplotlib for visualizations

## Build and Test

From the project root, run:

```bash
mvn clean test
```

The project includes 45 JUnit tests covering reference values, edge cases, comparison counters, input generation, and randomized correctness checks.

## Running the Experiments

Open `src/main/java/org/example/ExperimentRunner.java` in IntelliJ IDEA and run its `main()` method.

The experiment runner performs warm-up executions, records five measured runs per configuration, and saves the results to:

`results/results.csv`

Inputs are generated reproducibly using a fixed random seed of 42 (with deterministic size-dependent seeds where applicable).

Execution time is measured using `System.nanoTime()`. The program also collects algorithm-specific operation counters.

## Generating Plots

Install the required Python packages:

```bash
python -m pip install pandas matplotlib
```

Generate plots from the CSV:

```bash
python plot_results.py
```

The generated charts are saved in `results/plots/`.

## Experimental Design

Edit Distance experiments compare identical, random, and completely different strings.

String Search experiments compare random, repetitive, and adversarial inputs. Additional experiments vary pattern length while keeping text length fixed.

The collected metrics include recursive calls, DP cell updates, allocated integer cells, character comparisons, and execution time.

## Results

The project includes five visualizations:

1. Recursive Edit Distance call growth.
2. Full-table versus two-row DP memory consumption.
3. Full-table versus two-row DP runtime.
4. Naive versus KMP worst-case comparisons.
5. Effect of pattern length on string-search comparisons.

The complete analysis, predictions, observations, and conclusions are documented in [REPORT.md](REPORT.md).

## Algorithm Complexity

| Algorithm | Time Complexity | Auxiliary Space |
|---|---|---|
| Recursive Edit Distance | Exponential worst case | O(n + m) |
| Table Edit Distance | O(n × m) | O(n × m) |
| Two-Row Edit Distance | O(n × m) | O(min(n, m)) |
| Naive Search | O(n × m) worst case | O(1) |
| KMP Search | O(n + m) | O(m) |

For search algorithms, the space bounds exclude the returned list of matching indices.

## AI-Use Acknowledgement

ChatGPT was used as a learning and development assistant for explaining algorithm concepts, suggesting Java implementations and JUnit tests, structuring reproducible benchmarks and drafting documentation.

The project was built and executed locally, with tests and experiments used to verify its behavior. The experimental observations and conclusions are based on the generated results.

## Submission

The final version is maintained on the `main` branch and tagged `v1.0`.
