package com.example.nishant.genericx.data.database

import android.arch.persistence.room.*
import com.example.nishant.genericx.data.model.Event
import io.reactivex.Flowable

/**
* Created by AnEnigmaticBug on 26/6/18.
*/

@Dao
interface EventDao {

    @Query("SELECT * FROM events ORDER BY category, datetime")
    fun getAllEvents(): Flowable<List<Event>>

    @Query("SELECT * FROM events WHERE id = :id")
    fun getEventById(id: String): Flowable<Event>

    @Query("SELECT COUNT(id) FROM events WHERE id = :id")
    fun eventExists(id: String): Boolean

    @Query("SELECT * FROM events WHERE isFavorite = 1 ORDER BY category, datetime")
    fun getFavorites(): Flowable<List<Event>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(event: Event)

    @Update
    fun update(event: Event)

    @Delete
    fun delete(event: Event)
}