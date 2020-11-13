package com.example.hearthstonetrueapp.ui.Card

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.hearthstonetrueapp.R
import com.example.hearthstonetrueapp.dataClass.model.Card
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_image_card.view.*

class CardListAdapter(private val clickListener: CardListAdapterClickListener): RecyclerView.Adapter<CardListAdapter.CardListViewHolder>() {

    var adapterCardList = emptyList<Card>()

    class CardListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardListViewHolder {
        val v =
            LayoutInflater.from(parent.context).inflate(R.layout.item_image_card, parent, false)

        return CardListViewHolder(v)
    }

    override fun getItemCount(): Int {
        return adapterCardList.size
    }

    override fun onBindViewHolder(holder: CardListViewHolder, position: Int) {

        val myCard = adapterCardList[position]

        holder.itemView.setOnClickListener {
            clickListener.onClick(position, myCard)
        }

        Picasso.get().load(Uri.decode(myCard.imageUrl)).into(holder.itemView.myCardImage)
    }


    fun setData(cardList: List<Card>) {
        this.adapterCardList = cardList
        notifyDataSetChanged()
    }

    interface CardListAdapterClickListener {
        fun onClick(dataPosition: Int, card: Card)
    }

}