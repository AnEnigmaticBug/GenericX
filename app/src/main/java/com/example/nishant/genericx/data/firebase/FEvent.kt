package com.example.nishant.genericx.data.firebase

import com.example.nishant.genericx.data.model.Category
import com.example.nishant.genericx.data.model.Venue
import java.util.*

/**
 * Created by AnEnigmaticBug on 3/7/18.
 */

/**
 * This is to retrieve data from Firebase. Firebase isn't concerned with favoriting. Also, the id
 * field is omitted because the id in FireStore is the document key. It is not stored in the fields.
 * */
data class FEvent(val name: String, val description: String, val category: Category, val venue: Venue, val datetime: Date)
