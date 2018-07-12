package com.example.nishant.genericx.util

import android.support.v4.content.ContextCompat
import android.widget.ImageButton
import org.threeten.bp.*

/**
 * Created by AnEnigmaticBug on 26/6/18.
 */

/**
 * Represents numbers from 0-99 in the double digit form. For eg: 1 becomes "01" 81 becomes "81".
 * */
fun doubleDigitify(x: Int): String = when {
        x < 0       -> throw IllegalArgumentException("doubleDigitify received negative argument")
        x in 0..9   -> "0$x"
        x in 10..99 -> x.toString()
        else        -> throw IllegalArgumentException("doubleDigitify received >2 digit argument")
}

/**
 * Represents date in the format dd-mm-yyyy
 * */
fun LocalDate.prettyString() = "${doubleDigitify(dayOfMonth)}-${doubleDigitify(monthValue)}-$year"

/**
 * Represents time in the format hh:mm
 * */
fun LocalTime.prettyString() = this.toString()

/**
 * Checks whether the LocalDateTime and @param localDate refer to the same day.
 * */
fun LocalDateTime.isTheSameDay(localDate: LocalDate) = this.toLocalDate() == localDate

/**
 * Returns the modulo of two numbers. Remainders can be negative making them unsuitable for use with
 * negative numbers. Modulo is always position.
 *
 * (-2).modulo(9) == 7
 * */
fun Int.modulo(n: Int): Int {
    val r = this.rem(n)
    return if(r < 0) r + n else r
}

fun ImageButton.setTint(colorResId: Int) {
    setColorFilter(ContextCompat.getColor(this.context, colorResId), android.graphics.PorterDuff.Mode.SRC_IN)
}