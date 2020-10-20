package com.deadely.ege.ui.resultlist

import android.content.SharedPreferences
import android.graphics.Paint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.deadely.ege.R
import com.deadely.ege.base.*
import com.deadely.ege.model.Asks
import com.deadely.ege.utils.PreferencesManager
import com.deadely.ege.utils.PreferencesManager.get
import com.deadely.ege.utils.PreferencesManager.set
import com.deadely.ege.utils.Utils
import com.deadely.ege.utils.makeGone
import com.deadely.ege.utils.makeVisible
import kotlinx.android.synthetic.main.activity_result_list.*

class ResultListActivity : AppCompatActivity(R.layout.activity_result_list) {
    companion object {
        const val CURRENT_VARIANT = "ResultListActivity.VARIANT"
        const val SUCCESS_COUNT = "ResultListActivity.SUCCESS_COUNT"
        const val FAIL_COUNT = "ResultListActivity.FAIL_COUNT"
        const val POINTS = "ResultListActivity.POINTS"
    }

    private lateinit var preferences: SharedPreferences
    private var adapter = AsksAdapter(listOf(), this)
    private var successCount = 0
    private var failCount = 0
    private var allCount = 0
    private var points = 0
    private var list: ArrayList<Asks> = arrayListOf()
    var currentDiscipline: String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        preferences = PreferencesManager.defaultPrefs(this)
        getExtras()
        initView()
        setListeners()
    }

    private fun getExtras() {
        intent?.extras?.let {
            it.getParcelableArrayList<Asks>(CURRENT_VARIANT)?.let { asks -> list = asks }
            successCount = it.getInt(SUCCESS_COUNT)
            failCount = it.getInt(FAIL_COUNT)
            points = it.getInt(POINTS)
        }
        allCount = list.size
    }

    private fun setListeners() {
        tvMore.setOnClickListener { openVariantScreen() }
        btnClose.setOnClickListener { finish() }
    }

    private fun openVariantScreen() {
        rvAsks.adapter = adapter
        adapter.setData(list)
        llResultCount.makeGone()
        rvAsks.makeVisible()
    }

    private fun initView() {
        rvAsks.adapter = adapter
        rvAsks.layoutManager = LinearLayoutManager(applicationContext)
        tvMore.paintFlags = tvMore.paintFlags or Paint.UNDERLINE_TEXT_FLAG
        currentDiscipline = preferences[CURRENT_DISCIPLINE, ""]
        setPoints(currentDiscipline)
        tvMaxValue.text = Utils.getString(R.string.max_value).format(currentDiscipline)
        preferences[CURRENT_DISCIPLINE] = ""
        tvAskCount.text = "${successCount}/${allCount}"
        tvPoint.text = points.toString()
    }

    private fun setPoints(text: String?) {
        when (text) {
            MATEMATIKA -> {
                currentDiscipline = preferences[MAX_VALUE_MATEMATIKA, 0].toString()
                preferences[USERS_LAST_POINT_MATEMATIKA] = points
            }
            FIZIKA -> {
                currentDiscipline = preferences[MAX_VALUE_FIZIKA, 0].toString()
                preferences[USERS_LAST_POINT_FIZIKA] = points
            }
            INFORMATIKA -> {
                currentDiscipline = preferences[MAX_VALUE_INFORMATIKA, 0].toString()
                preferences[USERS_LAST_POINT_INFORMATIKA] = points

            }
            RYSSKIU -> {
                currentDiscipline = preferences[MAX_VALUE_RYSSKIU, 0].toString()
                preferences[USERS_LAST_POINT_RYSSKIU] = points
            }
        }
    }
}