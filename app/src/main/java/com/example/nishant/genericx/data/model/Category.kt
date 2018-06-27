package com.example.nishant.genericx.data.model

/**
* Created by AnEnigmaticBug on 26/6/18.
*/

enum class Category {
    Category1,
    Category2,
    Category3;

    /**
     * Converts to a form suitable for display in UI as PageTitle.
     * */
    fun prettyString() = when(this) {
        Category1 -> "Dance"
        Category2 -> "Misc"
        Category3 -> "Drama"
    }
}