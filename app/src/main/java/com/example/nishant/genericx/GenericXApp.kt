package com.example.nishant.genericx

import android.app.Application
import com.example.nishant.genericx.data.firebase.FEvent
import com.example.nishant.genericx.data.model.Category
import com.example.nishant.genericx.data.model.Event
import com.example.nishant.genericx.data.model.Venue
import com.example.nishant.genericx.di.AppComponent
import com.example.nishant.genericx.di.AppModule
import com.example.nishant.genericx.di.DaggerAppComponent
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*

/**
 * Created by AnEnigmaticBug on 26/6/18.
 */

class GenericXApp : Application() {

    companion object {
        lateinit var appComponent: AppComponent
    }

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder().appModule(AppModule(this)).build()
    }
}