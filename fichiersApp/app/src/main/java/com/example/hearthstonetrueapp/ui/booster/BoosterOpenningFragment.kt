package com.example.hearthstonetrueapp.ui.booster

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hearthstonetrueapp.R
import com.example.hearthstonetrueapp.dataClass.model.Card
import com.squareup.picasso.Picasso

class BoosterOpenningFragment : Fragment(),BoosterAdapter.BoosterAdapterClickListener {

    private lateinit var boosterOpenningViewModel: BoosterOpenningViewModel

    lateinit var myBoosterCardsRecyclerView: RecyclerView
    lateinit var boosterGridLayoutManager: RecyclerView.LayoutManager

    lateinit var boosterAdapter: BoosterAdapter

    private var myNewCards : List<Card> = emptyList()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_booster, container, false)
        activity?.run {
            boosterOpenningViewModel = ViewModelProvider(this, BoosterOpenningViewModel).get()
        }
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val button: Button = view.findViewById(R.id.buttonTheOnlyOne)

        myBoosterCardsRecyclerView = view.findViewById(R.id.myBoosterCardsRecyclerView)

        boosterGridLayoutManager = LinearLayoutManager(this.context,RecyclerView.HORIZONTAL,false)
        myBoosterCardsRecyclerView.layoutManager = boosterGridLayoutManager

        boosterAdapter = BoosterAdapter(this)
        myBoosterCardsRecyclerView.adapter = boosterAdapter
        myBoosterCardsRecyclerView.setHasFixedSize(true)


        boosterOpenningViewModel.allCardsLiveData.observe(viewLifecycleOwner, Observer { allCards ->
            button.setOnClickListener {
                myNewCards = boosterOpenningViewModel.openABooster(allCards, emptyList())
                boosterAdapter.setData(myNewCards)
            }
        })
    }

    override fun onClick(dataPosition: Int, card: Card) {

    }
}