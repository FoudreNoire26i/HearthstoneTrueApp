package com.example.hearthstonetrueapp.ui.Card

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.hearthstonetrueapp.dataClass.CardsRepository
import com.example.hearthstonetrueapp.dataClass.model.Card
import com.example.hearthstonetrueapp.dataClass.model.CardsPageList

class CardListViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is cardList Fragment"
    }
    val text: LiveData<String> = _text

    var cardListLiveData: LiveData<Card> = this.getCardsById()
    var mySelectedCard: Card? = null
    var cardsListLiveData: LiveData<CardsPageList> = this.getCards()


    private fun getCards() = CardsRepository.getCards(true)
    fun getCardsById() = CardsRepository.getCardById(1)

    fun getMyCardList() = CardsRepository.getMyCardList()



    companion object Factory: ViewModelProvider.Factory{

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return CardListViewModel() as T
        }

    }

}