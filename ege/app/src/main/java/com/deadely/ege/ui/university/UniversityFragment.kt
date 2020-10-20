package com.deadely.ege.ui.university

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.deadely.ege.R
import com.deadely.ege.base.BaseFragment
import com.deadely.ege.model.University
import com.deadely.ege.ui.detailuniversity.DetailUniversityActivity
import com.deadely.ege.utils.DataState
import com.deadely.ege.utils.makeGone
import com.deadely.ege.utils.makeVisible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_university.*

@AndroidEntryPoint
class UniversityFragment : BaseFragment(R.layout.fragment_university) {

    companion object {
        const val TAG = "UniversityFragment.TAG"
    }

    private val universityViewModel: UniversityViewModel by viewModels()

    private val adapter =
        UniversityAdapter(arrayListOf(), object : UniversityAdapter.OnClickListener {
            override fun onClick(unit: University) {
                openDetailScreen(unit)
            }
        })

    private fun openDetailScreen(unit: University) {
        val bundle = Bundle().apply {
            putParcelable(DetailUniversityActivity.UNIVERSITY, unit)
        }
        val intent = Intent(activity, DetailUniversityActivity::class.java).apply {
            putExtras(bundle)
        }
        startActivity(intent)
    }

    override fun initView() {
        rvUniversities.layoutManager = LinearLayoutManager(context)
        rvUniversities.setHasFixedSize(true)
        rvUniversities.adapter = adapter
    }

    override fun initObserver() {
        universityViewModel.universities.observe(viewLifecycleOwner, {
            when (it) {
                is DataState.Loading -> {
                    pvLoad.makeVisible()
                    rvUniversities.makeGone()
                }
                is DataState.Error -> {
                    it.exception.printStackTrace()
                    pvLoad.makeGone()
                    rvUniversities.makeGone()
                    Log.e(TAG, "initSubscribe: ${it.exception.message}")
                }
                is DataState.Success -> {
                    pvLoad.makeGone()
                    rvUniversities.makeVisible()
                    Log.e(TAG, "initSubscribe: ${it.data}")
                    adapter.setData(it.data)
                }
            }
        })
    }

    override fun setListeners() {

    }

    override fun getExtras() {

    }
}