package com.example.hearthstonetrueapp.ui.Classe

import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.hearthstonetrueapp.R
import com.squareup.picasso.Picasso
import com.example.hearthstonetrueapp.dataClass.model.Card
import com.example.hearthstonetrueapp.dataClass.model.Classe
import com.example.hearthstonetrueapp.dataClass.model.Hero
import com.example.hearthstonetrueapp.ui.Card.CardListAdapter
import kotlinx.android.synthetic.main.item_image_card.view.*

class ClassListAdapter(private val clickListener: ClassListAdapterClickListener): RecyclerView.Adapter<ClassListAdapter.ClassListViewHolder>() {

    var adapterClassList = emptyList<Hero>()

    class ClassListViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClassListViewHolder {
        val v =
            LayoutInflater.from(parent.context).inflate(R.layout.item_image_card, parent, false)

        return ClassListViewHolder(v)
    }

    override fun getItemCount(): Int {
        return adapterClassList.size
    }

    override fun onBindViewHolder(holder: ClassListViewHolder, position: Int) {

        val myHero = adapterClassList[position]

        holder.itemView.setOnClickListener {
            clickListener.onClick(position, myHero)
        }

        Picasso.get().load(Uri.decode(myHero.imageUrl)).into(holder.itemView.myCardImage)
    }

    fun setClass(classList: List<Hero>) {
        this.adapterClassList = classList
        notifyDataSetChanged()
    }

    interface ClassListAdapterClickListener {
        fun onClick(dataPosition: Int, hero: Hero)
    }

}