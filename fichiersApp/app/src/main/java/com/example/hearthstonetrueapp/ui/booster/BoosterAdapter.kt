package com.example.hearthstonetrueapp.ui.booster

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.drawable.toBitmap
import androidx.recyclerview.widget.RecyclerView
import com.example.hearthstonetrueapp.R
import com.example.hearthstonetrueapp.dataClass.model.Card
import com.example.hearthstonetrueapp.ui.Card.CardListAdapter
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_image_card.view.*

class BoosterAdapter(private val clickListener: BoosterAdapterClickListener): RecyclerView.Adapter<BoosterAdapter.BoosterViewHolder>() {

    var boosterAdapterCardList = emptyList<Card>()

    class BoosterViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BoosterViewHolder {

        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_image_card, parent, false)

        return BoosterViewHolder(v)
    }

    override fun getItemCount(): Int {
        return boosterAdapterCardList.size
    }

    fun setData(cardList: List<Card>) {
        this.boosterAdapterCardList = cardList
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: BoosterViewHolder, position: Int) {
        val myCard = boosterAdapterCardList[position]

        holder.itemView.setOnClickListener {
            clickListener.onClick(position, myCard)
        }

        Picasso.get().load(Uri.decode(myCard.imageUrl)).into(holder.itemView.myCardImage)    }

    interface BoosterAdapterClickListener {
        fun onClick(dataPosition: Int, card: Card)
    }

}