package com.example.hearthstonetrueapp.bdd

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.example.hearthstonetrueapp.dataClass.model.Card
import kotlinx.coroutines.CoroutineScope


object MyCardsRepository {
    var cardByIdLiveData = MutableLiveData<List<MyCards>>()
    lateinit var database : AppDataBase

    fun initDatabase(context: Context, scope : CoroutineScope){
         database = AppDataBase.getDatabase(context, scope)
    }

    fun getCards() : List<MyCards>?{
        database.myCardsDao().getAll().let { cardByIdLiveData.postValue(it) }
        return cardByIdLiveData.value

    }

    fun insertCard(c: MyCards){
        database.myCardsDao().insertCard(c)
    }

    fun closeDb() {
        database.close()
    }

}