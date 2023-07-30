package com.example.dailycheckquestboard

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.bottomnavbardemo.BottomBarScreen
import com.example.bottomnavbardemo.BottomNavGraph
import com.example.dailycheckquestboard.ui.theme.DailyCheckQuestBoardTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val navController = rememberNavController()
    //Instead of backStackEntry
    var selectedItem by remember { mutableStateOf(0) }
    val screens = listOf(
        BottomBarScreen.Home,
        BottomBarScreen.Calendar,
        BottomBarScreen.Settings,
    )
    Scaffold(
        bottomBar = {
            androidx.compose.material3.NavigationBar {
                //Replace bottomNavItems.forEach with,
                screens.forEachIndexed { index, item ->
                    NavigationBarItem(
                        selected = selectedItem == index,
                        onClick = {
                            selectedItem = index
                            navController.navigate(item.route)
                        },
                        icon = {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = "${item.title} Icon",
                            )
                        }
                    )
                }
            }
        }
    ) { contentPadding ->
        Box(modifier = Modifier.padding(contentPadding)) {
            BottomNavGraph(navController = navController)
        }
    }
}


@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    DailyCheckQuestBoardTheme {
        MainScreen()
    }
}







