package com.deadely.ege.ui.home

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.deadely.ege.R
import com.deadely.ege.utils.DataState
import com.deadely.ege.utils.Utils
import com.deadely.ege.utils.makeGone
import com.deadely.ege.utils.makeVisible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_home.*

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {
    companion object {
        const val TAG = "HomeFragment.Tag"
    }


    private val homeViewModel: HomeViewModel by viewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObserver()
        init()
    }

    private fun initObserver() {
        homeViewModel.point.observe(viewLifecycleOwner, {
            when (it) {
                is DataState.Loading -> {
                    pvLoad.makeVisible()
                    rlContent.makeGone()
                }
                is DataState.Error -> {
                    it.exception.printStackTrace()
                    pvLoad.makeGone()
                    rlContent.makeGone()
                    Log.e(TAG, "initSubscribe: ${it.exception.message}")
                }
                is DataState.Success -> {
                    pvLoad.makeGone()
                    rlContent.makeVisible()
                    Log.e(TAG, "initSubscribe: ${it.data}")
                    tvPoints.text = homeViewModel.getMiddlePoint(it.data).toString()
                }
            }
        })
    }

    private fun init() {
        homeViewModel.getSecondPoint()
        if (homeViewModel.isResult()) {
            tvDisciplines.text = homeViewModel.getDisciplinesResults()
        } else {
            tvDescription.text = Utils.getString(R.string.no_last_result)
            tvDisciplines.makeGone()
        }
    }

}