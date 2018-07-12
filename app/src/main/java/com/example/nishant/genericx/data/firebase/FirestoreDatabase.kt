package com.example.nishant.genericx.data.firebase

import com.example.nishant.genericx.data.model.Category
import com.example.nishant.genericx.data.model.Event
import com.example.nishant.genericx.data.model.EventDay
import com.example.nishant.genericx.data.model.Venue
import com.google.firebase.firestore.FirebaseFirestore
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.subjects.BehaviorSubject
import org.threeten.bp.Instant
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneId
import org.threeten.bp.ZoneOffset
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
        db.collection(eventsCollectionPath).orderBy("datetime")
                .addSnapshotListener { s, _ ->
                    val events = arrayListOf<Event>()
                    s?.documents?.forEach {
                        events.add(Event(
                                it.id,
                                it.getString("name")!!,
                                it.getString("description")!!,
                                Category.valueOf(it.getString("category")!!),
                                Venue.valueOf(it.getString("venue")!!),
                                it.getDate("datetime")!!.toLocalDateTime()))
                    }
                    subject.onNext(events)
                }
    }

    private fun Date.toLocalDateTime(): LocalDateTime {
        //LocalDateTime.ofEpochSecond(this.time/1000, 0, ZoneOffset.of(ZoneId.systemDefault().id))
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(this.time), ZoneId.systemDefault())
    }

    /**
     * To be deleted once real data is put in Firestore.
     * */
    private fun populateFireStore() {
        val times = listOf(
                Date(EventDay.Day1.date.toEpochDay()*(24*3600*1000).toLong()),
                Date(EventDay.Day2.date.toEpochDay()*(24*3600*1000).toLong()),
                Date(EventDay.Day3.date.toEpochDay()*(24*3600*1000).toLong())

        )

        fun Date.copy() = (this.clone() as Date).also { it.minutes = 0 }

        val events = listOf(
                FEvent("Portuguese Embassy", "", Category.Misc, Venue.Rotunda, times[0].copy().also { it.hours = 18 }),
                FEvent("Inauguration Ceremony", "", Category.Misc, Venue.Audi, times[0].copy().also { it.hours = 19 }),
                FEvent("Free Jam", "", Category.Music, Venue.MLawns, times[0].copy().also { it.hours = 22 }),
                FEvent("Hogathon", "", Category.Misc, Venue.MLawns, times[0].copy().also { it.hours = 22; it.minutes = 30 }),
                FEvent("The Night's Watch", "", Category.Misc, Venue.FD2QT, times[0].copy().also { it.hours = 23 }),
                FEvent("Street Dance Elims", "", Category.Dance, Venue.Rotunda, times[0].copy().also { it.hours = 23; it.minutes = 30 }),
                FEvent("Antakshari", "", Category.Music, Venue.FD2QT, times[0].copy().also { it.hours = 23; it.minutes = 59 }),
                FEvent("Stage Play", "", Category.Drama, Venue.Audi, times[1].copy().also { it.hours = 8; it.minutes = 30 }),
                FEvent("Street Play", "", Category.Drama, Venue.FD2QT, times[1].copy().also { it.hours = 10 }),
                FEvent("Exposure", "", Category.Photography, Venue.FD22158, times[1].copy().also { it.hours = 10 }),
                FEvent("Andholika Elims", "", Category.Music, Venue.FD22158, times[1].copy().also { it.hours = 10 }),
                FEvent("Desert Duels Elims", "", Category.Dance, Venue.Rotunda, times[1].copy().also { it.hours = 13 }),
                FEvent("Cocktail", "", Category.Oratory, Venue.NAB6105, times[1].copy().also { it.hours = 14 }),
                FEvent("60 Seconds", "", Category.Misc, Venue.MLawns, times[1].copy().also { it.hours = 14 }),
                FEvent("Turncoat", "", Category.Oratory, Venue.NAB6105, times[1].copy().also { it.hours = 15 }),
                FEvent("Classic Prof Show", "", Category.Misc, Venue.Audi, times[1].copy().also { it.hours = 17 }),
                FEvent("Escape Room", "", Category.Misc, Venue.FD22158, times[1].copy().also { it.hours = 19 }),
                FEvent("Indie Night", "", Category.Music, Venue.Audi, times[1].copy().also { it.hours = 21; it.minutes = 30 }),
                FEvent("Treasure Hunt", "", Category.Misc, Venue.FD2QT, times[2].copy().also { it.hours = 1; it.minutes = 30 }),
                FEvent("Tarang 1", "", Category.Music, Venue.Audi, times[2].copy().also { it.hours = 1; it.minutes = 30 }),
                FEvent("Taboo", "", Category.Oratory, Venue.FD22158, times[2].copy().also { it.hours = 11 }),
                FEvent("Poetry Slam", "", Category.Oratory, Venue.NAB6105, times[2].copy().also { it.hours = 11; it.minutes = 30 })
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