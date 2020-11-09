package com.example.hearthstonetrueapp.ui.Card

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.*
import com.example.hearthstonetrueapp.R
import com.example.hearthstonetrueapp.dataClass.model.Card
import kotlinx.android.synthetic.main.fragment_card_list.*

class CardListFragment: Fragment(), CardListAdapter.CardListAdapterClickListener,FilterListAdapter.setFilterListener {

    private lateinit var cardListViewModel: CardListViewModel

    lateinit var cardListRecyclerView: RecyclerView
    lateinit var filterListRecyclerView: RecyclerView

    lateinit var cardListGridLayoutManager: RecyclerView.LayoutManager
    lateinit var filterListGridLayoutManager: RecyclerView.LayoutManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        activity?.run { cardListViewModel = ViewModelProvider(this,CardListViewModel).get() }

        val root = inflater.inflate(R.layout.fragment_card_list, container, false)

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        cardListRecyclerView = view.findViewById<RecyclerView>(R.id.cardListRecyclerView)
        filterListRecyclerView = view.findViewById(R.id.filterListRecyclerView)

        cardListGridLayoutManager = GridLayoutManager(this.context, 3)
        cardListRecyclerView.layoutManager = cardListGridLayoutManager
        val snapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(cardListRecyclerView)

        filterListGridLayoutManager = LinearLayoutManager(this.context,RecyclerView.HORIZONTAL,false)
        filterListRecyclerView.layoutManager = filterListGridLayoutManager
        snapHelper.attachToRecyclerView(filterListRecyclerView)


        val cardListAdapter = CardListAdapter(this)
        cardListRecyclerView.adapter = cardListAdapter
        cardListRecyclerView.setHasFixedSize(true)
        cardListViewModel.cardsListLiveData.observe(viewLifecycleOwner, Observer {
            cardListAdapter.setData(it.cards)
        })

        val filterListAdapter = FilterListAdapter(this)
        filterListRecyclerView.adapter = filterListAdapter
        filterListRecyclerView.setHasFixedSize(true)

        //////////// Création et ajout des filtres ///////////////

        var myFilterList = mutableListOf<FilterItemForSpinner>()

        ///// filtre par coup en mana /////

        var filtreParCoupEnManaDC = FilterItemForSpinner("Coup en mana",R.array.coutEnMana)
        myFilterList.add(filtreParCoupEnManaDC)

        ///// filtre par classe /////

        var filtreParClasse = FilterItemForSpinner("Classes",R.array.cardClasses)
        myFilterList.add(filtreParClasse)

        ///// filtre par rareté /////

        var filtreParRareté = FilterItemForSpinner("Pv",R.array.cardRarety)
        myFilterList.add(filtreParRareté)

        ///// filtre par attaque /////

        var filtreParAttaque = FilterItemForSpinner("Attaque",R.array.cardAttack)
        myFilterList.add(filtreParAttaque)

        ///// filtre par pv /////

        var filtreParPV = FilterItemForSpinner("Pv",R.array.cardHealth)
        myFilterList.add(filtreParPV)



        filterListAdapter.setFilter(myFilterList)

        Log.d("listTest",myFilterList.toString())

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

    override fun returnFilter(filterOn: String, value: String) {
        Log.d("testFiltre",filterOn)
        Log.d("testFiltre",value)
        if(filterOn == "Coup en mana"){
            cardListViewModel.getMyCardList().filter {
                it.manaCost == value.toInt()
            }
        }

    }

}