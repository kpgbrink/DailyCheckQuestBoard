package com.example.dailycheckquestboard

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface DailyCheckDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsertDailyCheck(dailyCheck: DailyCheck)

    @Update
    fun updateDailyCheck(dailyCheck: DailyCheck)

    @Delete
    fun deleteDailyCheck(dailyCheck: DailyCheck)

    @Query("SELECT * FROM DailyCheck ORDER BY date ASC")
    fun getDailyCheckOrderedByDate(): Flow<List<DailyCheck>>
}