package com.example.dailycheckquestboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Date

class DailyCheckViewModel(
    private val dao: DailyCheckDao
) : ViewModel() {
    private val _sortType = MutableStateFlow(SortType.DATE)
    private val _dailyChecks = _sortType
        .flatMapLatest { sortType ->
            when (sortType) {
                SortType.DATE -> dao.getDailyCheckOrderedByDate()
            }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    private val _state = MutableStateFlow(DailyCheckState())

    val state = combine(_state, _sortType, _dailyChecks) { state, sortType, dailyChecks ->
        state.copy(
            dailyChecks = dailyChecks,
            sortType = sortType
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), DailyCheckState())

    fun onEvent(event: DailyCheckEvent) {
        when (event) {
            is DailyCheckEvent.DeleteDailyCheck -> {
                viewModelScope.launch {
                    dao.deleteDailyCheck(event.dailyCheck)
                }
            }

            DailyCheckEvent.HideDialog -> {
                _state.update {
                    it.copy(
                        isAddingDailyCheck = false
                    )
                }
            }

            DailyCheckEvent.SaveDailyCheck -> {
                val date = Date()
                val physical = state.value.physical
                val work = state.value.work
                val social = state.value.social

                val dailyCheck = DailyCheck(
                    date = date,
                    physical = physical,
                    work = work,
                    social = social,
                )
                viewModelScope.launch {
                    dao.upsertDailyCheck(dailyCheck)
                }

                _state.update {
                    it.copy(
                        isAddingDailyCheck = false,
                        physical = false,
                        work = false,
                        social = false,
                    )
                }
            }

            is DailyCheckEvent.SetPhysical -> {
                _state.update {
                    it.copy(
                        physical = event.physical
                    )
                }
            }

            is DailyCheckEvent.SetSocial -> {
                _state.update {
                    it.copy(
                        social = event.social
                    )
                }
            }

            is DailyCheckEvent.SetWork -> {
                _state.update {
                    it.copy(
                        work = event.work
                    )
                }
            }

            DailyCheckEvent.ShowDialog -> {
                _state.update {
                    it.copy(
                        isAddingDailyCheck = true
                    )
                }
            }

            is DailyCheckEvent.SortDailyChecks -> {
                _sortType.value = event.sortType
            }
        }
    }
}
