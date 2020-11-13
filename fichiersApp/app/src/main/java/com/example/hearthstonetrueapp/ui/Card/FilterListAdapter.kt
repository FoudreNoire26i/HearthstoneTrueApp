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
import kotlinx.android.synthetic.main.item_filter.view.*

class FilterListAdapter(private val filterListener: SetFilterListener) :
    RecyclerView.Adapter<FilterListAdapter.FilterListViewHolder>() {

    var adapterFilterList = emptyList<FilterItemForSpinner>()

    class FilterListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilterListViewHolder {

        val v: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_filter, parent, false)
        return FilterListViewHolder(v)
    }

    override fun getItemCount(): Int {
        return adapterFilterList.size
    }

    override fun onBindViewHolder(holder: FilterListViewHolder, position: Int) {

        val mySpinner: Spinner = holder.itemView.viewholderSpinner
        val filterTabValues = holder.itemView.context.resources.getStringArray(adapterFilterList[position].filterValueList)

        ArrayAdapter.createFromResource(
            holder.itemView.context,
            adapterFilterList[position].filterValueList, android.R.layout.simple_spinner_item
        ).also {
                it.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                mySpinner.adapter = it
            }

        mySpinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position2: Int,
                id: Long
            ) {

                adapterFilterList[position].isChecked = true
                adapterFilterList[position].actualValue = filterTabValues[position2]

                filterListener.returnFilter(adapterFilterList)

                Toast.makeText(
                    parent?.context,
                    "filtre sur : ${ adapterFilterList[position].filterName} -> " + filterTabValues[position2],
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    fun setFilter(spinnerList: List<FilterItemForSpinner>) {
        this.adapterFilterList = spinnerList
        notifyDataSetChanged()
    }

}