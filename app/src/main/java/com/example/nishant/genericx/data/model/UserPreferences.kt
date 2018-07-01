package com.example.nishant.genericx.data.model

/**
 * Created by AnEnigmaticBug on 26/6/18.
 */

/**
 * It stores all the settings which the users have tweaked such as event filters, notification
 * settings(not implemented yet) etc.
 *
 * The default parameter values reflect the default user preferences.
 * */
data class UserPreferences(val eventFilter: EventFilter = EventFilter(), val criterion: Criterion = Criterion.EventDay)