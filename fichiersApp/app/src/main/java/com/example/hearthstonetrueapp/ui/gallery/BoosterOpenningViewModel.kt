package com.example.hearthstonetrueapp.ui.gallery

import android.util.Log
import androidx.lifecycle.*
import com.example.hearthstonetrueapp.bdd.MyCards
import com.example.hearthstonetrueapp.bdd.MyCardsRepository
import com.example.hearthstonetrueapp.dataClass.CardsRepository
import com.example.hearthstonetrueapp.dataClass.model.Card
import kotlin.random.Random

class BoosterOpenningViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is gallery Fragment"
    }
    val text: LiveData<String> = _text

    var myCardsLiveData = MyCardsRepository.getCards()
    var allCardsLiveData = CardsRepository.getMyCardsFromMutableLiveData()

    fun openABooster(allCard : List<Card>, myCards : List<MyCards>) : List<Card> {
        var boosterCardsList : MutableList<Card> = emptyList<Card>().toMutableList()
        Log.i("card count", "${allCard.count()}")

        allCard.forEach {
            if (!myCards.contains(it.toMyCards())){

            }
        }


        for (i in  0..4){
            var cardFounded = allCard[(Math.random() * allCard.count()).toInt()]
            boosterCardsList.add(cardFounded)
            //MyCardsRepository.insertCard(MyCards(hearthstoneCardId = cardFounded.id, name = cardFounded.name))
        }
        return boosterCardsList
    }

    companion object Factory: ViewModelProvider.Factory{

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return BoosterOpenningViewModel() as T
        }

    }
}