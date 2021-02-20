package com.example.hearthstonetrueapp.ui.Card

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.*
import com.example.hearthstonetrueapp.R
import com.example.hearthstonetrueapp.dataClass.model.Card
import com.example.hearthstonetrueapp.ui.Classe.HeroesViewModel
import com.example.hearthstonetrueapp.ui.booster.BoosterAdapter
import com.example.hearthstonetrueapp.ui.booster.BoosterOpenningViewModel
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.fragment_card_list.*

class CardListFragment: Fragment(), CardListAdapter.CardListAdapterClickListener, SetFilterListener {

    private lateinit var cardListViewModel: CardListViewModel
    private lateinit var classesViewModel: HeroesViewModel
    private lateinit var boosterViewModel: BoosterOpenningViewModel

    lateinit var cardListRecyclerView: RecyclerView
    lateinit var filterListRecyclerView: RecyclerView

    lateinit var cardListAdapter: CardListAdapter
    lateinit var filterListAdapter: FilterListAdapter

    lateinit var cardListGridLayoutManager: RecyclerView.LayoutManager
    lateinit var filterListGridLayoutManager: RecyclerView.LayoutManager

    val classMap = hashMapOf<String,Int>()

    lateinit var myFilterMenu: ImageView


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        activity?.run {
            cardListViewModel = ViewModelProvider(this,CardListViewModel).get()
            classesViewModel = ViewModelProvider(this,HeroesViewModel).get()
            boosterViewModel = ViewModelProvider( this , BoosterOpenningViewModel).get()
        }


        return inflater.inflate(R.layout.fragment_card_list, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.M)
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


        myFilterMenu = view.findViewById(R.id.filtermenu)
        myFilterMenu.setOnClickListener {
            if (filterListRecyclerView.visibility == View.GONE){
                    filterListRecyclerView.visibility = View.VISIBLE
                    myFilterMenu.setImageResource(android.R.drawable.arrow_up_float)
            }
            else if (filterListRecyclerView.visibility == View.VISIBLE){
                filterListRecyclerView.visibility = View.GONE
                myFilterMenu.setImageResource(android.R.drawable.arrow_down_float)
            }
        }

        cardListAdapter = CardListAdapter(this)
        cardListRecyclerView.adapter = cardListAdapter
        cardListRecyclerView.setHasFixedSize(true)
        cardListViewModel.listeDeCartesLiveData.observe(viewLifecycleOwner, Observer { allCard ->
            boosterViewModel.myCardsLiveData.observe(viewLifecycleOwner, Observer { myCards ->
                cardListAdapter.setDataBd(myCards)
                cardListAdapter.setData(allCard)
            })
        })


        filterListAdapter = FilterListAdapter(this)
        filterListRecyclerView.adapter = filterListAdapter
        filterListRecyclerView.setHasFixedSize(true)

        //////////// Chargement des classes et des extensions et chargement dans des maps ///////////////

        classesViewModel.classeListLiveData.observe(viewLifecycleOwner, Observer {
            it.forEach {
                classMap.put(it.name,it.id)
            }
        })

        //////////// Création et ajout des filtres ///////////////

        val fragmentFilterList = mutableListOf<FilterItemForSpinner>()

        ///// filtre par coup en mana /////
        val filtreParCoupEnManaDC = FilterItemForSpinner("Cout en mana",R.array.coutEnMana,false,0)
        fragmentFilterList.add(filtreParCoupEnManaDC)

        ///// filtre par classe /////
        val filtreParClasse = FilterItemForSpinner("Classes",R.array.cardClasses,false,0)
        fragmentFilterList.add(filtreParClasse)

        ///// filtre par rareté /////
        val filtreParRarete = FilterItemForSpinner("Pv",R.array.cardRarety,false, 0)
        fragmentFilterList.add(filtreParRarete)

        ///// filtre par attaque /////
        val filtreParAttaque = FilterItemForSpinner("Attaque",R.array.cardAttack,false, 0)
        fragmentFilterList.add(filtreParAttaque)

        ///// filtre par pv /////
        val filtreParPV = FilterItemForSpinner("Pv",R.array.cardHealth,false, 0)
        fragmentFilterList.add(filtreParPV)

