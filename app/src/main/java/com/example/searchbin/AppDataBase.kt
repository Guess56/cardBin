package com.example.searchbin

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.searchbin.data.db.dao.CardDao
import com.example.searchbin.data.db.entity.CardEntity

@Database(version = 3, entities = [CardEntity::class])
abstract class AppDataBase: RoomDatabase() {
    abstract fun CardDao(): CardDao
}