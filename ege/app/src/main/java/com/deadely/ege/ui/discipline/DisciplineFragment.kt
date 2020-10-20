package com.deadely.ege.ui.discipline

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.deadely.ege.R
import com.deadely.ege.base.BaseFragment
import com.deadely.ege.model.Variant
import com.deadely.ege.ui.variants.VariantsActivity
import com.deadely.ege.utils.DataState
import com.deadely.ege.utils.makeGone
import com.deadely.ege.utils.makeVisible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_discipline.*

@AndroidEntryPoint
class DisciplineFragment : BaseFragment(R.layout.fragment_discipline) {

    companion object {
        const val TAG = "DisciplineFragment.TAG"
    }

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

    override fun initView() {
        rvDisciplines.layoutManager = LinearLayoutManager(context)
        rvDisciplines.setHasFixedSize(true)
        rvDisciplines.adapter = adapter
    }

    override fun initObserver() {
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
                    Log.e(TAG, "initSubscribe: ${it.exception.message}")
                }
                is DataState.Success -> {
                    pvLoad.makeGone()
                    rvDisciplines.makeVisible()
                    Log.e(TAG, "initSubscribe: ${it.data}")
                    disciplineViewModel.setMaxValues(it.data)
                    adapter.setData(it.data)
                }
            }
        })
    }

    override fun setListeners() {}

    override fun getExtras() {}
}