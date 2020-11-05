package com.example.hearthstonetrueapp.dataClass

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.hearthstonetrueapp.api.ApiHearthstoneFactory
import com.example.hearthstonetrueapp.dataClass.model.Card
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object CardsRepository {
    private val apiCard = ApiHearthstoneFactory.myCardsApi
    var magicCardLiveData = MutableLiveData<Card>()

    fun getCardById(id : Int) : MutableLiveData<Card>{
        Log.e("loadAndroidData", "yes")
        apiCard.getCardById().enqueue(object : Callback<Card> {

            override fun onFailure(call: Call<Card>, t: Throwable) {
                Log.e("on Failure :", "retrofit error")
            }

            override fun onResponse(call: Call<Card>, response: Response<Card>) {
                response.body()?.let { magicCardLiveData.postValue(it) }
                Log.e("blop", magicCardLiveData.value?.name ?: "default");
            }
        })
        return magicCardLiveData
    }
}