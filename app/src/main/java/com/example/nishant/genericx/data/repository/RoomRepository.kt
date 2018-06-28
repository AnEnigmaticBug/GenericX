package com.example.nishant.genericx.data.repository

import com.example.nishant.genericx.data.database.EventDao
import com.example.nishant.genericx.data.model.*
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import java.util.*

/**
* Created by AnEnigmaticBug on 26/6/18.
*/

/**
 * This repository is for use till I get Firebase(or REST API's; doesn't matter) to work. This is
 * obviously a purely offline repository. It is NOT going to be used in the final app.
 * */

class RoomRepository(private val eventDao: EventDao) : EventRepository {

    //Just for testing purposes. Remove once no longer needed.
    init {
        Observable.just(1)
                .subscribeOn(Schedulers.io())
                .subscribe {
                    eventDao.insert(Event(name = "Street Dance Elims", venue = Venue.Venue1, category = Category.Category1, datetime = Date(1541030400000), isFavorite = false, description = ""))
                    eventDao.insert(Event(name = "Portuguese Embassy", venue = Venue.Venue1, category = Category.Category2, datetime = Date(1541116800000), isFavorite = false, description = ""))
                    eventDao.insert(Event(name = "Inauguration Ceremony", venue = Venue.Venue1, category = Category.Category2, datetime = Date(1541030400000), isFavorite = true, description = ""))
                    eventDao.insert(Event(name = "Free Jam", venue = Venue.Venue1, category = Category.Category3, datetime = Date(1541116800000), isFavorite = false, description = ""))
                    eventDao.insert(Event(name = "Hogathon", venue = Venue.Venue1, category = Category.Category2, datetime = Date(1541116800000), isFavorite = false, description = ""))
                    eventDao.insert(Event(name = "The Night's Watch", venue = Venue.Venue1, category = Category.Category2, datetime = Date(), isFavorite = true, description = ""))
                    eventDao.insert(Event(name = "Stage Play 1", venue = Venue.Venue1, category = Category.Category3, datetime = Date(1541030400000), isFavorite = true, description = ""))
                    eventDao.insert(Event(name = "Stage Play 2", venue = Venue.Venue1, category = Category.Category3, datetime = Date(1541203200000), isFavorite = false, description = ""))
                    eventDao.insert(Event(name = "Street Play", venue = Venue.Venue1, category = Category.Category3, datetime = Date(1541030400000), isFavorite = false, description = ""))
                    eventDao.insert(Event(name = "Speed Scrabble 1", venue = Venue.Venue1, category = Category.Category2, datetime = Date(1541203200000), isFavorite = false, description = ""))
                    eventDao.insert(Event(name = "Exposure", venue = Venue.Venue1, category = Category.Category1, datetime = Date(1541203200000), isFavorite = true, description = ""))
                }
    }

    override val events: Flowable<List<Event>> =
            eventDao.getAllEvents().subscribeOn(Schedulers.io())

    override fun getEventById(eventId: String): Flowable<Event> =
            eventDao.getEventById(eventId).subscribeOn(Schedulers.io())

    override val favorites: Flowable<List<Event>> =
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
    override val userPreferences: Flowable<UserPreferences> =
            Flowable.just(UserPreferences(EventFilter(), Criterion.EventDay))

    override fun setUserPreferences(userPreferences: UserPreferences) {
        //Set shared preferences.
    }
}