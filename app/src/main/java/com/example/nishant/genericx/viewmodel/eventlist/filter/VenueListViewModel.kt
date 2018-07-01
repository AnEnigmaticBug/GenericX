package com.example.nishant.genericx.viewmodel.eventlist.filter

import android.arch.lifecycle.MutableLiveData
import com.example.nishant.genericx.GenericXApp
import com.example.nishant.genericx.data.model.EventFilter
import com.example.nishant.genericx.data.model.UserPreferences
import com.example.nishant.genericx.data.model.Venue
import com.example.nishant.genericx.data.repository.EventRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

/**
 * Created by AnEnigmaticBug on 30/6/18.
 */

class VenueListViewModel : FilterItemListViewModel() {

    @Inject
    lateinit var repository: EventRepository

    init {
        GenericXApp.appComponent.inject(this)
    }

    private val compositeDisposable = CompositeDisposable()

    init {
        compositeDisposable.add(repository.userPreferences
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    val userPrefs = it
                    //Each venue is associated with its presence in the user preferences.
                    (itemList as MutableLiveData).value =
                            Venue.values().map { it.prettyString() }
                                    .zip(Venue.values().map { it in userPrefs.eventFilter.venues })
                                    .toFilterItemList()
                })
    }

    override fun toggleItem(position: Int) {
        repository.userPreferences
                .take(1)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    val updatedVenues = it.eventFilter.venues.toggle(Venue.values()[position])
                    repository.setUserPreferences(
                            UserPreferences(
                                    it.eventFilter.withVenues(updatedVenues),
                                    it.criterion
                            )
                    )
                }
    }

    private fun EventFilter.withVenues(venues: List<Venue>) =
            EventFilter(this.categories, venues, this.days, this.isFavorite, this.isOngoing)

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}