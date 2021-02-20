package com.example.hearthstonetrueapp.bdd

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.example.hearthstonetrueapp.dataClass.model.Card
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


object MyCardsRepository {
    var cardByIdLiveData = MutableLiveData<List<MyCards>>()
    lateinit var database : AppDataBase

    fun initDatabase(context: Context, scope : CoroutineScope){
         database = AppDataBase.getDatabase(context, scope)
    }

    fun getCards() : MutableLiveData<List<MyCards>> {
        GlobalScope.launch {
            database.myCardsDao().getAll().let { cardByIdLiveData.postValue(it) }
        }
        return cardByIdLiveData
    }

    fun insertCard(c: MyCards){
        GlobalScope.launch {
            database.myCardsDao().insertCard(c)
        }
    }

    fun closeDb() {
        database.close()
    }

}