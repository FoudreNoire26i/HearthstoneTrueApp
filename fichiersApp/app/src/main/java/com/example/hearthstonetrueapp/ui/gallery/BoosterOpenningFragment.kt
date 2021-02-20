package com.example.hearthstonetrueapp.ui.gallery

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
import com.example.hearthstonetrueapp.R
import com.example.hearthstonetrueapp.dataClass.model.Card
import com.example.hearthstonetrueapp.ui.Card.CardListViewModel
import com.example.hearthstonetrueapp.ui.Classe.HeroesViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_image_card.view.*

class BoosterOpenningFragment : Fragment() {

    private lateinit var boosterOpenningViewModel: BoosterOpenningViewModel

    private var myNewCards : List<Card> = emptyList()
    private var currentNewCardDisplay = 0

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_gallery, container, false)
        activity?.run {
            boosterOpenningViewModel = ViewModelProvider(this, BoosterOpenningViewModel).get()
        }
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val button: Button = view.findViewById(R.id.buttonTheOnlyOne)
        var imageView = view.findViewById<ImageView>(R.id.opened_card_image_view)
        boosterOpenningViewModel.allCardsLiveData.observe(viewLifecycleOwner, Observer { allCards ->
            button.setOnClickListener {
                myNewCards = boosterOpenningViewModel.openABooster(allCards, emptyList())
                Picasso.get().load(Uri.decode(myNewCards[0].imageUrl)).into(imageView)
                boosterOpenningViewModel.openABooster(allCards, emptyList()).forEach { card ->
                    Log.e("card trouve : ", card.name)
                }
                imageView.setOnClickListener {
                    if (currentNewCardDisplay < 4){
                        currentNewCardDisplay++
                    } else {
                        currentNewCardDisplay = 0
                    }
                    Picasso.get().load(Uri.decode(myNewCards[currentNewCardDisplay].imageUrl)).into(imageView)
                }
            }
        })
    }
}