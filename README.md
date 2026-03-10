# 🔢 Android ViewModel vs remember — Counter Comparison App

A minimal but deliberately structured Android app that **side-by-side compares two state management approaches** in Jetpack Compose: a `ViewModel`-backed counter using `StateFlow`, and a plain Composable-local counter using `remember { mutableIntStateOf() }`. Built as a clear, visual learning reference for understanding *why* ViewModel matters.

---

## 📖 Description

This project answers a common beginner question: *"Why do I need a ViewModel if `remember` already works?"* By rendering both implementations on the same screen, developers can directly observe the behavioral difference — especially during screen rotation or recomposition — and understand where each approach belongs.

---

## 🗂️ Project Structure

```
app/
└── src/main/java/com/example/counter/
    ├── MainActivity.kt                    # Entry point; hosts both Counter composables
    └── viewmodel/
        └── CounterViewModel.kt            # ViewModel with StateFlow for increment/decrement/reset
```

---

## 🧠 Key Concepts Covered

| Concept | Implementation |
|---|---|
| ViewModel state | `MutableStateFlow<Int>` with private write, public `StateFlow` read |
| Observing StateFlow in Compose | `collectAsState()` → `by` delegate |
| Composable-local state | `remember { mutableIntStateOf(0) }` |
| ViewModel scoping | `viewModel()` from `androidx.lifecycle.viewmodel.compose` |
| State survival on rotation | ViewModel state survives; `remember` state does NOT |
| Encapsulation pattern | `_counter` (private mutable) / `counter` (public read-only) |
| Scrollable surface | `verticalScroll(rememberScrollState())` on `Surface` |
| Edge-to-Edge UI | `enableEdgeToEdge()` + `systemBarsPadding()` |

---

## 🖥️ Screen Breakdown

The single screen contains two independent counters stacked vertically, each with **Increment**, **Decrement**, and **Reset** buttons:

### 🟢 Counter 1 — "ViewModel" (survives rotation)
```kotlin
@Composable
fun Counter(viewModel: CounterViewModel = viewModel()) {
    val counter by viewModel.counter.collectAsState()
    // ...
}
```
- State lives in `CounterViewModel`, scoped to the Activity lifecycle
- Survives **screen rotation**, **recomposition**, and **back-stack changes**
- Mutations go through explicitly named functions: `increment()`, `decrement()`, `reset()`

### 🔴 Counter 2 — "Without ViewModel" (resets on rotation)
```kotlin
@Composable
fun Counter2() {
    var counter by remember { mutableIntStateOf(0) }
    // ...
}
```
- State lives inside the Composable itself
- **Lost on screen rotation** or when the Composable leaves the composition
- Suitable only for purely transient, UI-only state (e.g. expanded/collapsed toggles)

---

## ⚙️ ViewModel Deep Dive — `CounterViewModel.kt`

```kotlin
class CounterViewModel : ViewModel() {
    private val _counter = MutableStateFlow(0)  // Only ViewModel can write
    val counter: StateFlow<Int> = _counter       // Composables can only read

    fun increment() { _counter.value++ }
    fun decrement() { _counter.value-- }
    fun reset()     { _counter.value = 0 }
}
```

**Why this pattern matters:**
- **Single source of truth** — UI never directly mutates state
- **Testable** — logic lives outside Composables, easy to unit test
- **Lifecycle-aware** — ViewModel is NOT destroyed on rotation

---

## 🔍 The Core Difference — At a Glance

| | `ViewModel` + `StateFlow` | `remember` + `mutableIntStateOf` |
|---|---|---|
| Survives rotation | ✅ Yes | ❌ No |
| Testable | ✅ Yes | ❌ Hard |
| Separation of concerns | ✅ Yes | ❌ No |
| Best for | Business logic, persistent UI state | Temporary UI-only state |
| Boilerplate | Medium | Minimal |

---

## 🚀 Getting Started

### Prerequisites

- Android Studio Hedgehog or later
- Minimum SDK: 21 (Android 5.0)
- Kotlin 1.9+
- Jetpack Compose BOM

### Dependencies (`build.gradle`)

```kotlin
implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.7.0")
implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
```

### Clone & Run

```bash
git clone https://github.com/your-username/android-viewmodel-vs-remember-counter.git
```

Open in Android Studio, sync Gradle, and run. **Rotate the device** to see the behavioral difference between both counters in action.

---

## 🛠️ Tech Stack

- **Language:** Kotlin
- **UI:** Jetpack Compose + Material 3
- **State Management:** `ViewModel` + `StateFlow` vs `remember` + `mutableIntStateOf`
- **Architecture:** MVVM (demonstrated vs non-MVVM)

---

## 📚 What I Learned

- The **private mutable / public read-only** StateFlow pattern for safe state encapsulation
- How `collectAsState()` bridges Kotlin `Flow` to Compose's recomposition system
- Why `remember` state is **ephemeral** and tied to Composable lifecycle
- When to reach for a `ViewModel` vs when `remember` is perfectly sufficient
- How `viewModel()` from Compose automatically scopes a ViewModel to the nearest `ViewModelStoreOwner`

---

## 📄 License

This project is open source and available under the [MIT License](LICENSE).