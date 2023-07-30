package com.example.dailycheckquestboard

data class DailyCheckState(
    val dailyChecks: List<DailyCheck> = emptyList(),
    val physical: Boolean = false,
    val social: Boolean = false,
    val work: Boolean = false,
    val isAddingDailyCheck: Boolean = false,
    val sortType: SortType = SortType.DATE
)
