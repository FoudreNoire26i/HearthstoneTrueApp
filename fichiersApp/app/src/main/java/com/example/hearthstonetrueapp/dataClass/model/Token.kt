package com.example.hearthstonetrueapp.dataClass.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Token (
    @SerializedName("access_token")
    @Expose
    val access_token : String,

    @SerializedName("token_type")
    @Expose
    val token_type : String
)