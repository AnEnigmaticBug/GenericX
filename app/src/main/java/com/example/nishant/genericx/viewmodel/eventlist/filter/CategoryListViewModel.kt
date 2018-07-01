package com.example.nishant.genericx.viewmodel.eventlist.filter

import android.arch.lifecycle.MutableLiveData
import com.example.nishant.genericx.GenericXApp
import com.example.nishant.genericx.data.model.Category
import com.example.nishant.genericx.data.model.EventFilter
import com.example.nishant.genericx.data.model.UserPreferences
import com.example.nishant.genericx.data.repository.EventRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

/**
 * Created by AnEnigmaticBug on 30/6/18.
 */

class CategoryListViewModel : FilterItemListViewModel() {

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
                            Category.values().map { it.prettyString() }
                                    .zip(Category.values().map { it in userPrefs.eventFilter.categories })
                                    .toFilterItemList()
                })
    }

    override fun toggleItem(position: Int) {
        repository.userPreferences
                .take(1)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    val updatedCategories = it.eventFilter.categories.toggle(Category.values()[position])
                    repository.setUserPreferences(
                            UserPreferences(
                                    it.eventFilter.withCategories(updatedCategories),
                                    it.criterion
                            )
                    )
                }
    }

    private fun EventFilter.withCategories(categories: List<Category>) =
            EventFilter(categories, this.venues, this.days, this.isFavorite, this.isOngoing)

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}