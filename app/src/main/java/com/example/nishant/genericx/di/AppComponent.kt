package com.example.nishant.genericx.di

import com.example.nishant.genericx.viewmodel.eventlist.EventListViewModel
import dagger.Component
import javax.inject.Singleton

/**
 * Created by AnEnigmaticBug on 26/6/18.
 */

@Singleton
@Component(modules = [AppModule::class])
abstract class AppComponent {

    abstract fun inject(eventListViewModel: EventListViewModel)
}