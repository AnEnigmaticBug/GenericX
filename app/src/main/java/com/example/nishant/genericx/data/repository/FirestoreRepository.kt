package com.example.nishant.genericx.data.repository

import com.example.nishant.genericx.data.LocalStorage
import com.example.nishant.genericx.data.database.EventDao
import com.example.nishant.genericx.data.firebase.FirestoreDatabase
import com.example.nishant.genericx.data.model.Event
import com.example.nishant.genericx.data.model.UserPreferences
import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers

/**
 * Created by AnEnigmaticBug on 3/7/18.
 */

class FirestoreRepository(private val eventDao: EventDao, private val localStorage: LocalStorage, private val firestoreDb: FirestoreDatabase) : EventRepository {

    init {
        firestoreDb.getEvents()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .take(1)
                .subscribe {
                    it.forEach {
                        if(eventDao.eventExists(it.id)) {
                            getEventById(it.id)
                                    .take(1)
                                    .subscribe { dbEvent ->
                                        eventDao.update(Event(
                                                it.id,
                                                it.name,
                                                it.description,
                                                it.category,
                                                it.venue,
                                                it.datetime,
                                                dbEvent.isFavorite
                                        ))
                                    }
                        }
                        else {
                            eventDao.insert(it)
                        }
                    }
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

    override val userPreferences: Flowable<UserPreferences> = localStorage.getUserPreferences()

    override fun setUserPreferences(userPreferences: UserPreferences) {
        localStorage.setUserPreferences(userPreferences)
    }
}