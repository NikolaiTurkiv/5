package com.test.a5.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bankroll_entity")
data class BankrollEntity(
    @PrimaryKey
    val bankrollName: String,
    val capitalStart: Double,
    val capitalActual: Double
)