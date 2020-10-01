package com.deadely.ege.ui.university

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deadely.ege.model.University
import com.deadely.ege.repository.Repository
import com.deadely.ege.utils.DataState
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class UniversityViewModel @ViewModelInject constructor(private val repository: Repository) :
    ViewModel() {

    private val mUniversities = MutableLiveData<DataState<List<University>>>()
    val universities: LiveData<DataState<List<University>>>
        get() = mUniversities

    init {
        getUniversities()
    }

    private fun getUniversities() {
        viewModelScope.launch {
            repository.getUniversitiesList()
                .onEach { dataState -> subscribe(dataState) }
                .launchIn(viewModelScope)
        }
    }

    private fun subscribe(dataState: DataState<List<University>>) {
        when (dataState) {
            is DataState.Loading -> {
                mUniversities.postValue(DataState.Loading)
            }
            is DataState.Error -> {
                mUniversities.postValue(DataState.Error(dataState.exception))
            }
            is DataState.Success -> {
                mUniversities.postValue(DataState.Success(dataState.data))
            }
        }
    }

}