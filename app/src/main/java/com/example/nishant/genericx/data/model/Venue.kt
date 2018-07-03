package com.example.nishant.genericx.data.model

/**
* Created by AnEnigmaticBug on 26/6/18.
*/

enum class Venue {
    Audi,
    FD2QT,
    MLawns,
    SRGround,
    Rotunda,
    FD22158,
    NAB6105;

    /**
     * Converts to a form suitable for display in UI.
     * */
    fun prettyString() = when(this) {
        Audi     -> "Audi"
        FD2QT    -> "FD2 QT"
        MLawns   -> "M Lawns"
        SRGround -> "SR Ground"
        Rotunda  -> "Rotunda"
        FD22158  -> "FD2 2158"
        NAB6105  -> "NAB 6105"
    }
}