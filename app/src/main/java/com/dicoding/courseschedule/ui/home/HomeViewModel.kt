package com.dicoding.courseschedule.ui.home

import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.courseschedule.data.Course
import com.dicoding.courseschedule.data.DataRepository
import com.dicoding.courseschedule.util.Event
import com.dicoding.courseschedule.util.QueryType
import com.dicoding.courseschedule.util.executeThread
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: DataRepository): ViewModel() {

    private val _queryType = MutableLiveData<QueryType>()

    init {
        _queryType.value = QueryType.CURRENT_DAY
    }

    fun setQueryType(queryType: QueryType) {
        _queryType.value = queryType
    }

    val courses = Transformations.switchMap(_queryType) {
        repository.getNearestSchedule(it)
    }
}