        ///// filtre par extensions /////
        val filtreParExtension = FilterItemForSpinner("Extension",R.array.cardExtension,false, 0)
        fragmentFilterList.add(filtreParExtension)

        filterListAdapter.setFilter(fragmentFilterList)

        ///////////////// Fin de la création et ajout des filtres ////////////////

        searchBar.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                cardListAdapter.setData(
                    cardListViewModel.getMyCardList().filter {
                        it.name.contains(newText as CharSequence,true)
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

    override fun returnFilter(myFilterList: List<FilterItemForSpinner>) {
         cardListViewModel.getMyCardsFromMutableLiveData().observe(viewLifecycleOwner, Observer {cardsList ->

             var listToFilter = cardsList

             myFilterList.forEach { filterConcerned->

//                 Log.d("testFiltre",filterConcerned.filterName)
//                 Log.d("testFiltre",filterConcerned.isChecked.toString())
//                 Log.d("testFiltre",filterConcerned.actualValue)

                 if (filterConcerned.isChecked && filterConcerned.actualValue!=0){
                     when(filterConcerned.filterName){
                         myFilterList.get(0).filterName -> {
                             when(filterConcerned.actualValue){
                                 8 -> listToFilter = listToFilter.filter {myCard -> myCard.manaCost >= 7 }
                                 else -> listToFilter = listToFilter.filter{ myCard -> myCard.manaCost == (filterConcerned.actualValue - 1)}
                             }
                         }
                         myFilterList.get(1).filterName -> {
                             when(filterConcerned.actualValue){
                                 1 -> listToFilter = listToFilter.filter { myCard -> myCard.classId == classMap.get("Neutre") }
                                 2 -> listToFilter = listToFilter.filter { mycard -> mycard.classId == classMap.get("Druide")}
                                 3 -> listToFilter = listToFilter.filter {myCard -> myCard.classId == classMap.get("Chasseur de démons") }
                                 4 -> listToFilter = listToFilter.filter {myCard -> myCard.classId == classMap.get("Mage") }
                                 5 -> listToFilter = listToFilter.filter {myCard -> myCard.classId == classMap.get("Prêtre") }
                                 6 -> listToFilter = listToFilter.filter {myCard -> myCard.classId == classMap.get("Guerrier") }
                                 7 -> listToFilter = listToFilter.filter {myCard -> myCard.classId == classMap.get("Paladin") }
                                 8 -> listToFilter = listToFilter.filter {myCard -> myCard.classId == classMap.get("Chasseur") }
                                 9 -> listToFilter = listToFilter.filter {myCard -> myCard.classId == classMap.get("Voleur") }
                                 10 -> listToFilter = listToFilter.filter {myCard -> myCard.classId == classMap.get("Chaman") }
                                 11 -> listToFilter = listToFilter.filter {myCard -> myCard.classId == classMap.get("Démoniste") }
                             }
                         }
                         myFilterList.get(2).filterName -> {
                             when(filterConcerned.actualValue){
                                 1 -> listToFilter = listToFilter.filter { myCard -> myCard.rarityId == 2 }
                                 2 -> listToFilter = listToFilter.filter { myCard -> myCard.rarityId == 1 }
                                 3 -> listToFilter = listToFilter.filter { myCard -> myCard.rarityId == 3 }
                                 4 -> listToFilter = listToFilter.filter { myCard -> myCard.rarityId == 4 }
                                 5 -> listToFilter = listToFilter.filter { myCard -> myCard.rarityId == 5 }
                             }
                         }
                         myFilterList.get(3).filterName -> {
                             when(filterConcerned.actualValue){
                                 8 -> listToFilter = listToFilter.filter {myCard -> myCard.attack >= 7 }
                                 else -> listToFilter = listToFilter.filter{ myCard -> myCard.attack == (filterConcerned.actualValue - 1)}
                             }
                         }
                         myFilterList.get(4).filterName -> {
                             when(filterConcerned.actualValue){
                                 8 -> listToFilter = listToFilter.filter {myCard -> myCard.health >= 7 }
                                 else -> listToFilter = listToFilter.filter{ myCard -> myCard.health == (filterConcerned.actualValue - 1)}
                             }
                         }
                         myFilterList.get(5).filterName -> {

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