package com.example.nishant.genericx.data.repository

import com.example.nishant.genericx.data.model.Event
import com.example.nishant.genericx.data.model.UserPreferences
import io.reactivex.Flowable

/**
* Created by AnEnigmaticBug on 26/6/18.
*/

interface EventRepository {

    val events: Flowable<List<Event>>

    fun getEventById(eventId: String): Flowable<Event>

    fun getFavorites(): Flowable<List<Event>>

    fun makeFavorite(eventId: String)

    fun undoFavorite(eventId: String)

    fun getUserPreferences(): Flowable<UserPreferences>

    fun setUserPreferences(userPreferences: UserPreferences)
}