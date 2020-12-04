package com.example.hearthstonetrueapp.ui.Classe

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hearthstonetrueapp.R
import com.example.hearthstonetrueapp.dataClass.model.Hero

class ClassListFragment: Fragment(), ClassListAdapter.ClassListAdapterClickListener {

    private lateinit var heroesViewModel: HeroesViewModel

    lateinit var classListRecyclerView: RecyclerView

    lateinit var classListAdapter: ClassListAdapter

    lateinit var classListGridLayoutManager: RecyclerView.LayoutManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        activity?.run {
            heroesViewModel = ViewModelProvider(this,HeroesViewModel).get()
        }

        return inflater.inflate(R.layout.fragment_class_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        classListRecyclerView = view.findViewById<RecyclerView>(R.id.classListRecyclerView)

        classListGridLayoutManager = GridLayoutManager(this.context, 2)
        classListRecyclerView.layoutManager = classListGridLayoutManager

        classListAdapter = ClassListAdapter(this)
        classListRecyclerView.adapter = classListAdapter
        classListRecyclerView.setHasFixedSize(true)
        heroesViewModel.heroListLiveData.observe(viewLifecycleOwner, Observer {
            classListAdapter.setClass(it)
        })

    }

    override fun onClick(dataPosition: Int, hero: Hero) {
        TODO("Not yet implemented")
    }

}