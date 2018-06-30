package com.example.nishant.genericx.viewmodel.eventlist.filter

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel

/**
 * Created by AnEnigmaticBug on 30/6/18.
 */

abstract class FilterItemListViewModel : ViewModel() {

    data class FilterItem(val name: String, val enabled: Boolean)

    val itemList: LiveData<List<FilterItem>> = MutableLiveData<List<FilterItem>>().also {
        it.value = listOf()
    }

    protected fun List<Pair<String, Boolean>>.toFilterItemList() =
            this.map { FilterItem(it.first, it.second) }

    abstract fun toggleItem(name: String)
}