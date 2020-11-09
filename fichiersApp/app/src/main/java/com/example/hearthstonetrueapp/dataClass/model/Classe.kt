package com.example.hearthstonetrueapp.dataClass.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Classe (
    @SerializedName("id")
    @Expose
    val id : Int,

    @SerializedName("slug")
    @Expose
    val slug : String,

    @SerializedName("name")
    @Expose
    val name : String,

    @SerializedName("cardId")
    @Expose
    val cardId : Int
)