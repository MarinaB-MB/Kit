package com.deadely.ege.ui.resultlist

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.deadely.ege.R
import com.deadely.ege.model.Asks
import kotlinx.android.synthetic.main.activity_result_list.*

class ResultListActivity : AppCompatActivity(R.layout.activity_result_list) {
    companion object {
        const val CURRENT_VARIANT = "ResultListActivity.VARIANT"
        const val SUCCESS_COUNT = "ResultListActivity.VARIANT"
        const val FAIL_COUNT = "ResultListActivity.VARIANT"
    }

    private lateinit var adapter: AsksAdapter
    private var successCount = 0
    private var failCount = 0
    private var allCount = 0
    private var list: ArrayList<Asks>? = arrayListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        intent?.extras?.let {
            list = it.getParcelableArrayList<Asks>(CURRENT_VARIANT)
            successCount = it.getInt(SUCCESS_COUNT)
            failCount = it.getInt(FAIL_COUNT)
        }
        list?.let { allCount = it.size }
        initView()
    }

    private fun initView() {
        adapter = AsksAdapter(list!!)
        rvAsks.adapter = adapter
    }
}