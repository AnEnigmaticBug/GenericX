package com.example.nishant.genericx.data.database

import android.arch.persistence.room.TypeConverter
import com.example.nishant.genericx.data.model.EventCategory
import com.example.nishant.genericx.data.model.Venue
import java.util.*

/**
* Created by AnEnigmaticBug on 26/6/18.
*/

class TypeConverter {

    @TypeConverter
    fun toDate(time: Long): Date = Date(time)

    @TypeConverter
    fun toLong(date: Date): Long = date.time

    @TypeConverter
    fun toEventCategory(str: String): EventCategory = EventCategory.valueOf(str)

    @TypeConverter
    fun toString(eventCategory: EventCategory): String = eventCategory.toString()

    @TypeConverter
    fun toVenue(str: String): Venue = Venue.valueOf(str)

    @TypeConverter
    fun toString(venue: Venue): String = venue.toString()
}