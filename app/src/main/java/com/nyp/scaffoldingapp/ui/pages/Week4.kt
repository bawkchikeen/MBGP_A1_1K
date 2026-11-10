package com.nyp.scaffoldingapp.ui.pages

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel

/**
 * Week 4 practice — the Weekly Planner from Week 3, made to work.
 *
 *  ┌────────────────────────────────────────────┐
 *  │   ┌────────┬────────┐                      │  stats: counted from the list
 *  │   │   1    │   2    │                      │
 *  │   │  done  │ active │                      │
 *  │   └────────┴────────┘                      │
 *  │                                            │
 *  │  Today                                     │
 *  │  ────────────────────────────────────────  │
 *  │  ○  Submit A1 draft            4 Dec        │  tap a row: ○ becomes ●
 *  │  ●  Read the rubric            2 Dec        │
 *  │  ○  Set up the emulator        1 Dec        │
 *  │                                            │
 *  │  [             Add task             ]      │
 *  └────────────────────────────────────────────┘
 *
 * Part A (TODO 1–5): hoist the list, so the rows and the button can change it.
 * Part B (TODO 6–7): move the list into a ViewModel, so it survives a tab change.
 * Part C (TODO 8–9): two animations that show the user what just happened.
 *
 * Do the TODOs in order. Run it on the emulator after each one.
 */

/** One task. A data class, so copy() can make a changed version of it. */
data class Task(val id: Int, val title: String, val due: String, val done: Boolean = false)

private val sampleTasks = listOf(
    Task(1, "Submit A1 draft", "4 Dec"),
    Task(2, "Read the rubric", "2 Dec", done = true),
    Task(3, "Set up the emulator", "1 Dec")
)

// ---- Part B -----------------------------------------------------------------
// TODO 6: move the list in here, so it outlives the screen.
//         val tasks = sampleTasks.toMutableStateList()
//         Then add the two functions the screen will call:
//         fun toggle(task: Task)    fun add(title: String)
class PlannerViewModel : ViewModel() {

}

@Composable
fun PlannerScreen(modifier: Modifier = Modifier) {

    // TODO 7: take the ViewModel as a parameter instead, and delete TODO 1's line:
    //         fun PlannerScreen(vm: PlannerViewModel = viewModel(), modifier: Modifier = Modifier)
    //         Then call vm.toggle(task) and vm.add(...) in place of changing the list here.
    //         Switch tabs and come back. The list is still there.

    // ---- Part A -------------------------------------------------------------
    // TODO 1: this list can never change. Hold it as state instead:
    //         val tasks = remember { sampleTasks.toMutableStateList() }
    val tasks = sampleTasks

    Column(modifier
        .fillMaxSize()
        .padding(16.dp)) {

        // TODO 3: these numbers are made up. Count them from the list:
        //         tasks.count { it.done }    tasks.count { !it.done }
        //         The stats and the list now read the same state. That is why
        //         the state lives here, above both of them.
        Row(Modifier.fillMaxWidth()) {
            PlannerStat("8", "done", Modifier.weight(1f))
            PlannerStat("4", "active", Modifier.weight(1f))
        }

        Spacer(Modifier.height(24.dp))

        Text("Today", style = MaterialTheme.typography.titleMedium)
        HorizontalDivider(Modifier.padding(vertical = 8.dp))

        LazyColumn(Modifier.weight(1f)) {
            items(tasks, key = { it.id }) { task ->
                // TODO 2b: tell the row what a tap should do:
                //          onToggle = { tasks[tasks.indexOf(task)] = task.copy(done = !task.done) }
                //
                // TODO 8: make rows slide into place instead of jumping. Pass the row
                //         modifier = Modifier.animateItem()
                //         Tap "Add task". Then delete key = { it.id } above and try again.
                //         What changed, and why?
                PlannerTaskRow(task)
            }
        }

        // TODO 4: add a task when the button is tapped:
        //         onClick = { tasks.add(Task(tasks.size + 1, "New task", "5 Dec")) }
        //         Each id must be different: the list uses it as the key.
        Button(onClick = { }, modifier = Modifier.fillMaxWidth()) { Text("Add task") }

        // TODO 5: run it. Tick a task and add one. Rotate. Then switch to
        //         another tab and come back. What happened to your changes, and why?
    }
}

/** One statistic: a big number over a small label. */
@Composable
private fun PlannerStat(value: String, label: String, modifier: Modifier = Modifier) {
    Column(modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Text(value, style = MaterialTheme.typography.headlineSmall)
        Text(
            label,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

/** One row: a dot that shows whether it is done, the title, and the date. */
// TODO 2a: add a parameter  onToggle: () -> Unit  and call it when the row is
//          tapped: modifier.clickable { onToggle() }
//          The row does not change the task. It only reports the tap.
@Composable
private fun PlannerTaskRow(task: Task, modifier: Modifier = Modifier) {

    // TODO 9: fade the dot's colour when a task is ticked, instead of snapping:
    //         val dotColour by animateColorAsState(
    //             if (task.done) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline
    //         )
    //         Then use color = dotColour on the dot below.

    Row(
        modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            if (task.done) "●" else "○",
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(Modifier.width(12.dp))
        Text(task.title, Modifier.weight(1f), maxLines = 1, overflow = TextOverflow.Ellipsis)
        Text(
            task.due,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PlannerScreenPreview() {
    MaterialTheme { PlannerScreen() }
}
