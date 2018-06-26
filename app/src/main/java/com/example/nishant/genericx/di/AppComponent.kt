package com.example.nishant.genericx.di

import dagger.Component
import javax.inject.Singleton

/**
 * Created by AnEnigmaticBug on 26/6/18.
 */

@Singleton
@Component(modules = [AppModule::class])
abstract class AppComponent {


}