package com.example.bottomnavbardemo.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.example.dailycheckquestboard.DailyCheck
import com.example.dailycheckquestboard.DailyCheckEvent
import com.example.dailycheckquestboard.DailyCheckState
import com.example.dailycheckquestboard.screens.home.EditDailyCheck
import com.example.dailycheckquestboard.screens.home.LabelsSide
import com.example.dailycheckquestboard.ui.theme.DailyCheckQuestBoardTheme
import kotlinx.coroutines.delay
import java.time.DayOfWeek
import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

fun timeUntilNextDay(): Duration {
    val now = LocalTime.now()
    val endOfDay = LocalTime.of(23, 59, 59, 999_999_999)
    return Duration.between(now, endOfDay)
}

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
    val firstDayOfLastWeek = firstDayOfWeek.minusDays(7)

    val spacing: Dp = 15.dp
    val paddingCol: Dp = 1.dp
    val height: Dp = 20.dp

    val lifecycleOwner = LocalLifecycleOwner.current
    val forceUpdate = rememberUpdatedState(Unit)

    LaunchedEffect(lifecycleOwner) {
        lifecycleOwner.lifecycle.addObserver(object : LifecycleObserver {
            fun onResume() {
                forceUpdate.value // triggers recomposition
            }
        })
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 0.dp)
    ) {  // Added this

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = paddingCol, vertical = 0.dp)
                .background(MaterialTheme.colorScheme.inverseSurface.copy(alpha = .35f)),  // Apply the background modifier
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            LabelsSide(spacing, paddingCol, height, Modifier.weight(1f))
            for (i in 0..6) {
                val date = firstDayOfLastWeek.plusDays(i.toLong())
                val dailyCheck = state.dailyChecks.find { it.localDate == date }
                // Calculate background color
                val columnBackground = if (i % 2 == 0)
                    MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.3f)
                else
                    MaterialTheme.colorScheme.background.copy(alpha = 0.3f)


                EditDailyCheck(
                    date = date,
                    dailyCheck = dailyCheck,
                    onEvent = onEvent,
                    spacing = spacing,
                    paddingCol = paddingCol,
                    height = height,
                    backgroundColor = columnBackground,
                    modifier = Modifier.weight(1f)
                )
            }
        }
        Divider()  // No padding for the divider

        // Row showing the days of the week
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(paddingCol, bottom = 0.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Empty space for alignment purposes (assuming LabelsSide doesn't display a day)
            Box(modifier = Modifier.weight(1f))

            // Loop through the days of the week starting from Monday
            for (i in 1..7) {
                val dayOfWeek = DayOfWeek.of(i)
                val currentDayDate =
                    firstDayOfWeek.plusDays((i - 1).toLong())  // derive the date for the current dayOfWeek
                val today = LocalDate.now()

                // Calculate background color
                val columnBackground = when {
                    currentDayDate == today -> MaterialTheme.colorScheme.primary
                    i % 2 == 1 -> MaterialTheme.colorScheme.secondaryContainer
                    else -> MaterialTheme.colorScheme.surface
                }

                Text(
                    text = dayOfWeek.name.take(3),  // Displaying the first three letters of each day
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .weight(1f)
                        .background(columnBackground)
                )
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(paddingCol, top = 0.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            LabelsSide(spacing, paddingCol, height, Modifier.weight(1f))
            for (i in 0..6) {
                val date = firstDayOfWeek.plusDays(i.toLong())
                val dailyCheck = state.dailyChecks.find { it.localDate == date }
                // Get the current date
                val today = LocalDate.now()
                // Calculate background color
                val columnBackground = when {
                    date == today -> MaterialTheme.colorScheme.primary  // Change this to the desired color for "today"
                    i % 2 == 0 -> MaterialTheme.colorScheme.secondaryContainer
                    else -> MaterialTheme.colorScheme.surface
                }
                // If it is today make it a certain background color

                EditDailyCheck(
                    date = date,
                    dailyCheck = dailyCheck,
                    onEvent = onEvent,
                    spacing = spacing,
                    paddingCol = paddingCol,
                    height = height,
                    backgroundColor = columnBackground,
                    modifier = Modifier.weight(1f)
                )
            }
        }
        Divider(modifier = Modifier.padding(vertical = spacing / 9))  // Adjust the padding as needed
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
    DailyCheckQuestBoardTheme  {
        HomeScreen(state, {})
    }
}