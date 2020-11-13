package com.example.hearthstonetrueapp.dataClass

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.hearthstonetrueapp.accessToken
import com.example.hearthstonetrueapp.api.ApiHearthstoneFactory
import com.example.hearthstonetrueapp.api.ApiTokenFactory
import com.example.hearthstonetrueapp.dataClass.model.Classe
import com.example.hearthstonetrueapp.dataClass.model.Token
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object TokenRepository {
    private val tokenApi = ApiTokenFactory.myTokenApi
    var tokenLiveData = MutableLiveData<Token>()

    fun getToken() : MutableLiveData<Token>{
        tokenApi.getToken().enqueue(object : Callback<Token> {
            override fun onResponse(
                call: Call<Token>,
                response: Response<Token>
            ) {
                response.body()?.let {
                    tokenLiveData.postValue(it)
                }
            }

            override fun onFailure(call: Call<Token>, t: Throwable) {
                Log.e("on Failure :", "retrofit get token error")
            }
        })
        return tokenLiveData
    }
}