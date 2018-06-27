package com.example.nishant.genericx.data.model

/**
 * Created by AnEnigmaticBug on 27/6/18.
 */

/**
 * It represents the primary criterion by which the events are sorted. EventDay indicates that each
 * page should show events occurring on a particular date and Category indicates that each page
 * should show events of the same category.
 * */
enum class Criterion {
    EventDay, Category
}