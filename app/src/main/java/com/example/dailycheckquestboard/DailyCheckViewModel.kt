package com.example.dailycheckquestboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class DailyCheckViewModel(
    private val dao: DailyCheckDao
) : ViewModel() {
    @OptIn(ExperimentalCoroutinesApi::class)
    private val _dailyChecks = dao.getDailyCheckOrderedByDate()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    private val _state = MutableStateFlow(DailyCheckState())

    val state = combine(_state, _dailyChecks) { state, dailyChecks ->
        state.copy(
            dailyChecks = dailyChecks,
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), DailyCheckState())

    fun onEvent(event: DailyCheckEvent) {
        when (event) {
            is DailyCheckEvent.UpsertWork -> {
                val existingCheck = _dailyChecks.value.find { it.localDate == event.localDate }
                val updatedCheck = existingCheck?.copy(work = event.work)
                    ?: DailyCheck(
                        localDate = event.localDate,
                        work = event.work,
                        physical = false,
                        social = false
                    )

                viewModelScope.launch {
                    dao.upsertDailyCheck(updatedCheck)
                }
            }

            is DailyCheckEvent.UpsertPhysical -> {
                val existingCheck = _dailyChecks.value.find { it.localDate == event.localDate }
                val updatedCheck = existingCheck?.copy(physical = event.social)
                    ?: DailyCheck(
                        localDate = event.localDate,
                        work = false,
                        physical = event.social,
                        social = false
                    )

                viewModelScope.launch {
                    dao.upsertDailyCheck(updatedCheck)
                }
            }

            is DailyCheckEvent.UpsertSocial -> {
                val existingCheck = _dailyChecks.value.find { it.localDate == event.localDate }
                val updatedCheck = existingCheck?.copy(social = event.physical)
                    ?: DailyCheck(
                        localDate = event.localDate,
                        work = false,
                        physical = false,
                        social = event.physical
                    )

                viewModelScope.launch {
                    dao.upsertDailyCheck(updatedCheck)
                }
            }
        }
    }
}
