package com.example.nishant.genericx.view.eventlist.filter

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.nishant.genericx.R

/**
 * Created by AnEnigmaticBug on 29/6/18.
 */

class EventSearchFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_event_search, container, false)
    }
}