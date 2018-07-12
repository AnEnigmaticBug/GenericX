package com.example.nishant.genericx.data.model

import com.example.nishant.genericx.util.doubleDigitify
import org.threeten.bp.LocalDate
import org.threeten.bp.Month

/**
 * Created by AnEnigmaticBug on 27/6/18.
 */

/**
 * Using an enum makes it simpler to use the event filter and increases type-safety.
 * */
enum class EventDay(val date: LocalDate) {
    Day1(LocalDate.of(2018, Month.OCTOBER, 31)),
    Day2(LocalDate.of(2018, Month.NOVEMBER, 1)),
    Day3(LocalDate.of(2018, Month.NOVEMBER, 2));

    /**
     * Converts to form suitable for display in UI as PageTitle.
     * */
    fun prettyString(): String {
        val monthPart = when(this.date.month) {
            Month.JANUARY   -> "January"
            Month.FEBRUARY  -> "February"
            Month.MARCH     -> "March"
            Month.APRIL     -> "April"
            Month.MAY       -> "May"
            Month.JUNE      -> "June"
            Month.JULY      -> "July"
            Month.AUGUST    -> "August"
            Month.SEPTEMBER -> "September"
            Month.OCTOBER   -> "October"
            Month.NOVEMBER  -> "November"
            Month.DECEMBER  -> "December"
        }
        return "$monthPart ${doubleDigitify(this.date.dayOfMonth)}"
    }
}