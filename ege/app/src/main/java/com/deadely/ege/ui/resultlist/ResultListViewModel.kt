package com.deadely.ege.ui.resultlist

import android.content.Context
import android.content.SharedPreferences
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.deadely.ege.base.*
import com.deadely.ege.repository.Repository
import com.deadely.ege.utils.PreferencesManager
import com.deadely.ege.utils.PreferencesManager.get
import com.deadely.ege.utils.PreferencesManager.set
import dagger.hilt.android.qualifiers.ApplicationContext

class ResultListViewModel @ViewModelInject constructor(
    private val repository: Repository,
    @ApplicationContext private val context: Context
) :
    ViewModel() {

    private val preferences: SharedPreferences = PreferencesManager.defaultPrefs(context)

    var currentDiscipline: String
        get() = preferences[CURRENT_DISCIPLINE, ""]!!
        set(value) {
            preferences[CURRENT_DISCIPLINE] = value
        }

    fun setPoints(points: Int) {
        when (currentDiscipline) {
            MATEMATIKA -> {
                currentDiscipline = preferences[MAX_VALUE_MATEMATIKA, 0].toString()
                preferences[USERS_LAST_POINT_MATEMATIKA] = points
            }
            FIZIKA -> {
                currentDiscipline = preferences[MAX_VALUE_FIZIKA, 0].toString()
                preferences[USERS_LAST_POINT_FIZIKA] = points
            }
            INFORMATIKA -> {
                currentDiscipline = preferences[MAX_VALUE_INFORMATIKA, 0].toString()
                preferences[USERS_LAST_POINT_INFORMATIKA] = points

            }
            RYSSKIU -> {
                currentDiscipline = preferences[MAX_VALUE_RYSSKIU, 0].toString()
                preferences[USERS_LAST_POINT_RYSSKIU] = points
            }
        }
    }
}