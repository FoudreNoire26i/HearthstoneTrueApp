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
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hearthstonetrueapp.R
import com.example.hearthstonetrueapp.dataClass.model.Card

class CardListFragment: Fragment(), CardListAdapter.CardListAdapterClickListener {

    private lateinit var cardListViewModel: CardListViewModel


    lateinit var cardListRecyclerView: RecyclerView
    // lateinit var cardListAdapter: RecyclerView.Adapter<CardListAdapter.CardListViewHolder>
    lateinit var cardListLayoutManager: RecyclerView.LayoutManager

    lateinit var cardListGridLayoutManager: RecyclerView.LayoutManager


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        cardListViewModel =
            ViewModelProvider(this).get(CardListViewModel::class.java)

        val root = inflater.inflate(R.layout.fragment_card_list, container, false)
        val textView: TextView = root.findViewById(R.id.text_cardList)

        cardListViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cardListRecyclerView = view.findViewById<RecyclerView>(R.id.cardListRecyclerView)

        cardListLayoutManager = LinearLayoutManager(this.context)

        cardListGridLayoutManager = GridLayoutManager(this.context,3)

        cardListRecyclerView.layoutManager = cardListGridLayoutManager

        val cardListAdapter = CardListAdapter(this)

        cardListRecyclerView.adapter = cardListAdapter

        cardListRecyclerView.setHasFixedSize(true)

       /* cardListViewModel.cardListLiveData.observe(viewLifecycleOwner, Observer {

            val list = mutableListOf<Card>()
            list.add(it)
            cardListAdapter.setData(list)
        })*/

        cardListViewModel.cardsListLiveData.observe(viewLifecycleOwner, Observer {

            val list = mutableListOf<Card>()
            it.cards.forEach { card ->
                list.add(card)
            }
            cardListAdapter.setData(list)
        })
    }

    override fun onClick(dataPosition: Int, card: Card) {
        Log.d("test","ceci est un test du onClickListener")
    }

}