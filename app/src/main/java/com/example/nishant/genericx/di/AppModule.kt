package com.example.nishant.genericx.di

import android.arch.persistence.room.Room
import android.content.Context
import com.example.nishant.genericx.data.LocalStorage
import com.example.nishant.genericx.data.database.AppDatabase
import com.example.nishant.genericx.data.database.EventDao
import com.example.nishant.genericx.data.repository.EventRepository
import com.example.nishant.genericx.data.repository.RoomRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by AnEnigmaticBug on 26/6/18.
 */

@Module
class AppModule(private val context: Context) {

    @Provides @Singleton
    fun providesEventRepository(noteDao: EventDao, localStorage: LocalStorage): EventRepository = RoomRepository(noteDao, localStorage)

    @Provides @Singleton
    fun providesLocalStorage(context: Context) = LocalStorage(context)

    @Provides @Singleton
    fun providesEventDao(appDatabase: AppDatabase): EventDao = appDatabase.eventDao()

    @Provides @Singleton
    fun providesAppDatabase(context: Context): AppDatabase =
            Room.databaseBuilder(context, AppDatabase::class.java, "genericx.db").build()

    @Provides @Singleton
    fun providesContext(): Context = context
}