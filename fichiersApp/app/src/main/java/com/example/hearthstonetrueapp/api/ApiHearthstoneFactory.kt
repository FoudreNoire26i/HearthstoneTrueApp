package com.example.hearthstonetrueapp.api

import android.util.Log
import com.example.hearthstonetrueapp.BASE_URL
import com.example.hearthstonetrueapp.accessToken
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiHearthstoneFactory {
    var gson = GsonBuilder().setLenient().create()

    private fun retrofit() : Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create(gson))
        .baseUrl(BASE_URL)/*
        .client(OkHttpClient.Builder().addInterceptor { chain ->
        val request = chain.request().newBuilder().addHeader("Authorization", "Bearer ${accessToken}").build()
        chain.proceed(request)}.build())*/
        .build()

    val myCardsApi : ApiHearthstone = retrofit().create(ApiHearthstone::class.java)
}