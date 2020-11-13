package com.example.hearthstonetrueapp.ui.Card

import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.example.hearthstonetrueapp.R
import com.example.hearthstonetrueapp.dataClass.model.Card
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_card_detail.*

class CardDetailFragment: Fragment() {

    var mySelectedCard: Card? = null
    private lateinit var cardViewModel: CardListViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        activity?.run { cardViewModel = ViewModelProvider(this,CardListViewModel).get() }
        mySelectedCard = cardViewModel.mySelectedCard
        return inflater.inflate(R.layout.fragment_card_detail_linear_layout, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Picasso.get().load(Uri.decode(mySelectedCard?.imageUrl)).into(cardDetailImage)

        if(mySelectedCard?.slug != "")
            cardDetailText.text = Html.fromHtml("\"" + mySelectedCard?.slug + "\"", HtmlCompat.FROM_HTML_MODE_LEGACY)
        else{
            cardDetailText.text = "AUNCUNE DESCRIPTION"
        }

    }

}