package com.deadely.ege.ui.discipline

import android.content.Context
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deadely.ege.model.Disciplines
import com.deadely.ege.repository.Repository
import com.deadely.ege.utils.*
import com.deadely.ege.utils.PreferencesManager.set
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class DisciplineViewModel @ViewModelInject constructor(
    private val repository: Repository,
    @ApplicationContext private val context: Context
) :
    ViewModel() {

    private val mDisciplines = MutableLiveData<DataState<List<Disciplines>>>()
    val disciplines: LiveData<DataState<List<Disciplines>>>
        get() = mDisciplines

    private val preferences = PreferencesManager.defaultPrefs(context)

    init {
        getDisciplines()
        preferences[CURRENT_DISCIPLINE] = ""
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

    fun setMaxValues(data: List<Disciplines>) {
        for (discipline in data) {
            when (discipline.eid) {
                MATEMATIKA -> {
                    preferences[MAX_VALUE_MATEMATIKA] = discipline.maxPoints
                }
                INFORMATIKA -> {
                    preferences[MAX_VALUE_INFORMATIKA] = discipline.maxPoints
                }
                RYSSKIU -> {
                    preferences[MAX_VALUE_RYSSKIU] = discipline.maxPoints
                }
                FIZIKA -> {
                    preferences[MAX_VALUE_FIZIKA] = discipline.maxPoints
                }
            }
        }
    }

}