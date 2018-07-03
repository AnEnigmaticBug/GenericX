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