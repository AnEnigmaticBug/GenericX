package com.example.nishant.genericx.di

import com.example.nishant.genericx.viewmodel.eventlist.filter.FilterMainMenuViewModel
import com.example.nishant.genericx.viewmodel.eventlist.EventListViewModel
import com.example.nishant.genericx.viewmodel.eventlist.filter.CategoryListViewModel
import com.example.nishant.genericx.viewmodel.eventlist.filter.FilterItemListViewModel
import com.example.nishant.genericx.viewmodel.eventlist.filter.VenueListViewModel
import dagger.Component
import javax.inject.Singleton

/**
 * Created by AnEnigmaticBug on 26/6/18.
 */

@Singleton
@Component(modules = [AppModule::class])
abstract class AppComponent {

    abstract fun inject(eventListViewModel: EventListViewModel)

    abstract fun inject(eventFilterMainMenuViewModel: FilterMainMenuViewModel)
    abstract fun inject(categoryListViewModel: CategoryListViewModel)
    abstract fun inject(venueListViewModel: VenueListViewModel)
}