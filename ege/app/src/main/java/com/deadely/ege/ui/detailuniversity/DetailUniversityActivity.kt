package com.deadely.ege.ui.detailuniversity

import android.content.Intent
import android.graphics.Paint
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.deadely.ege.R
import com.deadely.ege.model.University
import kotlinx.android.synthetic.main.activity_detail_university.*


class DetailUniversityActivity : AppCompatActivity(R.layout.activity_detail_university) {

    private lateinit var university: University

    companion object {
        const val UNIVERSITY = "DetailUniversityActivity.UNIVERSITY"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        intent?.extras?.let {
            university = it.getParcelable<University>(UNIVERSITY)!!
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        title = ""
        initView()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }


    private fun initView() {
        name.text = university.name
        Glide.with(baseContext)
            .load(university.image)
            .error(R.drawable.ic_error)
            .into(image)
        site.text = university.site
        site.paintFlags = site.paintFlags or Paint.UNDERLINE_TEXT_FLAG
        site.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(university.site)))
        }
        nameFieldOne.text = university.specialities[0].name
        valueFieldOne.text = university.specialities[0].point.toString()
        nameFieldTwo.text = university.specialities[1].name
        valueFieldTwo.text = university.specialities[1].point.toString()
        nameFieldThree.text = university.specialities[2].name
        valueFieldThree.text = university.specialities[2].point.toString()
    }
}