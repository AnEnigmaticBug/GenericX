package com.example.nishant.genericx.data.database

import android.arch.persistence.room.TypeConverter
import com.example.nishant.genericx.data.model.Category
import com.example.nishant.genericx.data.model.Venue
import org.threeten.bp.LocalDateTime
import java.util.*

/**
* Created by AnEnigmaticBug on 26/6/18.
*/

class TypeConverter {

    @TypeConverter
    fun fromLongtoDate(time: Long): Date = Date(time)

    @TypeConverter
    fun fromStringtoLocalDateTime(str: String): LocalDateTime = LocalDateTime.parse(str)

    @TypeConverter
    fun fromLocalDateTimetoLong(dateTime: LocalDateTime): String = dateTime.toString()

    @TypeConverter
    fun fromStringtoEventCategory(str: String): Category = Category.valueOf(str)

    @TypeConverter
    fun fromEventCategorytoString(eventCategory: Category): String = eventCategory.toString()

    @TypeConverter
    fun fromStringtoVenue(str: String): Venue = Venue.valueOf(str)

    @TypeConverter
    fun fromVenuetoString(venue: Venue): String = venue.toString()
}