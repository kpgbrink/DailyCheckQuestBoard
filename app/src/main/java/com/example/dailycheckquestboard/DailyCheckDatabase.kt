package com.example.dailycheckquestboard

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [DailyCheck::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class DailyCheckDatabase : RoomDatabase() {

    abstract val dao: DailyCheckDao
}