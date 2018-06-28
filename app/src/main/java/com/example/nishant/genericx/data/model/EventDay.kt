package com.example.nishant.genericx.data.model

import com.example.nishant.genericx.util.doubleDigitify
import java.util.*

/**
 * Created by AnEnigmaticBug on 27/6/18.
 */

/**
 * @property datetime is initialized with the Date.time value of the EventDay's beginning. It is the
 * number of milliseconds elapsed since January 1,1970. For eg: 1534464000000 is the value used to
 * represent 17th Aug 2018.
 *
 * Using an enum makes it simpler to use the filter and increases type-safety.
 * */
enum class EventDay(val datetime: Date) {
    Day1(Date(1541030400000)),
    Day2(Date(1541116800000)),
    Day3(Date(1541203200000));

    /**
     * Converts to form suitable for display in UI as PageTitle.
     * */
    fun prettyString(): String {
        val monthPart = when(this.datetime.month) {
            1    -> "January"
            2    -> "February"
            3    -> "March"
            4    -> "April"
            5    -> "May"
            6    -> "June"
            7    -> "July"
            8    -> "August"
            9    -> "September"
            10   -> "October"
            11   -> "November"
            else -> "December"
        }
        return "$monthPart ${doubleDigitify(this.datetime.date)}"
    }
}