package com.example.nishant.genericx.data.repository

import com.example.nishant.genericx.data.database.EventDao
import com.example.nishant.genericx.data.model.Event
import com.example.nishant.genericx.data.model.EventFilter
import com.example.nishant.genericx.data.model.UserPreferences
import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers

/**
* Created by AnEnigmaticBug on 26/6/18.
*/

/**
 * This repository is for use till I get Firebase(or REST API's; doesn't matter) to work. This is
 * obviously a purely offline repository. It is NOT going to be used in the final app.
 * */

class RoomRepository(private val eventDao: EventDao) : EventRepository {

    override val events: Flowable<List<Event>> =
            eventDao.getAllEvents().subscribeOn(Schedulers.io())

    override fun getEventById(eventId: String): Flowable<Event> =
            eventDao.getEventById(eventId).subscribeOn(Schedulers.io())

    override fun getFavorites(): Flowable<List<Event>> =
            eventDao.getFavorites().subscribeOn(Schedulers.io())

    override fun makeFavorite(eventId: String) {
        setFavorite(eventId, true)
    }

    override fun undoFavorite(eventId: String) {
        setFavorite(eventId, false)
    }

    private fun setFavorite(eventId: String, status: Boolean) {
        eventDao.getEventById(eventId)
                .subscribeOn(Schedulers.io())
                .take(1)
                .map { Event(it.id, it.name, it.description, it.category, it.venue, it.datetime, status) }
                .subscribe { eventDao.update(it) }
    }

    /**
     * In real life, this would use SharedPreferences. But for now it is hard-coded.
     * */
    override fun getUserPreferences(): Flowable<UserPreferences> = Flowable.just(UserPreferences(EventFilter()))

    override fun setUserPreferences(userPreferences: UserPreferences) {
        //Set shared preferences.
    }
}