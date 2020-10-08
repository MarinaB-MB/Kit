package com.deadely.ege.ui.variants

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.deadely.ege.R
import com.deadely.ege.model.Answer
import com.deadely.ege.model.Asks
import com.deadely.ege.model.Variant
import com.deadely.ege.utils.DataState
import com.deadely.ege.utils.makeGone
import com.deadely.ege.utils.makeVisible
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_variants.*


@AndroidEntryPoint
class VariantsActivity : AppCompatActivity(R.layout.activity_variants) {
    companion object {
        const val VARIANT = "VariantsActivity.VARIANT"
    }

    private val variantsViewModel: VariantsViewModel by viewModels()

    private var variant: Variant? = null
    private var asks: MutableList<Asks> = mutableListOf()
    private var currentAsk: Asks? = null
    private var successCount = 0
    private var failCount = 0
    private var number = 0
    private var isImageScaled: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        intent?.extras?.let {
            variant = it.getParcelable<Variant>(VARIANT)
        }
        initView()
        initObserver()
    }

    private fun initObserver() {
        variantsViewModel.asks.observe(this, { dataState ->
            when (dataState) {
                is DataState.Loading -> {
                    pvLoad.makeVisible()
                    rlContent.makeGone()
                }
                is DataState.Error -> {
                    dataState.exception.printStackTrace()
                    pvLoad.makeGone()
                    rlContent.makeGone()
                    Log.e("VariantsActivity.TAG", "initSubscribe: ${dataState.exception.message}")
                }
                is DataState.Success -> {
                    Log.e("VariantsActivity.TAG", "initSubscribe: ${dataState.data}")
                    pvLoad.makeGone()
                    rlContent.makeVisible()
                    variant?.asks?.let { list ->
                        for (ask in list) {
                            for (apiAsk in dataState.data) {
                                if (ask == apiAsk._id) {
                                    asks.add(apiAsk)
                                }
                            }
                        }
                        asks = asks.sortedBy { it.number }.toMutableList()
                        setData(asks[number])
                    }
                }
            }
        })
    }

    private fun initView() {
        one.setOnClickListener { checkAnswer(one.text.toString()) }
        two.setOnClickListener { checkAnswer(two.text.toString()) }
        three.setOnClickListener { checkAnswer(three.text.toString()) }
        four.setOnClickListener { checkAnswer(four.text.toString()) }
//        image.setOnClickListener {
//            if (isImageScaled) {
//            image.animate().scaleX(1f).scaleY(1f).duration = 350
//        } else {
//            image.animate().scaleX(1.2f).scaleY(1.2f).duration = 350
//        }
//            isImageScaled = !isImageScaled
//        }
    }

    private fun checkAnswer(answer: String) {
        var rightAnswer: Answer? = null
        currentAsk?.answer?.let {
            for (answer in it) {
                if (answer.right) {
                    rightAnswer = answer
                }
            }
        }
        if (answer == rightAnswer?.answer) {
            success()
        } else {
            fail()
        }
        getNext()
    }

    private fun getNext() {
        number += 1
        setData(asks[number])
    }

    private fun success() {
        successCount += 1
    }

    private fun fail() {
        failCount += 1
    }

    private fun setData(ask: Asks) {
        currentAsk = ask
        tvNumber.text = "${number + 1}/${asks.size}"
        text.text = ask.ask
        one.text = ask.answer[0].answer
        two.text = ask.answer[1].answer
        three.text = ask.answer[2].answer
        four.text = ask.answer[3].answer
        image.visibility = if (ask.image.isEmpty()) View.GONE else View.VISIBLE
        if (!ask.image.isNullOrEmpty()) {
            if (ask.is_svg) {
                GlideToVectorYou.justLoadImage(this, Uri.parse(ask.image), image)
            } else {
                Glide.with(image.context)
                    .load(ask.image)
                    .error(R.drawable.ic_error)
                    .into(image)
            }
        }
    }

}