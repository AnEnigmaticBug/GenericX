package com.example.nishant.genericx.view.eventlist

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import com.example.nishant.genericx.R
import com.example.nishant.genericx.data.model.Criterion
import com.example.nishant.genericx.data.model.Event
import com.example.nishant.genericx.util.prettyString
import com.example.nishant.genericx.util.setTint
import com.example.nishant.genericx.view.eventlist.filter.FilterItemListFragment
import com.example.nishant.genericx.view.eventlist.filter.FilterListType
import com.example.nishant.genericx.view.eventlist.filter.FilterMainMenuFragment
import com.example.nishant.genericx.viewmodel.eventlist.EventListViewModel
import kotlinx.android.synthetic.main.activity_event_list.*

class EventListActivity : AppCompatActivity(), FilterMainMenuFragment.Listener, FilterItemListFragment.Listener {

    private lateinit var viewModel: EventListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_list)

        viewModel = ViewModelProviders.of(this).get(EventListViewModel::class.java)

        viewModel.criterion.observe(this, Observer {
            if(it != null) {
                eventsRCY.adapter = when(it) {
                    Criterion.EventDay -> EventsByEventDayAdapter()
                    Criterion.Category -> EventsByCategoryAdapter()
                }
            }
        })

        viewModel.pageTitle.observe(this, Observer {
            pageTitleLBL.text = it
        })

        viewModel.eventsToDisplay.observe(this, Observer {
            if(it != null) {
                (eventsRCY.adapter as HasEvents).events = it
            }
            else {
                Log.e("EventListActivity", "Null List given by eventsToDisplay")
            }
        })

        nextPageBTN.setOnClickListener { viewModel.showNextPage() }
        prevPageBTN.setOnClickListener { viewModel.showPrevPage() }

        filterBTN.setOnClickListener {
            supportFragmentManager.beginTransaction()
                    .add(R.id.filterMenuHolderFRM, FilterMainMenuFragment(), "FilterMenu")
                    .commit()
        }
    }



    //Fragment related code

    override fun removeCurrentFragment() {
        supportFragmentManager.beginTransaction()
                .remove(supportFragmentManager.fragments.last())
                .commit()
    }

    override fun onSearchBTNClicked() {
    }

    override fun showEventFilterItemList(type: FilterListType) {
        val bundle = Bundle().also { it.putString("TYPE", type.toString()) }
        supportFragmentManager.beginTransaction()
                .replace(R.id.filterMenuHolderFRM, FilterItemListFragment().also { it.arguments = bundle })
                .commit()
    }

    override fun showEventFilterMainMenu() {
        supportFragmentManager.beginTransaction()
                .replace(R.id.filterMenuHolderFRM, FilterMainMenuFragment())
                .commit()
    }



    //RecyclerView Adapters

    private interface HasEvents {

        var events: List<Event>
    }

    inner class EventsByEventDayAdapter : RecyclerView.Adapter<EventsByEventDayAdapter.EventsVHolder>(), HasEvents {

        override var events: List<Event> = listOf()
            set(value) {
                field = value
                notifyDataSetChanged()
            }

        override fun getItemCount() = events.size

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
                EventsVHolder(LayoutInflater.from(parent.context).inflate(R.layout.row_events_rcy_1, parent, false))

        override fun onBindViewHolder(holder: EventsVHolder, position: Int) {
            val event = events[position]
            holder.nameLBL.text = event.name
            holder.timeLBL.text = event.datetime.toLocalTime().prettyString()
            holder.venueLBL.text = event.venue.prettyString()
            holder.categoryLBL.text = event.category.prettyString()
            when(event.isFavorite) {
                true  -> holder.favoriteBTN.setTint(R.color.wildStrawberry)
                false -> holder.favoriteBTN.setTint(R.color.shadyLady)
            }
            holder.favoriteBTN.setOnClickListener {
                viewModel.toggleFavorite(event)
            }
        }

        inner class EventsVHolder(val rootPOV: View) : RecyclerView.ViewHolder(rootPOV) {

            val nameLBL: TextView = rootPOV.findViewById(R.id.nameLBL)
            val timeLBL: TextView = rootPOV.findViewById(R.id.timeLBL)
            val venueLBL: TextView = rootPOV.findViewById(R.id.venueLBL)
            val categoryLBL: TextView = rootPOV.findViewById(R.id.categoryLBL)

            val favoriteBTN: ImageButton = rootPOV.findViewById(R.id.favoriteBTN)
        }
    }

    inner class EventsByCategoryAdapter : RecyclerView.Adapter<EventsByCategoryAdapter.EventsVHolder>(), HasEvents {

        override var events: List<Event> = listOf()
            set(value) {
                field = value
                notifyDataSetChanged()
            }

        override fun getItemCount() = events.size

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
                EventsVHolder(LayoutInflater.from(parent.context).inflate(R.layout.row_events_rcy_2, parent, false))

        override fun onBindViewHolder(holder: EventsVHolder, position: Int) {
            val event = events[position]
            holder.nameLBL.text = event.name
            holder.dateLBL.text = event.datetime.toLocalDate().prettyString()
            holder.timeLBL.text = event.datetime.toLocalTime().prettyString()
            holder.venueLBL.text = event.venue.prettyString()
            when(event.isFavorite) {
                true  -> holder.favoriteBTN.setTint(R.color.wildStrawberry)
                false -> holder.favoriteBTN.setTint(R.color.shadyLady)
            }
            holder.favoriteBTN.setOnClickListener {
                viewModel.toggleFavorite(event)
            }
        }

        inner class EventsVHolder(val rootPOV: View) : RecyclerView.ViewHolder(rootPOV) {

            val nameLBL: TextView = rootPOV.findViewById(R.id.nameLBL)
            val dateLBL: TextView = rootPOV.findViewById(R.id.dateLBL)
            val timeLBL: TextView = rootPOV.findViewById(R.id.timeLBL)
            val venueLBL: TextView = rootPOV.findViewById(R.id.venueLBL)

            val favoriteBTN: ImageButton = rootPOV.findViewById(R.id.favoriteBTN)
        }
    }
}
