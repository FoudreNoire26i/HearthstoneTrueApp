package com.example.hearthstonetrueapp.dataClass

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.hearthstonetrueapp.api.ApiHearthstoneFactory
import com.example.hearthstonetrueapp.dataClass.model.Classe
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object ClassRepository {
        private val apiCard = ApiHearthstoneFactory.myCardsApi
        //var cardListLiveData = MutableLiveData<Card>()
        var classeListLiveData = MutableLiveData<List<Classe>>()

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
        fun getClasses(): MutableLiveData<List<Classe>> {
            apiCard.getClasses().enqueue(object : Callback<List<Classe>> {
                override fun onResponse(
                    call: Call<List<Classe>>,
                    response: Response<List<Classe>>
                ) {
                    response.body()?.let {
                        classeListLiveData.postValue(it)
                    }
                }

                override fun onFailure(call: Call<List<Classe>>, t: Throwable) {
                    Log.e("on Failure :", "retrofit error")
                }
            })
            return classeListLiveData
        }
    }

