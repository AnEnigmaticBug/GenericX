package com.example.nishant.genericx.view

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
import com.example.nishant.genericx.util.formattedDate
import com.example.nishant.genericx.util.formattedTime
import com.example.nishant.genericx.viewmodel.eventlist.EventListViewModel
import kotlinx.android.synthetic.main.activity_event_list.*
import java.util.*

class EventListActivity : AppCompatActivity() {

    private lateinit var viewModel: EventListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_list)

        viewModel = ViewModelProviders.of(this).get(EventListViewModel::class.java)

        viewModel.criterion.observe(this, Observer {
            if(it != null) {
                eventsRCY.adapter = when(it) {
                    Criterion.EventDay -> EventsAdapterByEventDay()
                    Criterion.Category -> EventsAdapterByCategory()
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
    }

    interface HasEvents {

        var events: List<Event>
    }

    class EventsAdapterByEventDay : RecyclerView.Adapter<EventsAdapterByEventDay.EventsVHolder>(), HasEvents {

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
            holder.timeLBL.text = event.datetime.formattedTime()
            holder.venueLBL.text = event.venue.displayValue
            holder.categoryLBL.text = event.category.prettyString()
        }

        class EventsVHolder(val rootPOV: View) : RecyclerView.ViewHolder(rootPOV) {

            val nameLBL: TextView = rootPOV.findViewById(R.id.nameLBL)
            val timeLBL: TextView = rootPOV.findViewById(R.id.timeLBL)
            val venueLBL: TextView = rootPOV.findViewById(R.id.venueLBL)
            val categoryLBL: TextView = rootPOV.findViewById(R.id.categoryLBL)

            val favoriteBTN: ImageButton = rootPOV.findViewById(R.id.favoriteBTN)
        }
    }

    class EventsAdapterByCategory : RecyclerView.Adapter<EventsAdapterByCategory.EventsVHolder>(), HasEvents {

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
            holder.dateLBL.text = event.datetime.formattedDate()
            holder.timeLBL.text = event.datetime.formattedTime()
            holder.venueLBL.text = event.venue.displayValue
        }

        private fun extractDate(datetime: Date): String {
            return "${datetime.date}"
        }

        class EventsVHolder(val rootPOV: View) : RecyclerView.ViewHolder(rootPOV) {

            val nameLBL: TextView = rootPOV.findViewById(R.id.nameLBL)
            val dateLBL: TextView = rootPOV.findViewById(R.id.dateLBL)
            val timeLBL: TextView = rootPOV.findViewById(R.id.timeLBL)
            val venueLBL: TextView = rootPOV.findViewById(R.id.venueLBL)

            val favoriteBTN: ImageButton = rootPOV.findViewById(R.id.favoriteBTN)
        }
    }
}
