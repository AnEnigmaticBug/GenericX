package com.example.nishant.genericx.data.firebase

import com.example.nishant.genericx.data.model.Category
import com.example.nishant.genericx.data.model.Event
import com.example.nishant.genericx.data.model.Venue
import com.google.firebase.firestore.FirebaseFirestore
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.subjects.BehaviorSubject
import java.util.*

/**
 * Created by AnEnigmaticBug on 3/7/18.
 */

class FirestoreDatabase {

    private val eventsCollectionPath = "events"

    private val db = FirebaseFirestore.getInstance()

    private val subject = BehaviorSubject.create<List<Event>>()


    init {
        //populateFireStore()
        db.collection(eventsCollectionPath)
                .addSnapshotListener { s, _ ->
                    val events = arrayListOf<Event>()
                    s?.documents?.forEach {
                        events.add(Event(
                                it.id,
                                it.getString("name")!!,
                                it.getString("description")!!,
                                Category.valueOf(it.getString("category")!!),
                                Venue.valueOf(it.getString("venue")!!),
                                it.getDate("datetime")!!))
                    }
                    subject.onNext(events)
                }
    }

    /**
     * To be deleted once real data is put in Firestore.
     * */
    private fun populateFireStore() {
        val times = listOf(
                Date().also { it.date += 0 },
                Date().also { it.date += 1 },
                Date().also { it.date += 2 }
        ).map { it.also { it.minutes = 0; it.hours = 18 } }

        val events = listOf(
                FEvent("Portuguese Embassy", "", Category.Misc, Venue.Rotunda, times[0]),
                FEvent("Inauguration Ceremony", "", Category.Misc, Venue.Audi, times[0]),
                FEvent("Free Jam", "", Category.Music, Venue.MLawns, times[0]),
                FEvent("Hogathon", "", Category.Misc, Venue.MLawns, times[0]),
                FEvent("The Night's Watch", "", Category.Misc, Venue.FD2QT, times[0]),
                FEvent("Street Dance Elims", "", Category.Dance, Venue.Rotunda, times[0]),
                FEvent("Antakshari", "", Category.Music, Venue.FD2QT, times[0]),
                FEvent("Stage Play", "", Category.Drama, Venue.Audi, times[1]),
                FEvent("Street Play", "", Category.Drama, Venue.FD2QT, times[1]),
                FEvent("Exposure", "", Category.Photography, Venue.FD22158, times[1]),
                FEvent("Andholika Elims", "", Category.Music, Venue.FD22158, times[1]),
                FEvent("Desert Duels Elims", "", Category.Dance, Venue.Rotunda, times[1]),
                FEvent("Cocktail", "", Category.Oratory, Venue.NAB6105, times[1]),
                FEvent("60 Seconds", "", Category.Misc, Venue.MLawns, times[1]),
                FEvent("Turncoat", "", Category.Oratory, Venue.NAB6105, times[1]),
                FEvent("Classic Prof Show", "", Category.Misc, Venue.Audi, times[1]),
                FEvent("Escape Room", "", Category.Misc, Venue.FD22158, times[1]),
                FEvent("Indie Night", "", Category.Music, Venue.Audi, times[1]),
                FEvent("Treasure Hunt", "", Category.Misc, Venue.FD2QT, times[2]),
                FEvent("Tarang 1", "", Category.Music, Venue.Audi, times[2]),
                FEvent("Taboo", "", Category.Oratory, Venue.FD22158, times[2]),
                FEvent("Poetry Slam", "", Category.Oratory, Venue.NAB6105, times[2])
        )

        events.forEach {
            db.collection(eventsCollectionPath)
                    .add(it)
        }
    }

    fun getEvents(): Flowable<List<Event>> {
        return subject.toFlowable(BackpressureStrategy.BUFFER)
    }
}