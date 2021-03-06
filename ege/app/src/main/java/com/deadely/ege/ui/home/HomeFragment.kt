package com.deadely.ege.ui.home

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.deadely.ege.R
import com.deadely.ege.base.BaseFragment
import com.deadely.ege.model.University
import com.deadely.ege.ui.detailuniversity.DetailUniversityActivity
import com.deadely.ege.ui.university.UniversityAdapter
import com.deadely.ege.utils.DataState
import com.deadely.ege.utils.Utils
import com.deadely.ege.utils.makeGone
import com.deadely.ege.utils.makeVisible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_home.*

@AndroidEntryPoint
class HomeFragment : BaseFragment(R.layout.fragment_home) {
    companion object {
        const val TAG = "HomeFragment.Tag"
    }

    private val universityAdapter =
        UniversityAdapter(arrayListOf(), object : UniversityAdapter.OnClickListener {
            override fun onClick(unit: University) {
                openDetailScreen(unit)
            }
        })

    private val homeViewModel: HomeViewModel by viewModels()

    var list = mutableListOf<University>()


    override fun initObserver() {
        homeViewModel.point.observe(viewLifecycleOwner, {
            when (it) {
                is DataState.Loading -> {
                    pvLoad.makeVisible()
                    rlContent.makeGone()
                    rvUnis.makeGone()
                    tvEmptyUnisList.makeGone()
                }
                is DataState.Error -> {
                    it.exception.printStackTrace()
                    pvLoad.makeGone()
                    rlContent.makeGone()
                    rvUnis.makeGone()
                    tvEmptyUnisList.makeGone()
                }
                is DataState.Success -> {
                    pvLoad.makeGone()
                    rlContent.makeVisible()
                    rvUnis.makeVisible()
                    tvPoints.requestFocus()
                    tvPoints.text = homeViewModel.getMiddlePoint(it.data).toString()
                    if (list.isEmpty()) {
                        rvUnis.makeGone()
                        tvEmptyUnisList.makeVisible()
                    } else {
                        tvEmptyUnisList.makeGone()
                        rvUnis.makeVisible()
                    }
                }
            }
        })
        homeViewModel.universities.observe(viewLifecycleOwner, { rawData ->
            when (rawData) {
                is DataState.Loading -> {
                }
                is DataState.Error -> {
                    rawData.exception.printStackTrace()
                    rvUnis.makeGone()
                    tvEmptyUnisList.makeGone()
                }
                is DataState.Success -> {
                    val middleValue = homeViewModel.getMiddleValue().toInt()
                    for (item in rawData.data) {
                        for (speciality in item.specialities) {
                            if (middleValue >= speciality.point.toInt()
                            ) {
                                list.add(item)
                            }
                        }
                    }
                    list = list.distinctBy { it.name }.toMutableList()
                    universityAdapter.setData(list)
                }
            }
        })
    }

    override fun initView() {
        homeViewModel.getSecondsPoints()
        if (homeViewModel.isResult()) {
            tvDisciplines.text = homeViewModel.getDisciplinesResults()
        } else {
            tvDescription.text = Utils.getString(R.string.no_last_result)
            tvDisciplines.makeGone()
        }
        rvUnis.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = universityAdapter
        }
        homeViewModel.getUniversities()
    }

    private fun openDetailScreen(unit: University) {
        val bundle = Bundle().apply {
            putParcelable(DetailUniversityActivity.UNIVERSITY, unit)
        }
        val intent = Intent(activity, DetailUniversityActivity::class.java).apply {
            putExtras(bundle)
        }
        startActivity(intent)
    }

    override fun setListeners() {}

    override fun getExtras() {}
}