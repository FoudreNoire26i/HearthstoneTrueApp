package com.example.hearthstonetrueapp.ui.Card

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.hearthstonetrueapp.dataClass.CardsRepository
import com.example.hearthstonetrueapp.dataClass.model.Card
import com.example.hearthstonetrueapp.dataClass.model.CardsPageList

class CardListViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is cardList Fragment"
    }
    val text: LiveData<String> = _text

    var cardListLiveData: LiveData<Card> = this.getCardsById()
    var cardsListLiveData: LiveData<CardsPageList> = this.getCards()

    private fun getCards() = CardsRepository.getCards()
    fun getCardsById() = CardsRepository.getCardById(1)

}