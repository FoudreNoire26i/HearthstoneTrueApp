package com.example.hearthstonetrueapp.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.hearthstonetrueapp.R
import com.example.hearthstonetrueapp.dataClass.CardsRepository
import com.example.hearthstonetrueapp.dataClass.ClassRepository
import com.example.hearthstonetrueapp.ui.Classe.ClassesViewModel

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var classeViewModel: ClassesViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
                ViewModelProvider(this).get(HomeViewModel::class.java)
        classeViewModel =
            ViewModelProvider(this).get(ClassesViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        val textView: TextView = root.findViewById(R.id.text_home)
        /*homeViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })*/

/*
        classeViewModel.classeListLiveData.observe(viewLifecycleOwner, {
            classeViewModel.setHeroListByClass()
        })*/
        classeViewModel.heroListLiveData.observe(viewLifecycleOwner, {
            var tmp = ""
            it.forEach {
                tmp += "${it.name}\n"
            }
        })



        Log.e("blop1", ""+ClassRepository.getClasses().value?.size )
        return root
    }
}