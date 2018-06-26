package com.example.nishant.genericx.data.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey
import java.util.*

/**
* Created by AnEnigmaticBug on 26/6/18.
*/

@Entity(tableName = "events")
data class Event(@PrimaryKey val id: String = UUID.randomUUID().toString(),
                 val name: String,
                 val description: String,
                 val category: EventCategory,
                 val venue: Venue,
                 val datetime: Date,
                 val isFavorite: Boolean = false)