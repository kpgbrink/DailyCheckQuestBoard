package com.example.dailycheckquestboard

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.Date

@Entity(
    indices = [Index(value = ["date"], unique = true)]
)
data class DailyCheck(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val date: Date,
    val work: Boolean,
    val social: Boolean,
    val physical: Boolean
)
