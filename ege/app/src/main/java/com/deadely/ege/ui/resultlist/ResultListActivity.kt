package com.deadely.ege.ui.resultlist

import android.content.SharedPreferences
import android.graphics.Paint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.deadely.ege.R
import com.deadely.ege.base.*
import com.deadely.ege.model.Asks
import com.deadely.ege.utils.PreferencesManager
import com.deadely.ege.utils.PreferencesManager.get
import com.deadely.ege.utils.PreferencesManager.set
import com.deadely.ege.utils.Utils
import kotlinx.android.synthetic.main.activity_result_list.*

class ResultListActivity : AppCompatActivity(R.layout.activity_result_list) {
    companion object {
        const val CURRENT_VARIANT = "ResultListActivity.VARIANT"
        const val SUCCESS_COUNT = "ResultListActivity.SUCCESS_COUNT"
        const val FAIL_COUNT = "ResultListActivity.FAIL_COUNT"
        const val POINTS = "ResultListActivity.POINTS"
    }

    private lateinit var preferences: SharedPreferences
    private lateinit var adapter: AsksAdapter
    private var successCount = 0
    private var failCount = 0
    private var allCount = 0
    private var points = 0
    private var list: ArrayList<Asks>? = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        preferences = PreferencesManager.defaultPrefs(this)
        intent?.extras?.let {
            list = it.getParcelableArrayList<Asks>(CURRENT_VARIANT)
            successCount = it.getInt(SUCCESS_COUNT)
            failCount = it.getInt(FAIL_COUNT)
            points = it.getInt(POINTS)
        }
        list?.let { allCount = it.size }
        initView()
        setListeners()
    }

    private fun setListeners() {
        tvMore.setOnClickListener {
//            llResultCount.startAnimation(
//                AnimationUtils.loadAnimation(this, R.anim.translate_result)
//            )
        }
        btnClose.setOnClickListener { finish() }
    }

    private fun initView() {
        adapter = AsksAdapter(list!!)
        rvAsks.adapter = adapter
        tvMore.paintFlags = tvMore.paintFlags or Paint.UNDERLINE_TEXT_FLAG
        var text = preferences[CURRENT_DISCIPLINE, ""]
        when (text) {
            MATEMATIKA -> {
                text = preferences[MAX_VALUE_MATEMATIKA, 0].toString()
                preferences[USERS_LAST_POINT_MATEMATIKA] = points
            }
            FIZIKA -> {
                text = preferences[MAX_VALUE_FIZIKA, 0].toString()
                preferences[USERS_LAST_POINT_FIZIKA] = points
            }
            INFORMATIKA -> {
                text = preferences[MAX_VALUE_INFORMATIKA, 0].toString()
                preferences[USERS_LAST_POINT_INFORMATIKA] = points

            }
            RYSSKIU -> {
                text = preferences[MAX_VALUE_RYSSKIU, 0].toString()
                preferences[USERS_LAST_POINT_RYSSKIU] = points
            }
        }
        tvMaxValue.text = Utils.getString(R.string.max_value).format(text)
        preferences[CURRENT_DISCIPLINE] = ""
        tvAskCount.text = "${successCount}/${allCount}"
        tvPoint.text = points.toString()
    }
}