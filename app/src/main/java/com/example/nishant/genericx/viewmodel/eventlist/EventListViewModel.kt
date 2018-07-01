package com.example.nishant.genericx.viewmodel.eventlist

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.example.nishant.genericx.GenericXApp
import com.example.nishant.genericx.data.model.*
import com.example.nishant.genericx.data.repository.EventRepository
import com.example.nishant.genericx.util.isOnSameDay
import com.example.nishant.genericx.util.modulo
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import javax.inject.Inject

/**
 * Created by AnEnigmaticBug on 26/6/18.
 */

class EventListViewModel : ViewModel() {

    @Inject
    lateinit var repository: EventRepository

    init {
        GenericXApp.appComponent.inject(this)
    }


    private val compositeDisposable = CompositeDisposable()

    /**
     * It always lies in [0, criterion.values().size). Its used as an index to determine the current
     * pageTitle and its contents.
     * */
    private var pageNumber = 0

    //LiveData to be exposed to interested Views.
    val criterion: LiveData<Criterion> = MutableLiveData<Criterion>()
    val pageTitle: LiveData<String> = MutableLiveData<String>()
    val eventsToDisplay: LiveData<List<Event>> =  MutableLiveData<List<Event>>()

    private lateinit var filteredEvents: Flowable<List<Event>>

    init {
        compositeDisposable.add(repository.userPreferences
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    pageNumber = -1
                    (criterion as MutableLiveData).value = it.criterion
                    val userPrefs = it
                    filteredEvents = repository.events
                            .map { it.filter { EventFilter.isSatisfiedByEvent(it, userPrefs.eventFilter) } }
                    showNextPage()
                })
    }

    fun showNextPage() {
        when(criterion.value) {
            Criterion.EventDay -> {
                pageNumber = (pageNumber + 1).modulo(EventDay.values().size)
                showPageByEventDay()
            }
            Criterion.Category -> {
                pageNumber = (pageNumber + 1).modulo(Category.values().size)
                showPageByCategory()
            }
        }
    }

    fun showPrevPage() {
        when(criterion.value) {
            Criterion.EventDay -> {
                pageNumber = (pageNumber - 1).modulo(EventDay.values().size)
                showPageByEventDay()
            }
            Criterion.Category -> {
                pageNumber = (pageNumber - 1).modulo(Category.values().size)
                showPageByCategory()
            }
        }
    }

    /**
     * This cute little variable is actually pretty critical to this app's functioning. If, in place
     * of this, compositeDisposable was used, it would cause increasing an amount of flickering to
     * take place.
     *
     * This is because compositeDisposable would dispose its subscriptions only when the ViewModel
     * would be cleared. This means that every time we called showPageByCategory(or EventDay), a new
     * subscription would come into existence, each running in parallel and each able to change the
     * eventsToDisplay variable and most damningly, each with its own filter. This would cause all
     * sorts of visual artifacts.
     *
     * TL;DR: Don't remove this variable unless you know what you're doing.
     * */
    private var disposable: Disposable? = null

    private fun showPageByCategory() {
        val category = Category.values()[pageNumber]
        (pageTitle as MutableLiveData).value = category.prettyString()

        disposable?.dispose()
        disposable = filteredEvents
                .map { it.filter { it.category == category } }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { (eventsToDisplay as MutableLiveData).value = it }
    }

    private fun showPageByEventDay() {
        val eventDay = EventDay.values()[pageNumber]
        (pageTitle as MutableLiveData).value = eventDay.prettyString()

        disposable?.dispose()
        disposable = filteredEvents
                .map { it.filter { it.datetime.isOnSameDay(eventDay.datetime) } }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { (eventsToDisplay as MutableLiveData).value = it }
    }

    fun toggleFavorite(event: Event) {
        when(event.isFavorite) {
            true  -> repository.undoFavorite(event.id)
            false -> repository.makeFavorite(event.id)
        }
    }

    override fun onCleared() {
        super.onCleared()
        disposable?.dispose()
        compositeDisposable.clear()
    }
}