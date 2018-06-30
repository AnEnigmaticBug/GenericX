package com.example.nishant.genericx.viewmodel.eventlist.filter

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import com.example.nishant.genericx.GenericXApp
import com.example.nishant.genericx.data.model.Criterion
import com.example.nishant.genericx.data.model.EventFilter
import com.example.nishant.genericx.data.model.UserPreferences
import com.example.nishant.genericx.data.repository.EventRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

/**
 * Created by AnEnigmaticBug on 29/6/18.
 */

class FilterMainMenuViewModel : ViewModel() {

    @Inject
    lateinit var repository: EventRepository

    init {
        GenericXApp.appComponent.inject(this)
    }

    private val compositeDisposable = CompositeDisposable()

    val activeCriterion: LiveData<Criterion> = MutableLiveData<Criterion>().also {
        it.value = Criterion.EventDay
    }
    val ongoingOnlyMode: LiveData<Boolean> = MutableLiveData<Boolean>().also {
        it.value = false
    }
    val favoritesOnlyMode: LiveData<Boolean> = MutableLiveData<Boolean>().also {
        it.value = false
    }

    init {
        compositeDisposable.add(repository.userPreferences
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    (activeCriterion as MutableLiveData).value = it.criterion
                    (ongoingOnlyMode as MutableLiveData).value = it.eventFilter.isOngoing
                    (favoritesOnlyMode as MutableLiveData).value = it.eventFilter.isFavorite
                })
    }

    fun enableShowByEventDay() {
        repository.userPreferences
                .take(1)
                .subscribe {
                    repository.setUserPreferences(UserPreferences(it.eventFilter, Criterion.EventDay))
                }
    }

    fun enableShowByCategory() {
        Log.d("DAO", "entered-1")
        repository.userPreferences
                .take(1)
                .doOnNext { Log.d("DAO", "entered-2") }
                .subscribe {
                    Log.d("DAO", "set")
                    repository.setUserPreferences(UserPreferences(it.eventFilter, Criterion.Category))
                }
    }

    fun toggleOngoingOnlyMode() {
        repository.userPreferences
                .take(1)
                .subscribe {
                    repository.setUserPreferences(
                            UserPreferences(
                                    EventFilter(
                                            it.eventFilter.categories,
                                            it.eventFilter.venues,
                                            it.eventFilter.days,
                                            it.eventFilter.isFavorite,
                                            !it.eventFilter.isOngoing
                                    ),
                                    it.criterion
                            )
                    )
                    (ongoingOnlyMode as MutableLiveData).value = !it.eventFilter.isOngoing
                }
    }

    fun toggleFavoritesOnlyMode() {
        repository.userPreferences
                .take(1)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    repository.setUserPreferences(
                            UserPreferences(
                                    EventFilter(
                                            it.eventFilter.categories,
                                            it.eventFilter.venues,
                                            it.eventFilter.days,
                                            !it.eventFilter.isFavorite,
                                            it.eventFilter.isOngoing
                                    ),
                                    it.criterion
                            )
                    )
                    (favoritesOnlyMode as MutableLiveData).value = !it.eventFilter.isFavorite
                }
    }

    fun resetToDefaults() {
        repository.setUserPreferences(UserPreferences(EventFilter(), Criterion.EventDay))
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}