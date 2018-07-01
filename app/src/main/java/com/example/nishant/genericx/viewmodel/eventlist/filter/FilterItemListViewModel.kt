package com.example.nishant.genericx.viewmodel.eventlist.filter

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.example.nishant.genericx.data.model.Category
import com.example.nishant.genericx.data.model.EventFilter
import com.example.nishant.genericx.data.model.Venue

/**
 * Created by AnEnigmaticBug on 30/6/18.
 */

abstract class FilterItemListViewModel : ViewModel() {

    data class FilterDisplayItem(val name: String, val enabled: Boolean)

    val itemList: LiveData<List<FilterDisplayItem>> = MutableLiveData<List<FilterDisplayItem>>().also {
        it.value = listOf()
    }

    protected fun List<Pair<String, Boolean>>.toFilterItemList() =
            this.map { FilterDisplayItem(it.first, it.second) }

    abstract fun toggleItem(position: Int)

    /**
     * Removes item from List if it is present. If it is not present, inserts it into the List.
     * */
    protected fun <T> List<T>.toggle(t: T): List<T> = when(this.contains(t)) {
        true  -> this.toMutableList().also { it.remove(t) }
        false -> this.toMutableList().also { it.add(t) }
    }
}