package com.example.hearthstonetrueapp.api

import com.example.hearthstonetrueapp.client_id
import com.example.hearthstonetrueapp.client_secret
import com.example.hearthstonetrueapp.dataClass.model.Token
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiToken {
    @GET("token")
    fun getToken(@Query("client_id") id : String = client_id,
                 @Query("client_secret") secret: String = client_secret,
                 @Query("grant_type") grant: String = "client_credentials",
    ) : Call<Token>
}