package com.example.searchbin.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.searchbin.data.db.entity.CardEntity

@Dao
interface CardDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCard(card: CardEntity)
    @Query("SELECT * FROM card_table")
    suspend fun getCard(): List<CardEntity>
    @Query("SELECT * FROM card_table WHERE id LIKE :id")
    suspend fun getCardById(id: String): CardEntity
    @Delete(entity = CardEntity::class)
    suspend fun deleteCard(cardEntity: CardEntity)
}