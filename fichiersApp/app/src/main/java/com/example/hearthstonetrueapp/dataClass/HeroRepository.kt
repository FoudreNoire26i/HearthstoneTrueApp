
package com.example.hearthstonetrueapp.dataClass

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.hearthstonetrueapp.api.ApiHearthstoneFactory
import com.example.hearthstonetrueapp.dataClass.model.Hero
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object HeroRepository {
    private val apiCard = ApiHearthstoneFactory.myCardsApi

    //var cardListLiveData = MutableLiveData<Card>()
    var heroListLiveData = MutableLiveData<List<Hero>>()

    /*
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
    */


    fun getHeroe(cardId : Int): MutableLiveData<List<Hero>> {
        if (!heroIsLoaded(cardId)) {
            apiCard.getHeroById("${cardId}").enqueue(object : Callback<Hero> {
                override fun onResponse(
                    call: Call<Hero>,
                    response: Response<Hero>
                ) {
                    Log.i("getHero", "is processing")
                    response.body()?.let {
                        val tmpList = (heroListLiveData.value ?: emptyList()).toMutableList()
                        tmpList.add(it)
                        heroListLiveData.postValue(tmpList)
                    }
                }

                override fun onFailure(call: Call<Hero>, t: Throwable) {
                    Log.e("on Failure :", "retrofit get hero error")
                    //heroListLiveData.postValue(emptyList())
                }
            })
            return heroListLiveData
        }
        else {
            if (heroListLiveData.value?.isEmpty() != false)
                heroListLiveData.postValue(emptyList())
            return heroListLiveData
        }
    }

    fun heroIsLoaded(cardId : Int) : Boolean{
        heroListLiveData.value?.forEach {
            if (it.id == cardId)
                return true
        }
        return false
    }


}