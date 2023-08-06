package com.example.bottomnavbardemo

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.bottomnavbardemo.screens.CalendarScreen
import com.example.bottomnavbardemo.screens.HomeScreen
import com.example.bottomnavbardemo.screens.SettingsScreen
import com.example.dailycheckquestboard.DailyCheckEvent
import com.example.dailycheckquestboard.DailyCheckState

@Composable
fun BottomNavGraph(
    navController: NavHostController,
    state: DailyCheckState,
    onEvent: (DailyCheckEvent) -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = BottomBarScreen.Home.route
    ) {
        composable(route = BottomBarScreen.Home.route) {
            HomeScreen(state = state, onEvent = onEvent)
        }
        composable(route = BottomBarScreen.Calendar.route) {
            CalendarScreen()
        }
        composable(route = BottomBarScreen.Settings.route) {
            SettingsScreen()
        }
    }
}