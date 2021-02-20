package com.example.hearthstonetrueapp.bdd

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.hearthstonetrueapp.dataClass.model.Card

@Entity
data class MyCards (
    @PrimaryKey(autoGenerate = true) val bdCardId : Int? = null,
    @ColumnInfo(name = "hearthstoneCardId") val hearthstoneCardId : Int,
    @ColumnInfo(name = "name") val name : String,
    @ColumnInfo(name = "founded") val founded : Int = 1
)