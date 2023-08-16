package com.example.bottomnavbardemo.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.dailycheckquestboard.DailyCheck
import com.example.dailycheckquestboard.DailyCheckEvent
import com.example.dailycheckquestboard.DailyCheckState
import com.example.dailycheckquestboard.screens.home.EditDailyCheck
import com.example.dailycheckquestboard.screens.home.LabelsSide
import java.time.DayOfWeek
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    state: DailyCheckState,
    onEvent: (DailyCheckEvent) -> Unit,
) {
    // Get the current date
    val currentDate = LocalDate.now()
    // Get the first day of the current week
    val firstDayOfWeek = currentDate.with(DayOfWeek.MONDAY)

    val spacing: Dp = .1.dp
    val paddingCol: Dp = .01.dp
    val height: Dp = 20.dp

    Scaffold { paddingValues ->
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(paddingCol)
                .padding(paddingValues),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            LabelsSide(spacing, paddingCol, height, Modifier.weight(1f))

            for (i in 0..6) {
                val date = firstDayOfWeek.plusDays(i.toLong())
                val dailyCheck = state.dailyChecks.find { it.localDate == date }

                EditDailyCheck(
                    date = date,
                    dailyCheck = dailyCheck,
                    onEvent = onEvent,
                    spacing = spacing,
                    paddingCol = paddingCol,
                    height = height,
                    modifier = Modifier.weight(1f)
                )
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
                localDate = LocalDate.now(),
                work = true,
                social = false,
                physical = true
            ),
            DailyCheck(
                id = 2,
                localDate = LocalDate.now(),
                work = false,
                social = true,
                physical = false
            )
        ),
    )
    HomeScreen(state, {})
}