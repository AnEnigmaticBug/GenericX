package com.example.nishant.genericx.data.repository

import com.example.nishant.genericx.data.LocalStorage
import com.example.nishant.genericx.data.database.EventDao
import com.example.nishant.genericx.data.model.*
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import org.threeten.bp.LocalDateTime
import org.threeten.bp.Month
import java.util.*

/**
* Created by AnEnigmaticBug on 26/6/18.
*/

/**
 * This repository is for use till I get Firebase(or REST API's; doesn't matter) to work. This is
 * obviously a purely offline repository. It is NOT going to be used in the final app.
 * */

class RoomRepository(private val eventDao: EventDao, private val localStorage: LocalStorage) : EventRepository {

    //Just for testing purposes. Remove once no longer needed.
    init {
        Observable.just(1)
                .subscribeOn(Schedulers.io())
                .subscribe {
                    eventDao.insert(Event(name = "Street Dance Elims", venue = Venue.Audi, category = Category.Dance, datetime = LocalDateTime.of(2018, Month.OCTOBER, 31, 23, 30), isFavorite = false, description = ""))
                    eventDao.insert(Event(name = "Portuguese Embassy", venue = Venue.FD2QT, category = Category.Misc, datetime = LocalDateTime.of(2018, Month.OCTOBER, 31, 18, 0), isFavorite = false, description = ""))
                    eventDao.insert(Event(name = "Inauguration Ceremony", venue = Venue.Audi, category = Category.Misc, datetime = LocalDateTime.of(2018, Month.OCTOBER, 31, 19, 0), isFavorite = true, description = ""))
                    eventDao.insert(Event(name = "Free Jam", venue = Venue.FD2QT, category = Category.Drama, datetime = LocalDateTime.of(2018, Month.OCTOBER, 31, 22, 0), isFavorite = false, description = ""))
                    eventDao.insert(Event(name = "Hogathon", venue = Venue.FD2QT, category = Category.Misc, datetime = LocalDateTime.of(2018, Month.OCTOBER, 31, 22, 30), isFavorite = false, description = ""))
                    eventDao.insert(Event(name = "The Night's Watch", venue = Venue.Audi, category = Category.Misc, datetime = LocalDateTime.of(2018, Month.OCTOBER, 31, 23, 0), isFavorite = true, description = ""))
                    eventDao.insert(Event(name = "Stage Play 1", venue = Venue.MLawns, category = Category.Drama, datetime = LocalDateTime.of(2018, Month.NOVEMBER, 1, 8, 30), isFavorite = true, description = ""))
                    eventDao.insert(Event(name = "Stage Play 2", venue = Venue.SRGround, category = Category.Drama, datetime = LocalDateTime.of(2018, Month.NOVEMBER, 2, 13, 0), isFavorite = false, description = ""))
                    eventDao.insert(Event(name = "Street Play", venue = Venue.SRGround, category = Category.Drama, datetime = LocalDateTime.of(2018, Month.NOVEMBER, 1, 10, 0), isFavorite = false, description = ""))
                    eventDao.insert(Event(name = "Speed Scrabble 1", venue = Venue.Audi, category = Category.Misc, datetime = LocalDateTime.of(2018, Month.NOVEMBER, 1, 10, 0), isFavorite = false, description = ""))
                    eventDao.insert(Event(name = "Exposure", venue = Venue.MLawns, category = Category.Dance, datetime = LocalDateTime.of(2018, Month.NOVEMBER, 2, 10, 0), isFavorite = true, description = ""))
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
    override val userPreferences: Flowable<UserPreferences> = localStorage.getUserPreferences()

    override fun setUserPreferences(userPreferences: UserPreferences) {
        localStorage.setUserPreferences(userPreferences)
    }
}