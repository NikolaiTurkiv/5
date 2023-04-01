package com.test.a5.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bet_entity")
data class BetEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name:String,
    val odd: Double,
    val amount: Double,
    val actualCapital: Double = 0.0,
    val bankrollName: String,
    val isSuccess: Boolean = false,
    val isFailure: Boolean = false,
    val isWait: Boolean = true,
)