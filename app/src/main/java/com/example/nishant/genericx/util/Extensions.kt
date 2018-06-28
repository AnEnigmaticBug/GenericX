package com.example.nishant.genericx.util

import android.support.v4.content.ContextCompat
import android.widget.ImageButton
import java.util.*

/**
 * Created by AnEnigmaticBug on 26/6/18.
 */

/**
 * Represents date in the format dd-mm-yyyy
 * */
fun Date.formattedDate() = "${doubleDigitify(date)}-${doubleDigitify(month)}-$year"

/**
 * Represents time in the format hh:mm
 * */
fun Date.formattedTime() = "${doubleDigitify(hours)}:${doubleDigitify(minutes)}"

/**
 * Represents numbers from 0-99 in the double digit form. For eg: 1 becomes "01" 81 becomes "81".
 * */
fun doubleDigitify(x: Int): String {
    if(x in 0..9) {
        return "0$x"
    }
    else if(x in 10..99) {
        return x.toString()
    }
    else if(x >= 100) {
        throw IllegalArgumentException("doubleDigitify received >2 digit argument")
    }
    else {
        throw IllegalArgumentException("doubleDigitify received negative argument")
    }
}

fun Date.isOnSameDay(date: Date) = this.date == date.date && this.month == date.month && this.year == this.year

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