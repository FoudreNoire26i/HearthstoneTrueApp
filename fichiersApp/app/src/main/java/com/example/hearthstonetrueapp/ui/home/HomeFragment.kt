package com.example.hearthstonetrueapp.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.example.hearthstonetrueapp.R
import com.example.hearthstonetrueapp.bdd.AppDataBase
import com.example.hearthstonetrueapp.bdd.MyCards
import com.example.hearthstonetrueapp.bdd.MyCardsRepository
import com.example.hearthstonetrueapp.ui.Card.CardListViewModel
import com.example.hearthstonetrueapp.ui.Classe.ClassesViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var classeViewModel: ClassesViewModel

    private lateinit var cardsViewModel: CardListViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
                ViewModelProvider(this).get(HomeViewModel::class.java)
        classeViewModel =
            ViewModelProvider(this).get(ClassesViewModel::class.java)
        cardsViewModel =
            ViewModelProvider(this).get(CardListViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        val textView: TextView = root.findViewById(R.id.text_home)
        /*homeViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })*/


        //database
        MyCardsRepository.initDatabase(this.requireContext(), lifecycle.coroutineScope)
        var a : List<MyCards>?

        GlobalScope.launch {
            //truc de la BD
            //MyCardsRepository.insertCard(MyCards(json = "Le nouveau"))
            a = MyCardsRepository.getCards()
        }

        MyCardsRepository.cardByIdLiveData.observe(viewLifecycleOwner, {
            Log.e("lolll", "/"+it.get(0).json+"/")
            Log.e("lolll", "/"+it.get(1).json+"/")
            Log.e("lolll", "/"+it.size+"/")
        })





        //Log.e("blop1", ""+CardsRepository.getCards(true))
        return root
    }

    suspend fun getCardAsync() : List<MyCards>?{
        return MyCardsRepository.getCards()
    }


    fun initDb() : AppDataBase{
        return Room.inMemoryDatabaseBuilder(
            requireContext(),
            AppDataBase::class.java
        )
            .allowMainThreadQueries()
            .build()
    }
}