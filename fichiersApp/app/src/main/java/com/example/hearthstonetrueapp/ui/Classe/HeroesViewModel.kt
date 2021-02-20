package com.example.hearthstonetrueapp.ui.Classe

import androidx.lifecycle.*
import com.example.hearthstonetrueapp.dataClass.ClassRepository
import com.example.hearthstonetrueapp.dataClass.HeroRepository
import com.example.hearthstonetrueapp.dataClass.model.Classe
import com.example.hearthstonetrueapp.dataClass.model.Hero
import com.example.hearthstonetrueapp.ui.Card.CardListViewModel
import com.example.hearthstonetrueapp.ui.home.HomeFragment

class HeroesViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is cardList Fragment"
    }
    val text: LiveData<String> = _text

    var classeListLiveData: LiveData<List<Classe>> = this.getClasses()
    var heroListLiveData: LiveData<List<Hero>> = Transformations.switchMap(classeListLiveData){
        getHeroesWithClasses(it)
    }

    fun setHeroListByClass(){
        //heroListLiveData = this.getHeroesWithClasses()
    }

    fun getHeroesWithClasses(classList : List<Classe>) : LiveData<List<Hero>>{
        val heroClasseToLoad = classList.filter{ it.cardId > 0 }
        
        heroClasseToLoad.forEach {
            if (heroClasseToLoad.last() == it ){
                return HeroRepository.getHeroe(it.cardId)
            }
            else{
                HeroRepository.getHeroe(it.cardId)
            }
        }
        return HeroRepository.getHeroe(5)
    }
    fun getClasses() = ClassRepository.getClasses()

    companion object Factory: ViewModelProvider.Factory{

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return HeroesViewModel() as T
        }

    }
}