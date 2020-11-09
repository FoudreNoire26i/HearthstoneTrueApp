package com.example.hearthstonetrueapp.api

import com.example.hearthstonetrueapp.BASE_URL_HS
import com.example.hearthstonetrueapp.BASE_URL_TOKEN
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiTokenFactory {
    var gson = GsonBuilder().setLenient().create()

    private fun retrofit() : Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create(gson))
        .baseUrl(BASE_URL_TOKEN)/*
        .client(OkHttpClient.Builder().addInterceptor { chain ->
        val request = chain.request().newBuilder().addHeader("Authorization", "Bearer ${accessToken}").build()
        chain.proceed(request)}.build())*/
        .build()

    val myTokenApi : ApiToken = retrofit().create(ApiToken::class.java)
}