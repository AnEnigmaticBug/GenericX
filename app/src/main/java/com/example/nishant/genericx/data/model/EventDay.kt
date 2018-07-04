package com.example.nishant.genericx.data.model

import com.example.nishant.genericx.util.Constants
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
    Day1(Date(Date().time + 0*Constants.msInOneDay)),
    Day2(Date(Date().time + 1*Constants.msInOneDay)),
    Day3(Date(Date().time + 2*Constants.msInOneDay));

    /**
     * Converts to form suitable for display in UI as PageTitle.
     * */
    fun prettyString(): String {
        val monthPart = when(this.datetime.month) {
            0    -> "January"
            1    -> "February"
            2    -> "March"
            3    -> "April"
            4    -> "May"
            5    -> "June"
            6    -> "July"
            7    -> "August"
            8    -> "September"
            9    -> "October"
            10   -> "November"
            else -> "December"
        }
        return "$monthPart ${doubleDigitify(this.datetime.date)}"
    }
}