package com.example.dailycheckquestboard

import androidx.room.Database

@Database(
    entities = [DailyCheck::class],
    version = 1
)
abstract class DailyCheckDatabase {

    abstract val dao: DailyCheckDao
}