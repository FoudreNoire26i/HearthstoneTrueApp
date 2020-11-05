package com.example.hearthstonetrueapp.dataClass

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.hearthstonetrueapp.api.ApiHearthstoneFactory
import com.example.hearthstonetrueapp.dataClass.model.Card
import com.example.hearthstonetrueapp.dataClass.model.CardsPageList
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object CardsRepository {
    private val apiCard = ApiHearthstoneFactory.myCardsApi
    var magicCardLiveData = MutableLiveData<Card>()
    var magicCardsLiveData = MutableLiveData<CardsPageList>()

    fun getCardById(id : Int) : MutableLiveData<Card>{
        apiCard.getCardById().enqueue(object : Callback<Card> {

            override fun onFailure(call: Call<Card>, t: Throwable) {
                Log.e("on Failure :", "retrofit error")
            }

            override fun onResponse(call: Call<Card>, response: Response<Card>) {
                response.body()?.let { magicCardLiveData.postValue(it) }
            }
        })
        return magicCardLiveData
    }

    fun getCards() : MutableLiveData<CardsPageList> {
        apiCard.getCards().enqueue(object : Callback<CardsPageList> {

            override fun onResponse(call: Call<CardsPageList>, response: Response<CardsPageList>) {
                Log.e("blopcards", response.body()?.toString() ?: "def")
                response.body()?.let { magicCardsLiveData.postValue(it) }
            }

            override fun onFailure(call: Call<CardsPageList>, t: Throwable) {
                Log.e("on Failure :", "retrofit error")
            }
        })
        return magicCardsLiveData
    }
}

