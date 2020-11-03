package com.deadely.ege.ui.resultlist

import android.graphics.Paint
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.deadely.ege.R
import com.deadely.ege.base.BaseActivity
import com.deadely.ege.model.Asks
import com.deadely.ege.utils.Utils
import com.deadely.ege.utils.makeGone
import com.deadely.ege.utils.makeVisible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_result_list.*

@AndroidEntryPoint
class ResultListActivity : BaseActivity(R.layout.activity_result_list) {

    companion object {
        const val CURRENT_VARIANT = "ResultListActivity.VARIANT"
        const val SUCCESS_COUNT = "ResultListActivity.SUCCESS_COUNT"
        const val FAIL_COUNT = "ResultListActivity.FAIL_COUNT"
        const val POINTS = "ResultListActivity.POINTS"
    }

    private val resultListViewModel: ResultListViewModel by viewModels()

    private var adapter = AsksAdapter(listOf(), this)
    private var successCount = 0
    private var failCount = 0
    private var allCount = 0
    private var points = 0
    private var list: ArrayList<Asks> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
    }

    override fun getExtras() {
        intent?.extras?.let {
            it.getParcelableArrayList<Asks>(CURRENT_VARIANT)?.let { asks -> list = asks }
            successCount = it.getInt(SUCCESS_COUNT)
            failCount = it.getInt(FAIL_COUNT)
            points = it.getInt(POINTS)
        }
        allCount = list.size
    }

    override fun setListeners() {
        btnBack.setOnClickListener { openResultScreen() }
        tvMore.setOnClickListener { openVariantScreen() }
        btnClose.setOnClickListener { finish() }
    }

    private fun openResultScreen() {
        btnBack.makeGone()
        llResultCount.makeVisible()
        rvAsks.makeGone()
    }

    private fun openVariantScreen() {
        adapter.setData(list)
        btnBack.makeVisible()
        llResultCount.makeGone()
        rvAsks.makeVisible()
    }

    override fun initView() {
        rvAsks.apply {
            adapter = adapter
            layoutManager = LinearLayoutManager(applicationContext)
        }
        tvMore.paintFlags = tvMore.paintFlags or Paint.UNDERLINE_TEXT_FLAG
        resultListViewModel.setPoints(points)
        tvMaxValue.text =
            Utils.getString(R.string.max_value).format(resultListViewModel.currentDiscipline)
        resultListViewModel.currentDiscipline = ""
        tvAskCount.text = "${successCount}/${allCount}"
        tvPoint.text = points.toString()
    }

    override fun initObserver() {}
}