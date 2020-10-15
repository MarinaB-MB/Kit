package com.deadely.ege.ui.university

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.deadely.ege.R
import com.deadely.ege.model.University
import com.deadely.ege.ui.detailuniversity.DetailUniversityActivity
import com.deadely.ege.utils.DataState
import com.deadely.ege.utils.makeGone
import com.deadely.ege.utils.makeVisible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_university.*

@AndroidEntryPoint
class UniversityFragment : Fragment(R.layout.fragment_university) {

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initSubscribe()
        initView()
    }

    private fun initView() {
        rvUniversities.layoutManager = LinearLayoutManager(context)
        rvUniversities.setHasFixedSize(true)
        rvUniversities.adapter = adapter
    }

    private fun initSubscribe() {
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
                    Log.e("UniversityFragment.TAG", "initSubscribe: ${it.exception.message}")
                }
                is DataState.Success -> {
                    pvLoad.makeGone()
                    rvUniversities.makeVisible()
                    Log.e("UniversityFragment.TAG", "initSubscribe: ${it.data}")
                    adapter.setData(it.data)
                }
            }
        })
    }
}