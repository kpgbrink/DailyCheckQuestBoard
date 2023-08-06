package com.example.dailycheckquestboard

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface DailyCheckDao {
    @Upsert
    suspend fun upsertDailyCheck(dailyCheck: DailyCheck)

    @Update
    suspend fun updateDailyCheck(dailyCheck: DailyCheck)

    @Delete
    suspend fun deleteDailyCheck(dailyCheck: DailyCheck)

    @Query("SELECT * FROM DailyCheck ORDER BY date ASC")
    fun getDailyCheckOrderedByDate(): Flow<List<DailyCheck>>
}