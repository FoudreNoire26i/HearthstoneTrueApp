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
    var cardByIdLiveData = MutableLiveData<Card>()
    var cardPageLiveData = MutableLiveData<CardsPageList>()
    var cardslistLiveData = MutableLiveData<List<Card>>()
    var myCardsList = mutableListOf<Card>()



    fun getCardById(id: Int): LiveData<Card> {
        Log.e("loadAndroidData", "yes")
        apiCard.getCardById("1").enqueue(object : Callback<Card> {

            override fun onFailure(call: Call<Card>, t: Throwable) {
                Log.e("on Failure :", "retrofit error")
            }

            override fun onResponse(call: Call<Card>, response: Response<Card>) {
                response.body()?.let { cardByIdLiveData.postValue(it) }
                Log.e("blop", cardByIdLiveData.value?.name ?: "default");
            }
        })
        return cardByIdLiveData
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
                    val tmpList = mutableListOf<Card>()
                    tmpList?.addAll(it.cards.filter {
                            myCard -> myCard.cardTypeId != 3 && myCard.cardTypeId != 10
                    })
                    val tmp = CardsPageList(cards = tmpList ?: emptyList(), pageCount = it.pageCount, cardCount = it.cardCount, pageId = it.pageId)
                    cardPageLiveData.postValue(tmp)
                    myCardsList.addAll(tmp.cards)
                }
                if (allPage) {
                    val pageCount = response.body()?.pageCount ?: 0
                    for (i in 2..pageCount) {
                        getCards(false, i)
                    }
                }
                cardslistLiveData.postValue(myCardsList)
            }

            override fun onFailure(call: Call<CardsPageList>, t: Throwable) {
                Log.e("on Failure :", "retrofit error")
            }
        })
        return cardPageLiveData
    }

    fun getMyCardList(): MutableList<Card> {
        return myCardsList
    }

    fun getMyCardsFromMutableLiveData(): LiveData<List<Card>>{
        return cardslistLiveData
    }
}
