import pandas as pd
import matplotlib.pyplot as plt
from pathlib import Path

RESULTS = Path("results")
PLOTS = RESULTS / "plots"
PLOTS.mkdir(parents=True, exist_ok=True)

df = pd.read_csv(RESULTS / "results.csv")

summary = (
    df.groupby(["algorithm", "input", "n", "m"], as_index=False)
    .agg(
        time_ms=("time_ms", "median"),
        recursive_calls=("recursive_calls", "median"),
        dp_cell_updates=("dp_cell_updates", "median"),
        comparisons=("comparisons", "median"),
        allocated_cells=("allocated_cells", "median")
    )
)

def finish(title, xlabel, ylabel, filename):
    plt.title(title)
    plt.xlabel(xlabel)
    plt.ylabel(ylabel)
    plt.grid(True, alpha=0.3)
    plt.legend()
    plt.tight_layout()
    plt.savefig(PLOTS / filename, dpi=200)
    plt.close()

data = summary[summary.algorithm == "Recursive"]

plt.figure()
for kind, group in data.groupby("input"):
    group = group.sort_values("n")
    plt.plot(group.n, group.recursive_calls,
             marker="o", label=kind)

plt.yscale("log")
finish("Recursive Edit Distance: Call Growth",
       "String length n",
       "Recursive calls (log scale)",
       "01_recursive_calls.png")

data = summary[
    summary.algorithm.isin(["Table", "TwoRows"])
    & (summary.input == "Different")
    ]

plt.figure()
for algorithm, group in data.groupby("algorithm"):
    group = group.sort_values("n")
    plt.plot(group.n, group.allocated_cells,
             marker="o", label=algorithm)
    plt.yscale("log")

finish("DP Memory Comparison",
       "String length n",
       "Allocated integer cells (log scale)",
       "02_dp_memory.png")

plt.figure()
for algorithm, group in data.groupby("algorithm"):
    group = group.sort_values("n")
    plt.plot(group.n, group.time_ms,
             marker="o", label=algorithm)

finish("DP Runtime Comparison",
       "String length n",
       "Median execution time (ms)",
       "03_dp_runtime.png")

data = summary[
    summary.algorithm.isin(["Naive", "KMP"])
    & (summary.input == "WorstCase")
    ]

plt.figure()
for algorithm, group in data.groupby("algorithm"):
    group = group.sort_values("n")
    plt.plot(group.n, group.comparisons,
             marker="o", label=algorithm)

finish("Worst-Case String Search",
       "Text length n",
       "Character comparisons",
       "04_search_worst_case.png")

data = summary[
    summary.input.str.startswith("PatternLength_")
]

plt.figure()
for algorithm, group in data.groupby("algorithm"):
    group = group.sort_values("m")
    plt.plot(group.m, group.comparisons,
             marker="o", label=algorithm)

finish("Effect of Pattern Length",
       "Pattern length m (characters)",
       "Character comparisons",
       "05_pattern_length.png")

print("All five plots generated successfully!")
