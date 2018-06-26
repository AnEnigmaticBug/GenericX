package com.example.nishant.genericx.data.database

import android.arch.persistence.room.*
import com.example.nishant.genericx.data.model.Event
import io.reactivex.Flowable

/**
* Created by AnEnigmaticBug on 26/6/18.
*/

@Dao
interface EventDao {

    @Query("SELECT * FROM events")
    fun getAllEvents(): Flowable<List<Event>>

    @Query("SELECT * FROM events WHERE id = :id")
    fun getEventById(id: String): Flowable<Event>

    @Query("SELECT * FROM events WHERE isFavorite = 1")
    fun getFavorites(): Flowable<List<Event>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(event: Event)

    @Update
    fun update(event: Event)

    @Delete
    fun delete(event: Event)
}