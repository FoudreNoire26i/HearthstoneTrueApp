package com.example.hearthstonetrueapp.api

import com.example.hearthstonetrueapp.accessToken
import com.example.hearthstonetrueapp.dataClass.model.Card
import com.example.hearthstonetrueapp.dataClass.model.CardsPageList
import com.example.hearthstonetrueapp.dataClass.model.Classe
import com.example.hearthstonetrueapp.dataClass.model.Hero
import com.example.hearthstonetrueapp.local
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


//headers
//https://futurestud.io/tutorials/oauth-2-on-android-with-retrofit

interface ApiHearthstone {
    @GET("cards/{id}")
    fun getCardById(
        @Path("id") id : String,
        @Query("access_token") token : String = accessToken,
        @Query("locale") locale: String = local
    ) : Call<Card>

    @GET("cards")
    fun getCardsByPage(
        @Query("page") page: Int,
        @Query("access_token") token : String = accessToken,
        @Query("locale") locale: String = local
    ) : Call<CardsPageList>

    @GET("metadata/classes")
    fun getClasses(
        @Query("access_token") token : String = accessToken,
        @Query("locale") locale: String = local
    ) : Call<List<Classe>>

    @GET("cards/{id}")
    fun getHeroById(@Path("id") id : String,
                    @Query("access_token") token : String = accessToken,
                    @Query("locale") locale: String = local
    ) : Call<Hero>
}
