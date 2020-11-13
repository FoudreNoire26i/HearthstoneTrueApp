package com.example.hearthstonetrueapp.ui.Classe

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.hearthstonetrueapp.dataClass.CardsRepository
import com.example.hearthstonetrueapp.dataClass.ClassRepository
import com.example.hearthstonetrueapp.dataClass.HeroRepository
import com.example.hearthstonetrueapp.dataClass.model.Card
import com.example.hearthstonetrueapp.dataClass.model.CardsPageList
import com.example.hearthstonetrueapp.dataClass.model.Classe
import com.example.hearthstonetrueapp.dataClass.model.Hero
import com.example.hearthstonetrueapp.ui.home.HomeFragment

class ClassesViewModel : ViewModel() {

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
        val heroClasseToLoad = classList.filter{ it -> it.cardId > 0 }
        
        heroClasseToLoad.forEach {
            HeroRepository.getHeroe(it.cardId)
            if (heroClasseToLoad.last() == it ){
                val h = HeroRepository.getHeroe(it.cardId).value
                return HeroRepository.getHeroe(it.cardId)
            }
        }
        return HeroRepository.getHeroe(5)
    }
    fun getClasses() = ClassRepository.getClasses()
}