package com.example.searchbin.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "card_table")
data class CardEntity (
    val id: String,
    @PrimaryKey
    val bin: String,
    val url: String,
    val phone: String,
    val city: String
)