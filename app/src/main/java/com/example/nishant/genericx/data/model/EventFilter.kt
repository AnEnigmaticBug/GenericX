package com.example.nishant.genericx.data.model

import java.util.*

/**
 * Created by AnEnigmaticBug on 26/6/18.
 */

/**
 * This is used to filter events received from the repository. By default it allows all events.
 * */
class EventFilter(val categories: List<EventCategory> = EventCategory.values().toList(),
                  val venues: List<Venue> = Venue.values().toList(),
                  val days: List<Day> = Day.values().toList(),
                  val isFavorite: Boolean = false,
                  val isOngoing: Boolean = false) {

    /**
     * @property time is the Date.time value of the Day's beginning. It is the number of milliseconds
     * elapsed since January 1, 1970. For eg: 1534464000 is the value to represent 17th Aug 2018.
     *
     * Using an enum makes it simpler to use the filter and increases type-safety. The time property
     * allows for easy conversion to date.
     * */
    enum class Day(val time: Long) {
        Day1(1234567890),
        Day2(2345678910),
        Day3(3456789120)
    }

    companion object {

        private const val msInOneDay = 86400000L

        fun satisfiesEvent(event: Event, filter: EventFilter): Boolean {
            return event.category in filter.categories &&
                    event.venue in filter.venues &&
                    event.happensOnOneOfDays(filter.days) &&
                    event.satisfiesFavoriteCondition(filter.isFavorite) &&
                    event.satisfiesOngoingCondition(filter.isOngoing)
        }

        /**
         * Tells us if the event happened on one of the days in @param days.
         * */
        private fun Event.happensOnOneOfDays(days: List<Day>) =
                days.map { this.datetime.time in LongRange(it.time, it.time + msInOneDay) }.contains(true)

        private fun Event.satisfiesFavoriteCondition(condition: Boolean) =
                when(condition) {
                    true  -> this.isFavorite
                    false -> true
                }

        private fun Event.satisfiesOngoingCondition(condition: Boolean) =
                when(condition) {
                    true  -> this.datetime.time < Date().time
                    false -> true
                }

    }
}