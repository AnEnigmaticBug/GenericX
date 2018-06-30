package com.example.nishant.genericx.view.eventlist.filter

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import com.example.nishant.genericx.R
import com.example.nishant.genericx.data.model.Criterion
import com.example.nishant.genericx.viewmodel.eventlist.filter.FilterMainMenuViewModel
import kotlinx.android.synthetic.main.fragment_event_filter_main_menu.*
import kotlinx.android.synthetic.main.fragment_event_filter_main_menu.view.*

/**
 * Created by AnEnigmaticBug on 28/6/18.
 */

class FilterMainMenuFragment : Fragment() {

    enum class ItemType { Category, Venue }

    interface ActionListener : BaseFilterFragmentListener {

        fun onSearchBTNClicked()

        fun showEventFilterItemList(type: ItemType)
    }

    lateinit var listener: ActionListener
    lateinit var viewModel: FilterMainMenuViewModel

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        viewModel = ViewModelProviders.of(this).get(FilterMainMenuViewModel::class.java)
        if(context is ActionListener) {
            listener = context
        }
        else {
            throw ClassCastException("I need a ActionListener dumb arse.")
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootPOV = inflater.inflate(R.layout.fragment_event_filter_main_menu, container, false)

        setupButtons(rootPOV)
        setupLiveDataUses(rootPOV)

        return rootPOV
    }

    private fun setupLiveDataUses(rootPOV: View) {
        viewModel.activeCriterion.observe(this, Observer {
            when(it!!) {
                Criterion.EventDay -> {
                    rootPOV.showByEventDayBTN.setTextColor(resources.getColor(R.color.electricViolet))
                    rootPOV.showByCategoryBTN.setTextColor(resources.getColor(R.color.shadyLady))
                }
                Criterion.Category -> {
                    rootPOV.showByCategoryBTN.setTextColor(resources.getColor(R.color.electricViolet))
                    rootPOV.showByEventDayBTN.setTextColor(resources.getColor(R.color.shadyLady))
                }
            }
        })

        viewModel.ongoingOnlyMode.observe(this, Observer {
            when(it!!) {
                true  -> showOngoingBTN.setTextColor(resources.getColor(R.color.electricViolet))
                false -> showOngoingBTN.setTextColor(resources.getColor(R.color.shadyLady))
            }
        })

        viewModel.favoritesOnlyMode.observe(this, Observer {
            when(it!!) {
                true  -> showFavoritesBTN.setTextColor(resources.getColor(R.color.electricViolet))
                false -> showFavoritesBTN.setTextColor(resources.getColor(R.color.shadyLady))
            }
        })
    }

    private fun setupButtons(rootPOV: View) {
        rootPOV.findViewById<ImageButton>(R.id.closeBTN).setOnClickListener {
            listener.removeCurrentFragment()
        }
        rootPOV.findViewById<Button>(R.id.showVenuesBTN).setOnClickListener {
            listener.showEventFilterItemList(ItemType.Category)
        }

        rootPOV.showByEventDayBTN.setOnClickListener {
            viewModel.enableShowByEventDay()
        }
        rootPOV.showByCategoryBTN.setOnClickListener {
            viewModel.enableShowByCategory()
        }

        rootPOV.showCategoriesBTN.setOnClickListener {
            listener.showEventFilterItemList(ItemType.Category)
        }

        rootPOV.showVenuesBTN.setOnClickListener {
            listener.showEventFilterItemList(ItemType.Venue)
        }

        rootPOV.showOngoingBTN.setOnClickListener {
            viewModel.toggleOngoingOnlyMode()
        }

        rootPOV.showFavoritesBTN.setOnClickListener {
            viewModel.toggleFavoritesOnlyMode()
        }

        rootPOV.resetBTN.setOnClickListener { viewModel.resetToDefaults() }
    }
}