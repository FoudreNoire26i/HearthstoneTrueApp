package com.example.hearthstonetrueapp.dataClass.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Hero (
    @SerializedName("id")
    @Expose
    val id : Int,

    @SerializedName("slug")
    @Expose
    val slug : String,

    @SerializedName("classId")
    @Expose
    val classId : Int,

    @SerializedName("cardTypeId")
    @Expose
    val cardTypeId : Int,

    @SerializedName("name")
    @Expose
    val name : String,
    
    @SerializedName("image")
    @Expose
    val imageUrl : String,

    @SerializedName("imageGoldUrl")
    @Expose
    val imageGoldUrl : String,

    @SerializedName("collectible")
    @Expose
    val collectible : Int,

    @SerializedName("childIds")
    @Expose
    val childIds : List<Int>,
)