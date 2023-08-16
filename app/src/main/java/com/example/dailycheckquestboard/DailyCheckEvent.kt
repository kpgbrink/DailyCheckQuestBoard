package com.example.dailycheckquestboard

import java.time.LocalDate

sealed interface DailyCheckEvent {
    data class UpsertWork(val localDate: LocalDate, val work: Boolean) : DailyCheckEvent
    data class UpsertPhysical(val localDate: LocalDate, val social: Boolean) : DailyCheckEvent
    data class UpsertSocial(val localDate: LocalDate, val physical: Boolean) : DailyCheckEvent
}