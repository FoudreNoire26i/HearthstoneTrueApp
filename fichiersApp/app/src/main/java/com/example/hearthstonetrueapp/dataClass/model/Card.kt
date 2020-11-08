package com.example.hearthstonetrueapp.dataClass.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Card (
    @SerializedName("id")
    @Expose
    val id : Int,

    @SerializedName("collectible")
    @Expose
    val collectible : Int,

    @SerializedName("flavorText")
    @Expose
    val slug : String,

    @SerializedName("classId")
    @Expose
    val classId : Int,

    @SerializedName("multiClassIds")
    @Expose
    val multiClassIds : List<Int>,

    @SerializedName("cardTypeId")
    @Expose
    val cardTypeId : Int,

    @SerializedName("cardSetId")
    @Expose
    val cardSetId : Int,

    @SerializedName("rarityId")
    @Expose
    val rarityId : Int,

    @SerializedName("artistName")
    @Expose
    val artistName : String,

    @SerializedName("health")
    @Expose
    val health : Int,

    @SerializedName("attack")
    @Expose
    val attack : Int,

    @SerializedName("manaCost")
    @Expose
    val manaCost : Int,

    @SerializedName("name")
    @Expose
    val name : String,

    @SerializedName("text")
    @Expose
    val text : String,

    @SerializedName("image")
    @Expose
    val imageUrl : String,

    @SerializedName("imageGoldUrl")
    @Expose
    val imageGoldUrl : String,
/*
    @SerializedName("flavorText")
    @Expose
    val flavorText : String,
*/
    @SerializedName("cropImage")
    @Expose
    val cropImage : String,

    @SerializedName("keywordIds")
    @Expose
    val keywordIds : List<Int>

)