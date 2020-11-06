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

    //if allPage = false, pageNumber is the page to load
    fun getCards(allPage : Boolean, pageNumber : Int = 1) : MutableLiveData<CardsPageList> {
        val truePageNumber : Int =
            if (allPage) 1
            else pageNumber

        apiCard.getCardsByPage(page = truePageNumber).enqueue(object : Callback<CardsPageList> {
            override fun onResponse(
                call: Call<CardsPageList>,
                response: Response<CardsPageList>
            ) {
                response.body()?.let { magicCardsLiveData.postValue(it) }
                val pageCount = response.body()?.pageCount ?: 0
                if (allPage) {
                    for (i in 2..pageCount) {
                        getCards(false, i)
                    }
                }

            }

            override fun onFailure(call: Call<CardsPageList>, t: Throwable) {
                Log.e("on Failure :", "retrofit error")
            }
        })
        return magicCardsLiveData
    }

}

