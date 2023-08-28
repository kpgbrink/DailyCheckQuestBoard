package com.example.dailycheckquestboard.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.dailycheckquestboard.DailyCheck
import com.example.dailycheckquestboard.DailyCheckEvent
import com.example.dailycheckquestboard.ui.theme.LocalExtendedColorScheme
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun EditRecentCheck(
    date: LocalDate,
    dailyCheck: DailyCheck?,
    onEvent: (DailyCheckEvent) -> Unit,
    spacing: Dp,
    height: Dp,
    backgroundColor: Color,
    checkboxPaddingHorizontal: Dp,
    modifier: Modifier
) {
    val extendedColors = LocalExtendedColorScheme.current
    val checkboxWorkColor = extendedColors.checkboxWork
    val checkboxPhysicalColor = extendedColors.checkboxPhysical
    val checkboxSocialColor = extendedColors.checkboxSocial

    val work = dailyCheck?.work ?: false
    val social = dailyCheck?.social ?: false
    val physical = dailyCheck?.physical ?: false

    val label = when {
        date == LocalDate.now() -> "Today"
        date == LocalDate.now().minusDays(1) -> "Yesterday"
        else -> date.dayOfWeek.name // for any other date, simply display the day name
    }

    val formattedDate =
        date.format(DateTimeFormatter.ofPattern("EEEE d")) + getDayOfMonthSuffix(date.dayOfMonth)

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(backgroundColor),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .height(height)
        )

        Text(
            text = formattedDate,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .height(height)
        )

        Spacer(modifier = Modifier.height(spacing / 2))

        CheckboxWithLabel(
            label = "Work",
            checked = work,
            onCheckedChange = { checked ->
                onEvent(DailyCheckEvent.UpsertWork(date, checked))
            },
            paddingHorizontal = checkboxPaddingHorizontal,
            checkboxColor = checkboxWorkColor
        )

        Spacer(modifier = Modifier.height(spacing))

        CheckboxWithLabel(
            label = "Physical",
            checked = physical,
            onCheckedChange = { checked ->
                onEvent(DailyCheckEvent.UpsertPhysical(date, checked))
            },
            paddingHorizontal = checkboxPaddingHorizontal,
            checkboxColor = checkboxPhysicalColor
        )

        Spacer(modifier = Modifier.height(spacing))

        CheckboxWithLabel(
            label = "Social",
            checked = social,
            onCheckedChange = { checked ->
                onEvent(DailyCheckEvent.UpsertSocial(date, checked))
            },
            paddingHorizontal = checkboxPaddingHorizontal,
            checkboxColor = checkboxSocialColor
        )
    }
}

@Composable
fun CheckboxWithLabel(
    label: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    checkboxColor: Color,
    paddingHorizontal: Dp,
    modifier: Modifier = Modifier
) {
    var internalCheckedState by remember { mutableStateOf(checked) }
    var test = 10.dp
    // Combined clickable area for both the Text and the Checkbox
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                internalCheckedState = !internalCheckedState
                onCheckedChange(internalCheckedState)
            },
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = paddingHorizontal, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = label,
                fontWeight = FontWeight.Normal,
                color = checkboxColor,
                modifier = Modifier.padding(end = 16.dp)  // Adjust padding for proximity
            )

            Checkbox(
                checked = internalCheckedState,
                onCheckedChange = null,  // Handler is null because we've handled the change in the Row clickable
                colors = CheckboxDefaults.colors(checkedColor = checkboxColor),
                modifier = Modifier.heightIn(min = 24.dp)  // Adjust as needed
            )
        }
    }
}


// Helper function to get the suffix for the day of the month (e.g., 1st, 2nd, 3rd, ...)
fun getDayOfMonthSuffix(n: Int): String {
    if (n in 11..13) {
        return "th"
    }
    return when (n % 10) {
        1 -> "st"
        2 -> "nd"
        3 -> "rd"
        else -> "th"
    }
}
