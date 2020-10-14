package com.deadely.ege.ui.discipline

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.deadely.ege.R
import com.deadely.ege.base.*
import com.deadely.ege.model.Disciplines
import com.deadely.ege.model.Variant
import com.deadely.ege.ui.variants.VariantsActivity
import com.deadely.ege.utils.DataState
import com.deadely.ege.utils.PreferencesManager
import com.deadely.ege.utils.PreferencesManager.set
import com.deadely.ege.utils.makeGone
import com.deadely.ege.utils.makeVisible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_discipline.*

@AndroidEntryPoint
class DisciplineFragment : Fragment(R.layout.fragment_discipline) {

    private lateinit var preferences: SharedPreferences
    private val disciplineViewModel: DisciplineViewModel by viewModels()

    private val adapter =
        DisciplineAdapter(arrayListOf(), object : DisciplineAdapter.OnClickListener {
            override fun onVariantClick(unit: Variant) {
                openVariantsScreen(unit)
            }

        })

    private fun openVariantsScreen(variants: Variant) {
        val bundle = Bundle().apply {
            putParcelable(VariantsActivity.VARIANT, variants)
        }
        val intent = Intent(activity, VariantsActivity::class.java)
        intent.putExtras(bundle)
        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        preferences = PreferencesManager.defaultPrefs(requireContext())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initSubscribe()
        initView()
    }

    private fun initView() {
        preferences[CURRENT_DISCIPLINE] = ""
        rvDisciplines.layoutManager = LinearLayoutManager(context)
        rvDisciplines.setHasFixedSize(true)
        rvDisciplines.adapter = adapter
    }

    private fun initSubscribe() {
        disciplineViewModel.disciplines.observe(viewLifecycleOwner, {
            when (it) {
                is DataState.Loading -> {
                    pvLoad.makeVisible()
                    rvDisciplines.makeGone()
                }
                is DataState.Error -> {
                    it.exception.printStackTrace()
                    pvLoad.makeGone()
                    rvDisciplines.makeGone()
                    Log.e("DisciplineFragment.TAG", "initSubscribe: ${it.exception.message}")
                }
                is DataState.Success -> {
                    pvLoad.makeGone()
                    rvDisciplines.makeVisible()
                    Log.e("DisciplineFragment.TAG", "initSubscribe: ${it.data}")
                    setMaxValues(it.data)
                    adapter.setData(it.data)
                }
            }
        })
    }

    private fun setMaxValues(data: List<Disciplines>) {
        for (discipline in data) {
            when (discipline.eid) {
                MATEMATIKA -> {
                    preferences[MAX_VALUE_MATEMATIKA] = discipline.max_points
                }
                INFORMATIKA -> {
                    preferences[MAX_VALUE_INFORMATIKA] = discipline.max_points
                }
                RYSSKIU -> {
                    preferences[MAX_VALUE_RYSSKIU] = discipline.max_points
                }
                FIZIKA -> {
                    preferences[MAX_VALUE_FIZIKA] = discipline.max_points
                }
            }
        }
    }

}