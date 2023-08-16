package com.example.dailycheckquestboard

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import java.time.LocalDate
import java.util.Date

@Entity(
    indices = [Index(value = ["localDate"], unique = true)]
)
@TypeConverters(LocalDateConverter::class) // Add this line
data class DailyCheck(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val localDate: LocalDate,
    val work: Boolean,
    val social: Boolean,
    val physical: Boolean,
    @ColumnInfo(name = "updateDate") val updateDate: Date = Date()

)