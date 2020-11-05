package com.example.hearthstonetrueapp.api

import com.example.hearthstonetrueapp.accessToken
import com.example.hearthstonetrueapp.dataClass.model.Card
import com.example.hearthstonetrueapp.local
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query


//headers
//https://futurestud.io/tutorials/oauth-2-on-android-with-retrofit

interface ApiHearthstone {
    @GET("52119")
    fun getCardById(@Query("access_token") token : String = accessToken,
                    @Query("locale") locale: String = local
    ) : Call<Card>
}