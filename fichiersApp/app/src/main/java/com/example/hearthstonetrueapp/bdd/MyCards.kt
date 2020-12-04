package com.example.hearthstonetrueapp.bdd

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.hearthstonetrueapp.dataClass.model.Card

@Entity
data class MyCards (
    @PrimaryKey(autoGenerate = true) val cardId : Int? = null,
    @ColumnInfo(name = "cardJson") val json : String
)