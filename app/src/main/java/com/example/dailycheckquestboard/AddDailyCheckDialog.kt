package com.example.dailycheckquestboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import java.util.Date

@Composable
fun AddDailyCheckDialog(
    state: DailyCheckState,
    onEvent: (DailyCheckEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        onDismissRequest = {

        },
        title = { Text(text = "Add Daily Check") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Row(
                    modifier = Modifier
                ) {
                    Checkbox(checked = state.work, onCheckedChange = {
                        onEvent(DailyCheckEvent.SetWork(it))
                    })
                    Text(text = "Work")
                }
                Row(
                    modifier = Modifier
                ) {
                    Checkbox(checked = state.physical, onCheckedChange = {
                        onEvent(DailyCheckEvent.SetPhysical(it))
                    })
                    Text(text = "Physical")
                }
                Row(
                    modifier = Modifier
                ) {
                    Checkbox(checked = state.social, onCheckedChange = {
                        onEvent(DailyCheckEvent.SetSocial(it))
                    })
                    Text(text = "Social")
                }
            }
        },
        confirmButton = {
            Box(
                modifier = Modifier.fillMaxWidth(),
            ) {
                Button(onClick = {
                    onEvent(DailyCheckEvent.SaveDailyCheck)
                }) {
                    Text(text = "Save")
                }
            }
        }
    )
}

@Preview
@Composable

fun AddDailyCheckDialogPreview() {
    val state = DailyCheckState(
        dailyChecks = listOf(
            DailyCheck(
                id = 1,
                date = Date(),
                work = true,
                social = false,
                physical = true
            ),
            DailyCheck(
                id = 2,
                date = Date(),
                work = false,
                social = true,
                physical = false
            )
        ),
        physical = true,
        social = true,
        work = false,
        isAddingDailyCheck = false,
        sortType = SortType.DATE
    )
    AddDailyCheckDialog(state = state, onEvent = {})
}