package com.example.dailycheckquestboard.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.dailycheckquestboard.DailyCheckEvent
import com.example.dailycheckquestboard.DailyCheckState
import java.time.LocalDate

@Composable
fun DisplayTodayAndYesterday(
    state: DailyCheckState,
    currentDate: LocalDate,
    onEvent: (DailyCheckEvent) -> Unit,
    spacing: Dp,
    paddingCol: Dp,
    height: Dp
) {
    val todayCheck = state.dailyChecks.find { it.localDate == currentDate }
    val yesterdayCheck = state.dailyChecks.find { it.localDate == currentDate.minusDays(1) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(paddingCol, top = spacing, bottom = 40.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Display for 'Yesterday'
        EditRecentCheck(
            date = currentDate.minusDays(1),
            dailyCheck = yesterdayCheck,
            onEvent = onEvent,
            spacing = spacing,
            height = height,
            backgroundColor = MaterialTheme.colorScheme.surface,
            checkboxPaddingHorizontal = 10.dp,
            modifier = Modifier.weight(1f) // 1/3 of available width
        )

        // Display for 'Today'
        EditRecentCheck(
            date = currentDate,
            dailyCheck = todayCheck,
            onEvent = onEvent,
            spacing = spacing,
            height = height,
            backgroundColor = MaterialTheme.colorScheme.primary,  // Assuming you want 'today' in primary color
            checkboxPaddingHorizontal = 50.dp,
            modifier = Modifier.weight(2f) // 2/3 of available width
        )
    }
}