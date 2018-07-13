package com.example.nishant.genericx.view.eventlist

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.nishant.genericx.R
import com.example.nishant.genericx.view.eventlist.filter.BaseFragmentListener
import kotlinx.android.synthetic.main.fragment_event_details.view.*

/**
 * Created by AnEnigmaticBug on 13/7/18.
 */

class EventDetailsFragment : Fragment() {

    private lateinit var listener: BaseFragmentListener

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if(context is BaseFragmentListener) {
            listener = context
        }
        else {
            throw ClassCastException("I expect a listener bro.")
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootPOV = inflater.inflate(R.layout.fragment_event_details, container, false)

        setupLabels(rootPOV)
        rootPOV.closeBTN.setOnClickListener {
            listener.removeCurrentFragment()
        }

        return rootPOV
    }

    private fun setupLabels(rootPOV: View) {
        arguments?.let { arguments ->
            rootPOV.nameLBL.text = arguments.getString("EVENT_NAME")
            rootPOV.categoryLBL.text = arguments.getString("EVENT_CATEGORY")
            rootPOV.dateLBL.text = arguments.getString("EVENT_DATE")
            rootPOV.timeLBL.text = arguments.getString("EVENT_TIME")
            rootPOV.venueLBL.text = arguments.getString("EVENT_VENUE")
            rootPOV.aboutLBL.text = arguments.getString("EVENT_ABOUT", "-")
            rootPOV.rulesLBL.text = arguments.getString("EVENT_RULES", "-")
        }
    }
}