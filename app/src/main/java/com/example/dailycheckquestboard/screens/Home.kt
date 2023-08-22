package com.example.bottomnavbardemo.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
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
    val firstDayOfLastWeek = firstDayOfWeek.minusDays(7)

    val spacing: Dp = 15.dp
    val paddingCol: Dp = 1.dp
    val height: Dp = 20.dp
    val shadowGrey = Color(0xFFD3D3D3)  // You can adjust the color as needed


    Scaffold { paddingValues ->
        Column(modifier = Modifier.fillMaxSize()) {  // Added this


            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(paddingCol)
                    .padding(paddingValues)
                    .background(shadowGrey),  // Add the background modifier
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,

            ) {
                LabelsSide(spacing, paddingCol, height, Modifier.weight(1f))
                for (i in 0..6) {
                    val date = firstDayOfLastWeek.plusDays(i.toLong())
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
            Divider(modifier = Modifier)  // Adjust the padding as needed

            // Row showing the days of the week
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(paddingCol)
                    .padding(paddingValues),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Empty space for alignment purposes (assuming LabelsSide doesn't display a day)
                Box(modifier = Modifier.weight(1f))

                // Loop through the days of the week starting from Monday
                for (i in 1..7) {
                    val dayOfWeek = DayOfWeek.of(i)
                    Text(
                        text = dayOfWeek.name.take(3),  // Displaying the first three letters of each day
                        textAlign = TextAlign.Center,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
            Divider(modifier = Modifier.padding(vertical = spacing / 9))  // Adjust the padding as needed
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
            Divider(modifier = Modifier.padding(vertical = spacing / 9))  // Adjust the padding as needed

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