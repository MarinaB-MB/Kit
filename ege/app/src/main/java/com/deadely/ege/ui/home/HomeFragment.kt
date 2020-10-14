package com.deadely.ege.ui.home

import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.deadely.ege.R
import com.deadely.ege.base.*
import com.deadely.ege.utils.PreferencesManager
import com.deadely.ege.utils.PreferencesManager.get
import com.deadely.ege.utils.Utils
import com.deadely.ege.utils.makeGone
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_home.*

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {
    companion object {
        const val TAG = "HomeFragment.Tag"
    }

    private lateinit var preferences: SharedPreferences
    private val homeViewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        preferences = PreferencesManager.defaultPrefs(requireContext())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        if (preferences[USERS_LAST_POINT, 0].toString().isEmpty()
            && preferences[USERS_LAST_POINT_RYSSKIU, 0].toString().isEmpty()
            && preferences[USERS_LAST_POINT_INFORMATIKA, 0].toString().isEmpty()
            && preferences[USERS_LAST_POINT_MATEMATIKA, 0].toString().isEmpty()
            && preferences[USERS_LAST_POINT_FIZIKA, 0].toString().isEmpty()
        ) {
            tvPoints.text = Utils.getString(R.string.no_last_result)
            tvDisciplines.makeGone()
        } else {
            tvPoints.text =
                preferences[USERS_LAST_POINT, 0].toString().format(Utils.getString(R.string.points))
            tvDisciplines.text =
                Utils.getString(R.string.rysskiu).format(preferences[USERS_LAST_POINT_RYSSKIU, 0])

        }
    }
}