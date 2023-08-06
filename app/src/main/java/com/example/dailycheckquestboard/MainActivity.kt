package com.example.dailycheckquestboard

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.example.dailycheckquestboard.ui.theme.DailyCheckQuestBoardTheme

class MainActivity : ComponentActivity() {

    // TODO learn dependency injection for this
    private val db by lazy {
        Room.databaseBuilder(
            applicationContext,
            DailyCheckDatabase::class.java,
            "dailyChecks.db"
        ).build()
    }

    private val viewModel by viewModels<DailyCheckViewModel>(
        factoryProducer = {
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return DailyCheckViewModel(db.dao) as T
                }
            }
        }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val state by viewModel.state.collectAsState()
            DailyCheckQuestBoardTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen(state = state, onEvent = viewModel::onEvent)
                }
            }
        }
    }
}
