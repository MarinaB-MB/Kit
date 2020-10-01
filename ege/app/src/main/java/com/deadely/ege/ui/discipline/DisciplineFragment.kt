package com.deadely.ege.ui.discipline

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.deadely.ege.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DisciplineFragment : Fragment(R.layout.fragment_discipline) {

    private val disciplineViewModel: DisciplineViewModel by viewModels()

}