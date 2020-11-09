package com.example.hearthstonetrueapp.ui.Card

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.hearthstonetrueapp.R
import com.example.hearthstonetrueapp.dataClass.model.Card
import kotlinx.android.synthetic.main.item_filter.view.*

class FilterListAdapter(): RecyclerView.Adapter<FilterListAdapter.FilterListViewHolder>() {

    var adapterFilterList = emptyList<FilterItemForSpinner>()

    class FilterListViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilterListViewHolder {

        val v : View= LayoutInflater.from(parent.context).inflate(R.layout.item_filter,parent,false)
        val viewHolder = FilterListViewHolder(v)
        return viewHolder
    }

    override fun getItemCount(): Int {
        return adapterFilterList.size
    }

    override fun onBindViewHolder(holder: FilterListViewHolder, position: Int) {

        val mySpinner: Spinner = holder.itemView.viewholderSpinner
        val filterTabValues = holder.itemView.context.resources.getStringArray(adapterFilterList.get(position).filterValueList)

        ArrayAdapter.createFromResource(holder.itemView.context,adapterFilterList.get(position).filterValueList,android.R.layout.simple_spinner_item)
            .also {
                it.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                mySpinner.adapter = it
            }

        mySpinner.onItemSelectedListener= object:
        AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                Toast.makeText(
                    parent?.context,"filtre sur : "
                            + filterTabValues[position] , Toast.LENGTH_SHORT).show()
            }

        }
    }

    fun setFilter(spinnerList: List<FilterItemForSpinner>){
        this.adapterFilterList = spinnerList
        notifyDataSetChanged()
    }
}