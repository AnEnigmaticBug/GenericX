package com.example.nishant.genericx.data.model

import java.util.*

/**
* Created by AnEnigmaticBug on 26/6/18.
*/

data class Event(val id: String = UUID.randomUUID().toString(),
                 val name: String,
                 val description: String,
                 val category: EventCategory,
                 val venue: Venue,
                 val datetime: Date,
                 val isFavorite: Boolean = false)