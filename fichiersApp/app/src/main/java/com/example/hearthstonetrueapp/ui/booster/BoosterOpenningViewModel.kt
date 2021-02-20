package com.example.hearthstonetrueapp.ui.booster

import android.util.Log
import androidx.lifecycle.*
import com.example.hearthstonetrueapp.bdd.MyCards
import com.example.hearthstonetrueapp.bdd.MyCardsRepository
import com.example.hearthstonetrueapp.dataClass.CardsRepository
import com.example.hearthstonetrueapp.dataClass.model.Card

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
            if (isCardFindable(myCards, it.id)){
                boosterCardsList.add(it)
            }
        }

        var fourOrMaxOfCardsFindable = 4

        if (boosterCardsList.count() < fourOrMaxOfCardsFindable){
            fourOrMaxOfCardsFindable = boosterCardsList.count()
        }


        var result = emptyList<Card>().toMutableList()

        for (i in  0..fourOrMaxOfCardsFindable){
            var cardFounded = boosterCardsList[(Math.random() * boosterCardsList.count()).toInt()]

            var nbOfFounded : Int = 0
            if (isAUserCard(myCards, cardId = cardFounded.id)){
                nbOfFounded = myCards.find {  it.hearthstoneCardId == cardFounded.id  }!!.founded + 1

                //todo : la prochaine ligne est a garnir pour gérer le fait qu'il peut avoir 2 fois la meme carte dans un meme paquet
                boosterCardsList.remove(cardFounded)
            } else {
                nbOfFounded = 1
            }

            result.add(cardFounded)
            MyCardsRepository.insertCard(MyCards(hearthstoneCardId = cardFounded.id, name = cardFounded.name, founded = nbOfFounded))
        }

        return result
    }

    fun isCardFindable(userCards: List<MyCards>, cardId : Int) : Boolean{
        if (userCards.count() == 0){
            return true
        }
        userCards.forEach {
            if (it.hearthstoneCardId == cardId ) {
                return it.founded < 2
            }
        }
        return true
    }

    fun isAUserCard(userCards: List<MyCards>, cardId : Int) : Boolean{
        if (userCards.count() == 0){
            return false
        }
        userCards.forEach {
            if (it.hearthstoneCardId == cardId) {
                return true
            }
        }
        return false
    }

    companion object Factory: ViewModelProvider.Factory{

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return BoosterOpenningViewModel() as T
        }

    }
}