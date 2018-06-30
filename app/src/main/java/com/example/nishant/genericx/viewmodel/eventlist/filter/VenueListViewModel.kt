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
                    (itemList as MutableLiveData).value =
                            Venue.values().map { it.displayValue }
                                    .zip(Venue.values().map { it in userPrefs.eventFilter.venues })
                                    .toFilterItemList()
                })
    }

    override fun toggleItem(name: String) {
        repository.userPreferences
                .take(1)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    val toggledVenue = Venue.valueOf(name)
                    val updatedVenues = when (it.eventFilter.venues.contains(toggledVenue)) {
                        true -> it.eventFilter.venues.toMutableList().also { it.remove(toggledVenue) }
                        false -> it.eventFilter.venues.toMutableList().also { it.add(toggledVenue) }
                    }
                    repository.setUserPreferences(
                            UserPreferences(
                                    EventFilter(
                                            it.eventFilter.categories,
                                            updatedVenues,
                                            it.eventFilter.days,
                                            it.eventFilter.isFavorite,
                                            it.eventFilter.isOngoing
                                    ),
                                    it.criterion
                            )
                    )
                }
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}