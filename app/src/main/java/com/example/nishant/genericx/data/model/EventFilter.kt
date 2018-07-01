package com.example.nishant.genericx.data.model

import com.example.nishant.genericx.util.Constants

/**
 * Created by AnEnigmaticBug on 26/6/18.
 */

/**
 * This is used to filter events received from the repository. By default it allows all events.
 * */
class EventFilter(val categories: List<Category> = Category.values().toList(),
                  val venues: List<Venue> = Venue.values().toList(),
                  val days: List<EventDay> = EventDay.values().toList(),
                  val isFavorite: Boolean = false,
                  val isOngoing: Boolean = false) {
    
    companion object {

        fun isSatisfiedByEvent(event: Event, filter: EventFilter): Boolean {
            return event.category in filter.categories &&
                    event.venue in filter.venues &&
                    event.happensOnOneOfDays(filter.days) &&
                    event.satisfiesFavoriteCondition(filter.isFavorite) &&
                    event.satisfiesOngoingCondition(filter.isOngoing)
        }

        /**
         * Tells us if the event happened on one of the days in @param days.
         * */
        private fun Event.happensOnOneOfDays(days: List<EventDay>) =
                days.map { this.datetime.time in LongRange(it.datetime.time, it.datetime.time + Constants.msInOneDay) }.contains(true)

        private fun Event.satisfiesFavoriteCondition(condition: Boolean) =
                when(condition) {
                    true  -> this.isFavorite
                    false -> true
                }

        private fun Event.satisfiesOngoingCondition(condition: Boolean) =
                when(condition) {
                    true  -> this.datetime.time < java.util.Date().time
                    false -> true
                }

    }
}