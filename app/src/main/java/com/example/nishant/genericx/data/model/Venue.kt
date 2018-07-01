package com.example.nishant.genericx.data.model

/**
* Created by AnEnigmaticBug on 26/6/18.
*/

enum class Venue {
    Venue1,
    Venue2,
    Venue3,
    Venue4;

    /**
     * Converts to a form suitable for display in UI.
     * */
    fun prettyString() = when(this) {
        Venue1 -> "Audi"
        Venue2 -> "FD2 QT"
        Venue3 -> "M Lawns"
        Venue4 -> "SR Ground"
    }
}