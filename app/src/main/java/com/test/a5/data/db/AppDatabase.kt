package com.test.a5.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.test.a5.data.db.dao.BetManagerDao
import com.test.a5.data.db.entities.BankrollEntity
import com.test.a5.data.db.entities.BetEntity
import com.test.a5.data.db.entities.TeamEntity

@Database(entities = [BetEntity::class,BankrollEntity::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun teamDao(): BetManagerDao
}