package com.example.nishant.genericx.view.eventlist.filter

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import com.example.nishant.genericx.R
import com.example.nishant.genericx.viewmodel.eventlist.filter.CategoryListViewModel
import com.example.nishant.genericx.viewmodel.eventlist.filter.FilterItemListViewModel
import com.example.nishant.genericx.viewmodel.eventlist.filter.VenueListViewModel
import kotlinx.android.synthetic.main.fragment_filter_item_list.view.*
import kotlinx.android.synthetic.main.row_filter_items_rcy.view.*

/**
 * Created by AnEnigmaticBug on 28/6/18.
 */

class FilterItemListFragment : Fragment() {

    interface Listener : BaseFragmentListener {

        fun showEventFilterMainMenu()
    }

    private lateinit var listener: Listener
    lateinit var viewModel: FilterItemListViewModel

    override fun onAttach(context: Context?) {
        super.onAttach(context)

        viewModel = when(FilterListType.valueOf(arguments?.getString("TYPE")!!)) {
            FilterListType.Category -> ViewModelProviders.of(this).get(CategoryListViewModel::class.java)
            FilterListType.Venue    -> ViewModelProviders.of(this).get(VenueListViewModel::class.java)
        }

        if(context is Listener) {
            listener = context
        }
        else {
            throw ClassCastException("I need a Listener dumb arse.")
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootPOV = inflater.inflate(R.layout.fragment_filter_item_list, container, false)

        setupButtons(rootPOV)
        rootPOV.filterItemsRCY.adapter = FilterItemsAdapter()

        viewModel.itemList.observe(this, Observer {
            (rootPOV.filterItemsRCY.adapter as FilterItemsAdapter).items = it!!
        })

        return rootPOV
    }

    private fun setupButtons(rootPOV: View) {
        rootPOV.backBTN.setOnClickListener {
            listener.showEventFilterMainMenu()
        }
        rootPOV.doneBTN.setOnClickListener {
            listener.removeCurrentFragment()
        }
    }

    inner class FilterItemsAdapter : RecyclerView.Adapter<FilterItemsAdapter.FilterItemsVHolder>() {

        var items: List<FilterItemListViewModel.FilterDisplayItem> = listOf()
            set(value) {
                field = value
                notifyDataSetChanged()
            }

        override fun getItemCount() = items.size

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
                FilterItemsVHolder(LayoutInflater.from(parent.context).inflate(R.layout.row_filter_items_rcy, parent, false))

        override fun onBindViewHolder(holder: FilterItemsVHolder, position: Int) {
            holder.itemNameLBL.text = items[position].name
            when(items[position].enabled) {
                true  -> holder.selectionToggleBTN.setImageResource(R.drawable.ic_selected_24dp)
                false -> holder.selectionToggleBTN.setImageResource(R.drawable.ic_deselected_24dp)
            }
            holder.selectionToggleBTN.setOnClickListener {
                viewModel.toggleItem(position)
            }
        }

        inner class FilterItemsVHolder(rootPOV: View) : RecyclerView.ViewHolder(rootPOV) {
            val itemNameLBL: TextView = rootPOV.itemNameLBL
            val selectionToggleBTN: ImageButton = rootPOV.selectionToggleBTN
        }
    }
}