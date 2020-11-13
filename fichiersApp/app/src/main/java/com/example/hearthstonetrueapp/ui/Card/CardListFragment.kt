package com.example.hearthstonetrueapp.ui.Card

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.*
import com.example.hearthstonetrueapp.R
import com.example.hearthstonetrueapp.dataClass.model.Card
import kotlinx.android.synthetic.main.fragment_card_list.*

class CardListFragment: Fragment(), CardListAdapter.CardListAdapterClickListener, SetFilterListener {

    private lateinit var cardListViewModel: CardListViewModel

    lateinit var cardListRecyclerView: RecyclerView
    lateinit var filterListRecyclerView: RecyclerView
    lateinit var cardListAdapter: CardListAdapter

    lateinit var cardListGridLayoutManager: RecyclerView.LayoutManager
    lateinit var filterListGridLayoutManager: RecyclerView.LayoutManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        activity?.run {
            cardListViewModel = ViewModelProvider(this,CardListViewModel).get() }

        return inflater.inflate(R.layout.fragment_card_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        cardListRecyclerView = view.findViewById<RecyclerView>(R.id.cardListRecyclerView)
        filterListRecyclerView = view.findViewById(R.id.filterListRecyclerView)

        cardListGridLayoutManager = GridLayoutManager(this.context, 3)
        cardListRecyclerView.layoutManager = cardListGridLayoutManager
//        val snapHelper = PagerSnapHelper()
//        snapHelper.attachToRecyclerView(cardListRecyclerView)

        filterListGridLayoutManager = LinearLayoutManager(this.context,RecyclerView.HORIZONTAL,false)
        filterListRecyclerView.layoutManager = filterListGridLayoutManager
//        snapHelper.attachToRecyclerView(filterListRecyclerView)


        cardListAdapter = CardListAdapter(this)
        cardListRecyclerView.adapter = cardListAdapter
        cardListRecyclerView.setHasFixedSize(true)
        cardListViewModel.listeDeCartesLiveData.observe(viewLifecycleOwner, Observer {
            it.sortedBy { it.classId }
            cardListAdapter.setData(it)

        })


        val filterListAdapter = FilterListAdapter(this)
        filterListRecyclerView.adapter = filterListAdapter
        filterListRecyclerView.setHasFixedSize(true)

        //////////// Création et ajout des filtres ///////////////

        var myFilterList = mutableListOf<FilterItemForSpinner>()

        ///// filtre par coup en mana /////

        var filtreParCoupEnManaDC = FilterItemForSpinner("Cout en mana",R.array.coutEnMana,false,"")
        myFilterList.add(filtreParCoupEnManaDC)

        ///// filtre par classe /////

        var filtreParClasse = FilterItemForSpinner("Classes",R.array.cardClasses,false,"")
        myFilterList.add(filtreParClasse)

        ///// filtre par rareté /////

        var filtreParRarete = FilterItemForSpinner("Pv",R.array.cardRarety,false, "")
        myFilterList.add(filtreParRarete)

        ///// filtre par attaque /////

        var filtreParAttaque = FilterItemForSpinner("Attaque",R.array.cardAttack,false, "")
        myFilterList.add(filtreParAttaque)

        ///// filtre par pv /////

        var filtreParPV = FilterItemForSpinner("Pv",R.array.cardHealth,false, "")
        myFilterList.add(filtreParPV)

        ///// filtre par extensions /////

        var filtreParExtension = FilterItemForSpinner("Extension",R.array.cardExtension,false, "")
        myFilterList.add(filtreParExtension)

        filterListAdapter.setFilter(myFilterList)

        ///////////////// Fin de la création et ajout des filtres ////////////////

        searchBar.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                cardListAdapter.setData(
                    cardListViewModel.getMyCardList().filter {
                        it.name.contains(newText as CharSequence)
                    }

                )
                return true
            }
        })
    }

    override fun onClick(dataPosition: Int, card: Card) {
        cardListViewModel.mySelectedCard = card
        findNavController().navigate(R.id.action_nav_cardList_to_nav_cardDetail)
    }

    override fun returnFilter(filterList: List<FilterItemForSpinner>) {
         cardListViewModel.getMyCardsFromMutableLiveData().observe(viewLifecycleOwner, Observer {cardsList ->

             var listToFilter = cardsList

             filterList.forEach { filterConcerned->

//                 Log.d("testFiltre",filterConcerned.filterName)
//                 Log.d("testFiltre",filterConcerned.isChecked.toString())
//                 Log.d("testFiltre",filterConcerned.actualValue)

                 if (filterConcerned.isChecked){
                     if(filterConcerned.filterName == "Cout en mana"){
                         Log.d("testFiltre","CheckPassage1")
                         when(filterConcerned.actualValue){
                             "7 et +" -> listToFilter = listToFilter.filter {myCard -> myCard.manaCost >= 7 }
                             "Mana (Aucun)" -> listToFilter
                             else -> listToFilter = listToFilter.filter{ myCard -> myCard.manaCost == filterConcerned.actualValue.toInt()}
                         }
                     }
                     if(filterConcerned.filterName == "Classes"){
                         when(filterConcerned.actualValue){
                             "Chasseur de démon" -> listToFilter = listToFilter.filter {myCard -> myCard.classId == 14 }
                             "Démoniste" -> listToFilter = listToFilter.filter { myCard -> myCard.classId == 12 }
                             "Classe (Aucune)" -> listToFilter
                         }
                     }
                 }
             }
             cardListAdapter.setData(listToFilter)
        })
    }

}

interface SetFilterListener {
    fun returnFilter(myFilterList: List<FilterItemForSpinner>)
}