package com.example.dailycheckquestboard

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [DailyCheck::class],
    version = 4
)
abstract class DailyCheckDatabase : RoomDatabase() {

    abstract val dao: DailyCheckDao
}