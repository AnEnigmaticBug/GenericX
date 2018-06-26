package com.example.nishant.genericx.data.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import com.example.nishant.genericx.data.model.Event

/**
* Created by AnEnigmaticBug on 26/6/18.
*/

@Database(entities = [Event::class], version = 1, exportSchema = false)
@TypeConverters(TypeConverter::class)
abstract class EventDatabase : RoomDatabase() {

    abstract fun eventDao(): EventDao
}