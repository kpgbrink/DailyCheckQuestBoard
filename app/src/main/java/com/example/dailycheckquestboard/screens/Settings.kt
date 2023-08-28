package com.example.bottomnavbardemo.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import java.time.LocalTime
import java.time.format.DateTimeFormatter


@Composable
fun SettingsScreen(
    // Settings state
    darkLightModeState: MutableState<DarkLightMode> = remember { mutableStateOf(DarkLightMode.Device) },
    notificationsState: MutableState<Boolean> = remember { mutableStateOf(true) },
    notificationTimeState: MutableState<LocalTime> = remember {
        mutableStateOf(
            LocalTime.of(
                22,
                0
            )
        )
    }
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(MaterialTheme.colorScheme.background),
    ) {
        Text(
            text = "SETTINGS",
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Dark/Light Mode
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Dark/Light Mode",
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            DarkLightModeSwitcher(darkLightModeState)
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Notifications on/off
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Notifications",
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Switch(
                checked = notificationsState.value,
                onCheckedChange = { notificationsState.value = it }
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Daily notifications time
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Notification Time",
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(text = "${notificationTimeState.value.format(DateTimeFormatter.ofPattern("h:mm a"))}")  // Display time as 10:00 PM
            // A Slider or TimePicker could be used to change this time
        }
    }
}

@Composable
fun DarkLightModeSwitcher(darkLightModeState: MutableState<DarkLightMode>) {
    val options = DarkLightMode.values()
    var expanded by remember { mutableStateOf(false) }

    Box(modifier = Modifier.pointerInput(Unit) {
        detectTapGestures { expanded = true }
    }) {
        Text(
            text = darkLightModeState.value.name,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.clickable { expanded = true }
        )
        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(text = option.name) },
                    onClick = {
                        darkLightModeState.value = option
                        expanded = false
                    }
                )
            }
        }
    }
}

enum class DarkLightMode {
    Dark, Light, Device
}


@Composable
@Preview
fun SettingsScreenPreview() {
    SettingsScreen()
}