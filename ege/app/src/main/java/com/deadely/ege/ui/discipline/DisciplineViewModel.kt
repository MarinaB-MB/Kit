package com.deadely.ege.ui.discipline

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deadely.ege.model.Disciplines
import com.deadely.ege.repository.Repository
import com.deadely.ege.utils.DataState
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class DisciplineViewModel @ViewModelInject constructor(private val repository: Repository) :
    ViewModel() {

    private val mDisciplines = MutableLiveData<DataState<List<Disciplines>>>()
    val disciplines: LiveData<DataState<List<Disciplines>>>
        get() = mDisciplines


    init {
        getDisciplines()
    }

    private fun getDisciplines() {
        viewModelScope.launch {
            repository.getDisciplinesList().onEach { dataState -> subscribe(dataState) }
                .launchIn(viewModelScope)
        }
    }

    private fun subscribe(dataState: DataState<List<Disciplines>>) {
        when (dataState) {
            is DataState.Loading -> {
                mDisciplines.postValue(DataState.Loading)
            }
            is DataState.Error -> {
                mDisciplines.postValue(DataState.Error(dataState.exception))
            }
            is DataState.Success -> {
                mDisciplines.postValue(DataState.Success(dataState.data))
            }
        }
    }

}