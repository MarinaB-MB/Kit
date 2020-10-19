package com.deadely.ege.ui.home

import android.content.Context
import android.content.SharedPreferences
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deadely.ege.R
import com.deadely.ege.base.*
import com.deadely.ege.model.PointsObject
import com.deadely.ege.model.University
import com.deadely.ege.repository.Repository
import com.deadely.ege.utils.DataState
import com.deadely.ege.utils.PreferencesManager
import com.deadely.ege.utils.PreferencesManager.get
import com.deadely.ege.utils.Utils
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class HomeViewModel @ViewModelInject constructor(
    private val repository: Repository,
    @ApplicationContext context: Context
) :
    ViewModel() {

    private val mUniversities = MutableLiveData<DataState<List<University>>>()
    val universities: LiveData<DataState<List<University>>>
        get() = mUniversities

    private val mPoint = MutableLiveData<DataState<PointsObject>>()
    val point: LiveData<DataState<PointsObject>>
        get() = mPoint

    private var preferences: SharedPreferences =
        PreferencesManager.defaultPrefs(context)

    fun isResult(): Boolean {
        return preferences[USERS_LAST_POINT, 0].toString().isNotEmpty()
    }

    fun getDisciplinesResults(): String {
        val text =
            Utils.getString(R.string.rysskiu).format(preferences[USERS_LAST_POINT_RYSSKIU, 0])
        val text1 = Utils.getString(R.string.matematika)
            .format(preferences[USERS_LAST_POINT_MATEMATIKA, 0])
        val text2 = Utils.getString(R.string.informatika).format(
            preferences[USERS_LAST_POINT_INFORMATIKA, 0]
        )
        val text3 = Utils.getString(R.string.fizika)
            .format(preferences[USERS_LAST_POINT_FIZIKA, 0])
        return text.plus(text1).plus(text2).plus(text3)
    }

    fun getSecondsPoints() {
        viewModelScope.launch {
            repository.getPoints()
                .onEach { dataState -> subscribeData(dataState) }
                .launchIn(viewModelScope)
        }
    }

    fun getMiddlePoint(pointsObject: PointsObject): Int {
        var infa = preferences[USERS_LAST_POINT_INFORMATIKA, 0]
        var matem = preferences[USERS_LAST_POINT_MATEMATIKA, 0]
        var ryss = preferences[USERS_LAST_POINT_RYSSKIU, 0]
        var fizika = preferences[USERS_LAST_POINT_FIZIKA, 0]
        fizika = pointsObject.fiz[fizika!!].second_point
        ryss = pointsObject.fiz[ryss!!].second_point
        matem = pointsObject.fiz[matem!!].second_point
        infa = pointsObject.fiz[infa!!].second_point
        return Utils.getMiddleValue(listOf(infa, matem, ryss, fizika))
    }

    private fun subscribeData(dataState: DataState<PointsObject>) {
        when (dataState) {
            is DataState.Loading -> {
                mPoint.postValue(DataState.Loading)
            }
            is DataState.Error -> {
                mPoint.postValue(DataState.Error(dataState.exception))
            }
            is DataState.Success -> {
                mPoint.postValue(DataState.Success(dataState.data))
            }
        }
    }

    fun getUniversities() {
        viewModelScope.launch {
            repository.getUniversitiesList()
                .onEach { dataState -> subscribeUniversitiesData(dataState) }
                .launchIn(viewModelScope)
        }
    }

    private fun subscribeUniversitiesData(dataState: DataState<List<University>>) {
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

    fun getMiddleValue(): String {
        return preferences[USERS_LAST_POINT, 0].toString()
    }
}
