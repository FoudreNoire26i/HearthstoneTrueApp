package com.example.hearthstonetrueapp.dataClass.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CardsPageList (
    @SerializedName("cards")
    @Expose
    val cards : List<Card>,
    @SerializedName("cardCount")
    @Expose
    val cardCount : Int,
    @SerializedName("pageCount")
    @Expose
    val pageCount : Int,
    @SerializedName("page")
    @Expose
    val pageId : Int
)