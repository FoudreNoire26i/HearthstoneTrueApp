package com.example.hearthstonetrueapp.ui.Card

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hearthstonetrueapp.R
import com.example.hearthstonetrueapp.dataClass.model.Card
import kotlinx.android.synthetic.main.fragment_card_list.*

class CardListFragment: Fragment(), CardListAdapter.CardListAdapterClickListener {

    private lateinit var cardListViewModel: CardListViewModel


    lateinit var cardListRecyclerView: RecyclerView
    lateinit var cardListGridLayoutManager: RecyclerView.LayoutManager


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

        cardListGridLayoutManager = GridLayoutManager(this.context, 3)
        cardListRecyclerView.layoutManager = cardListGridLayoutManager

        val cardListAdapter = CardListAdapter(this)

        cardListRecyclerView.adapter = cardListAdapter

        cardListRecyclerView.setHasFixedSize(true)

        cardListViewModel.cardsListLiveData.observe(viewLifecycleOwner, Observer {
            cardListAdapter.setData(it.cards)
        })

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

}