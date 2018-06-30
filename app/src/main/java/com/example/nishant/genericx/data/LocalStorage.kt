package com.example.nishant.genericx.data

import android.content.Context
import com.example.nishant.genericx.data.model.*
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.subjects.BehaviorSubject

/**
 * Created by AnEnigmaticBug on 29/6/18.
 */

class LocalStorage(val context: Context) {

    private val storageName = "user_preferences"

    private val subject = BehaviorSubject.create<UserPreferences>()

    fun setUserPreferences(userPreferences: UserPreferences) {
        context.getSharedPreferences(storageName, Context.MODE_PRIVATE).edit().also {
            it.putStringSet("CATEGORIES", userPreferences.eventFilter.categories.toStringSet())
            it.putStringSet("VENUES", userPreferences.eventFilter.venues.toStringSet())
            it.putStringSet("EVENT_DAYS", userPreferences.eventFilter.days.toStringSet())
            it.putBoolean("FAVORITE", userPreferences.eventFilter.isFavorite)
            it.putBoolean("ONGOING", userPreferences.eventFilter.isOngoing)
            it.putString("CRITERION", userPreferences.criterion.toString())
        }.apply()
        subject.onNext(userPreferences)
    }

    fun getUserPreferences(): Flowable<UserPreferences> {
        val sharedPref = context.getSharedPreferences(storageName, Context.MODE_PRIVATE)
        val userPref = UserPreferences(
                EventFilter(
                        sharedPref.getStringSet("CATEGORIES", Category.values().toStringSet()).map { Category.valueOf(it) },
                        sharedPref.getStringSet("VENUES", Venue.values().toStringSet()).map { Venue.valueOf(it) },
                        sharedPref.getStringSet("EVENT_DAYS", EventDay.values().toStringSet()).map { EventDay.valueOf(it) },
                        sharedPref.getBoolean("FAVORITE", false),
                        sharedPref.getBoolean("ONGOING", false)
                ),
                Criterion.valueOf(sharedPref.getString("CRITERION", Criterion.EventDay.toString()))
        )
        subject.onNext(userPref)
        return subject.toFlowable(BackpressureStrategy.BUFFER)
    }

    private fun <T> List <T>.toStringSet() = this.map { it.toString() }.toSet()

    private fun <T> Array<T>.toStringSet() = this.map { it.toString() }.toSet()
}