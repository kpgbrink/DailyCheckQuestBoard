package com.example.dailycheckquestboard.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.dailycheckquestboard.DailyCheck
import com.example.dailycheckquestboard.DailyCheckEvent
import com.example.dailycheckquestboard.ui.theme.DailyCheckQuestBoardTheme
import com.example.dailycheckquestboard.ui.theme.LocalExtendedColorScheme
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun EditDailyCheck(
    date: LocalDate,
    dailyCheck: DailyCheck?,
    onEvent: (DailyCheckEvent) -> Unit,
    spacing: Dp,
    paddingCol: Dp,
    height: Dp,
    backgroundColor: Color,
    modifier: Modifier
) {
    val extendedColors = LocalExtendedColorScheme.current
    val checkboxWorkColor = extendedColors.checkboxWork
    val checkboxPhysicalColor = extendedColors.checkboxPhysical
    val checkboxSocialColor = extendedColors.checkboxSocial

    val work = dailyCheck?.work ?: false
    val social = dailyCheck?.social ?: false
    val physical = dailyCheck?.physical ?: false

    val formattedDate = date.format(DateTimeFormatter.ofPattern("dd"))

    Column(
        modifier = modifier
            .fillMaxWidth()
           // .padding(paddingCol, vertical = 0.dp)
            .background(backgroundColor),  // Use the passed background color
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = formattedDate,
            style = TextStyle(fontWeight = FontWeight.Bold),
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .height(height)
        )

        Spacer(modifier = Modifier.height(spacing / 2))

        Checkbox(
            checked = work,
            onCheckedChange = { checked ->
                onEvent(DailyCheckEvent.UpsertWork(date, checked))
            },
            colors = CheckboxDefaults.colors(checkedColor = checkboxWorkColor),
            modifier = Modifier
                .padding(0.dp)
                .height(height)

        )

        Spacer(modifier = Modifier.height(spacing))

        Checkbox(
            checked = physical,
            onCheckedChange = { checked ->
                onEvent(DailyCheckEvent.UpsertPhysical(date, checked))
            },
            colors = CheckboxDefaults.colors(checkedColor = checkboxPhysicalColor),
            modifier = Modifier
                .padding(0.dp)
                .height(height)
        )

        Spacer(modifier = Modifier.height(spacing))

        Checkbox(
            checked = social,
            onCheckedChange = { checked ->
                onEvent(DailyCheckEvent.UpsertSocial(date, checked))
            },
            colors = CheckboxDefaults.colors(checkedColor = checkboxSocialColor),
            modifier = Modifier
                .padding(0.dp, bottom = 5.dp)
                .height(height)
        )
    }
}


@Composable
@Preview(showBackground = true)
fun EditDailyCheckPreview() {
    val dummyDailyCheck = DailyCheck(
        localDate = LocalDate.now(),
        work = true,
        social = false,
        physical = true
    )

    val dummyOnEvent: (DailyCheckEvent) -> Unit = {}
    DailyCheckQuestBoardTheme {
        EditDailyCheck(
            date = LocalDate.now(),
            dailyCheck = dummyDailyCheck,
            onEvent = dummyOnEvent,
            spacing = 16.dp,
            paddingCol = 8.dp,
            height = 1.dp,
            backgroundColor = Color.White,
            modifier = Modifier
        )
    }
}