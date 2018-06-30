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

    override fun toggleItem(name: String) {
        repository.userPreferences
                .take(1)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    val toggledCategory = Category.valueOf(name)
                    val updatedCategories = when(it.eventFilter.categories.contains(toggledCategory)) {
                        true  -> it.eventFilter.categories.toMutableList().also { it.remove(toggledCategory) }
                        false -> it.eventFilter.categories.toMutableList().also { it.add(toggledCategory) }
                    }
                    repository.setUserPreferences(
                            UserPreferences(
                                    EventFilter(
                                            updatedCategories,
                                            it.eventFilter.venues,
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