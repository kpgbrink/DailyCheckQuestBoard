package com.example.dailycheckquestboard

sealed interface DailyCheckEvent {
    object SaveDailyCheck : DailyCheckEvent
    data class SetWork(val work: Boolean) : DailyCheckEvent
    data class SetSocial(val social: Boolean) : DailyCheckEvent
    data class SetPhysical(val physical: Boolean) : DailyCheckEvent

    object ShowDialog : DailyCheckEvent

    object HideDialog : DailyCheckEvent

    data class SortDailyChecks(val sortType: SortType) : DailyCheckEvent

    data class DeleteDailyCheck(val dailyCheck: DailyCheck) : DailyCheckEvent
}