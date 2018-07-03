package com.example.nishant.genericx.data.model

/**
* Created by AnEnigmaticBug on 26/6/18.
*/

enum class Category {
    Dance,
    Misc,
    Drama,
    Photography,
    Oratory,
    Music;

    /**
     * Converts to a form suitable for display in UI as PageTitle.
     * */
    fun prettyString() = when(this) {
        Dance       -> "Dance"
        Misc        -> "Misc"
        Drama       -> "Drama"
        Photography -> "Photography"
        Oratory     -> "Oratory"
        Music       -> "Music"
    }
}