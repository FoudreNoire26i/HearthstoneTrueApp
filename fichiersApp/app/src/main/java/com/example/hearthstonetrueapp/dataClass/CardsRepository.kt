package com.example.hearthstonetrueapp.dataClass

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.hearthstonetrueapp.api.ApiHearthstoneFactory
import com.example.hearthstonetrueapp.dataClass.model.Card
import com.example.hearthstonetrueapp.dataClass.model.CardsPageList
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object CardsRepository {
    private val apiCard = ApiHearthstoneFactory.myCardsApi
    var cardListLiveData = MutableLiveData<Card>()
    var cardsListLiveData = MutableLiveData<CardsPageList>()
    var myCardsList = mutableListOf<Card>()


    fun getCardById(id: Int): LiveData<Card> {
        Log.e("loadAndroidData", "yes")
        apiCard.getCardById().enqueue(object : Callback<Card> {

            override fun onFailure(call: Call<Card>, t: Throwable) {
                Log.e("on Failure :", "retrofit error")
            }

            override fun onResponse(call: Call<Card>, response: Response<Card>) {
                response.body()?.let { cardListLiveData.postValue(it) }
                Log.e("blop", cardListLiveData.value?.name ?: "default");
            }
        })
        return cardListLiveData
    }

    fun getCards(allPage: Boolean, pageNumber: Int = 1): MutableLiveData<CardsPageList> {
        val truePageNumber: Int =
            if (allPage) 1
            else pageNumber
        apiCard.getCardsByPage(page = truePageNumber).enqueue(object : Callback<CardsPageList> {
            override fun onResponse(
                call: Call<CardsPageList>,
                response: Response<CardsPageList>
            ) {

                response.body()?.let {
                    val tmpList = cardsListLiveData.value?.cards?.toMutableList()
                    tmpList?.addAll(it.cards)
                    val tmp = CardsPageList(cards = tmpList ?: emptyList(), pageCount = it.pageCount, cardCount = it.cardCount, pageId = it.pageId)
                    cardsListLiveData.postValue(tmp)
                    myCardsList.addAll(it.cards)
                }
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
        return cardsListLiveData
    }

    fun getMyCardList(): MutableList<Card> {
        return myCardsList
    }
}
