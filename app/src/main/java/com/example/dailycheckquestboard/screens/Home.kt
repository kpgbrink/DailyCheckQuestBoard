package com.example.bottomnavbardemo.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dailycheckquestboard.AddDailyCheckDialog
import com.example.dailycheckquestboard.DailyCheck
import com.example.dailycheckquestboard.DailyCheckEvent
import com.example.dailycheckquestboard.DailyCheckState
import com.example.dailycheckquestboard.SortType
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    state: DailyCheckState,
    onEvent: (DailyCheckEvent) -> Unit
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                onEvent(DailyCheckEvent.ShowDialog)
            }) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Daily Check"
                )
            }

        }
    ) { padding ->
        if (state.isAddingDailyCheck) {
            AddDailyCheckDialog(state = state, onEvent = onEvent)
        }
        LazyColumn(
            contentPadding = padding,
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    verticalAlignment = CenterVertically
                ) {
                    SortType.values().forEach { sortType ->
                        Row(
                            modifier = Modifier
                                .clickable {
                                    onEvent(DailyCheckEvent.SortDailyChecks(sortType))
                                },
                            verticalAlignment = CenterVertically
                        ) {
                            RadioButton(selected = state.sortType == sortType, onClick = {
                                onEvent(DailyCheckEvent.SortDailyChecks(sortType))
                            })
                            Text(text = sortType.name)
                        }
                    }
                }
            }
            items(state.dailyChecks) { dailyCheck ->
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = "${dailyCheck.date} Work:${dailyCheck.work} Physical:${dailyCheck.physical} Social:${dailyCheck.social}",
                            fontSize = 20.sp
                        )
                        Text(text = dailyCheck.id.toString(), fontSize = 12.sp)
                    }
                    IconButton(onClick = {
                        onEvent(DailyCheckEvent.DeleteDailyCheck(dailyCheck))
                    }) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete DailyCheck"
                        )
                    }
                }
            }
        }
    }
}

@Composable
@Preview
fun HomeScreenPreview() {
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
    HomeScreen(state, {})
}