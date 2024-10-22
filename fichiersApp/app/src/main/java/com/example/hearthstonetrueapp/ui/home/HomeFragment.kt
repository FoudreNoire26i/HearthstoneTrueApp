package com.example.hearthstonetrueapp.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.example.hearthstonetrueapp.R
import com.example.hearthstonetrueapp.bdd.MyCardsRepository
import com.example.hearthstonetrueapp.dataClass.ClassRepository
import com.example.hearthstonetrueapp.ui.Classe.HeroesViewModel

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var classeViewModel: HeroesViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
                ViewModelProvider(this).get(HomeViewModel::class.java)
        classeViewModel =
            ViewModelProvider(this).get(HeroesViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        val textView: TextView = root.findViewById(R.id.text_home)
        /*homeViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })*/

        classeViewModel.classeListLiveData.observe(viewLifecycleOwner) {
            classeViewModel.setHeroListByClass()
        }

        MyCardsRepository.getCards().observe(viewLifecycleOwner, {
            it.forEach { card ->
                Log.e("cards", card.name)
            }
        })

        return root
    }
}