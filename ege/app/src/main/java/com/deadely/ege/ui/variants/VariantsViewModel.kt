package com.deadely.ege.ui.variants

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deadely.ege.model.Asks
import com.deadely.ege.repository.Repository
import com.deadely.ege.utils.DataState
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class VariantsViewModel @ViewModelInject constructor(private val repository: Repository) :
    ViewModel() {

    private val mAsks = MutableLiveData<DataState<List<Asks>>>()
    val asks: LiveData<DataState<List<Asks>>>
        get() = mAsks

    init {
        getAsks()
    }

    private fun getAsks() {
        viewModelScope.launch {
            repository.getAsks()
                .onEach { dataState -> subscribe(dataState) }
                .launchIn(viewModelScope)
        }
    }

    private fun subscribe(dataState: DataState<List<Asks>>) {
        when (dataState) {
            is DataState.Loading -> {
                mAsks.postValue(DataState.Loading)
            }
            is DataState.Error -> {
                mAsks.postValue(DataState.Error(dataState.exception))
            }
            is DataState.Success -> {
                mAsks.postValue(DataState.Success(dataState.data))
            }
        }
    }

}