# Processor Execution Simulator (Java)

A **discrete-event CPU scheduler** that assigns incoming tasks to a pool of processors and produces a **cycle-by-cycle** timeline of events (task creation, dispatch, completion). Inputs include **number of processors**, **total clock cycles**, and a **task file** with `(creationTime, executionTime, priority)` for each task. Tasks are scheduled by **priority** with an **execution-time tiebreaker**. Built in **Java**.

> Repo includes a nested project folder and a technical report PDF (`CPU Simulator Report.pdf`).

---

## ✨ Features

* **Multiple processors** (parallel dispatch per cycle).
* **Priority-first scheduling** (shorter execution time breaks ties).
* **Cycle-accurate log**: creation, assignment, completion on each tick.
* **Deterministic simulation** (same input ⇒ same output).
* Simple, standalone **Java** project (no external runtime dependencies).

---

## 🗂 Project Structure

```
Processor-Execution-Simulator/
├─ Processor_Execution_Simulation/
│  └─ Processor-Execution-Simulation-main/   # Java source project
├─ CPU Simulator Report.pdf                  # Design/overview
└─ README.md
```

---

## 🧱 Prerequisites

* **JDK 17+** (`java -version`)
* Any IDE (IntelliJ/Eclipse/VS Code) or plain CLI

---

## ▶️ Build & Run

> Open **`Processor_Execution_Simulation/Processor-Execution-Simulation-main`** as the project in your IDE and run the main class.
> For CLI, compile sources and run with program arguments as shown below.

### CLI (generic)

```bash
# 1) Compile (adjust src path to match your project layout)
javac -d out $(find Processor_Execution_Simulation/Processor-Execution-Simulation-main -name "*.java")

# 2) Run (replace 'your.package.Main' with the actual main class)
java -cp out your.package.Main \
  --processors 4 \
  --cycles 200 \
  --tasks ./tasks.txt
```

**Program arguments (expected):**

* `--processors <int>`: number of processor “cores”
* `--cycles <int>`: total simulation ticks
* `--tasks <path>`: task definition file

> If your main class uses positional args, pass them in order:
> `<processors> <cycles> <tasksFile>`

---

## 📄 Task File Format

Plain text; **one task per line**:

```
<creationTime> <executionTime> <priority>
```

**Example (`tasks.txt`)**

```
0 5 2
1 2 3
3 4 1
5 3 2
```

* `creationTime` — cycle when the task appears
* `executionTime` — required CPU cycles
* `priority` — higher means more important

---

## 🖨 Output (example)

Cycle-by-cycle log (format may vary by implementation):

```
[Cycle 0] CREATED  T1(ct=0, et=5, pr=2)
[Cycle 0] ASSIGN   T1 -> P0
[Cycle 1] CREATED  T2(ct=1, et=2, pr=3)
[Cycle 1] ASSIGN   T2 -> P1
[Cycle 3] COMPLETE T2 on P1
[Cycle 5] COMPLETE T1 on P0
...
```

---

## ⚙️ Scheduling Logic (at a glance)

```mermaid
flowchart TD
  A[Start cycle c] --> B[Enqueue tasks with creationTime == c]
  B --> C{Idle processors?}
  C -- No --> D[Tick running tasks (et--)]
  C -- Yes --> E[Pick highest-priority task\n(ties: shortest execution time)]
  E --> F[Dispatch to an idle processor]
  F --> D
  D --> G{Any task finished?}
  G -- Yes --> H[Mark COMPLETE & free processor]
  G -- No --> I[Next cycle c+1]
  H --> I
```

* **Ready queue** ordered by **priority desc**, then **execution time asc**.
* Each cycle: admit new tasks → dispatch to any **idle** processors → decrement remaining times → record completions.

---

## ✅ Testing Ideas

* **Determinism**: same input → identical log
* **Tie-breakers**: equal priority → shorter `executionTime` first
* **Throughput**: increasing `--processors` reduces total completion time
* **Edge cases**: zero tasks, tasks arriving after `--cycles`, very long `executionTime`

---

## 🛠 Troubleshooting

* **No output** → verify argument mapping (named vs positional)
* **Main class not found** → confirm the package name and class in the `java -cp` command
* **Tasks never scheduled** → ensure `creationTime < --cycles` and at least one processor is available

---

## 🗺 Roadmap

* Additional policies: **SJF**, **Round-Robin**, **Aging** (to avoid starvation)
* CSV task loader with headers
* Export log to JSON/CSV; basic charts
* Unit tests for queue ordering and multi-processor dispatch

