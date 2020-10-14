package com.deadely.ege.ui.variants

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.deadely.ege.R
import com.deadely.ege.base.FIZIKA
import com.deadely.ege.base.INFORMATIKA
import com.deadely.ege.base.MATEMATIKA
import com.deadely.ege.base.RYSSKIU
import com.deadely.ege.model.Answer
import com.deadely.ege.model.Asks
import com.deadely.ege.model.Variant
import com.deadely.ege.ui.resultlist.ResultListActivity
import com.deadely.ege.utils.DataState
import com.deadely.ege.utils.Utils
import com.deadely.ege.utils.makeGone
import com.deadely.ege.utils.makeVisible
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_variants.*
import java.util.*


@AndroidEntryPoint
class VariantsActivity : AppCompatActivity(R.layout.activity_variants) {
    companion object {
        const val VARIANT = "VariantsActivity.VARIANT"
    }

    private val LAST_ITEM = -1
    private val variantsViewModel: VariantsViewModel by viewModels()

    private var variant: Variant? = null
    private var asks: MutableList<Asks> = mutableListOf()
    private var currentAsk: Asks? = null
    private var successCount = 0
    private var failCount = 0
    private var number = 0
    private var points = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        intent?.extras?.let {
            variant = it.getParcelable<Variant>(VARIANT)
        }
        initObserver()
    }

    private fun initObserver() {
        variantsViewModel.asks.observe(this, { dataState ->
            when (dataState) {
                is DataState.Loading -> {
                    pvLoad.makeVisible()
                    llContent.makeGone()
                }
                is DataState.Error -> {
                    dataState.exception.printStackTrace()
                    pvLoad.makeGone()
                    llContent.makeGone()
                    Log.e("VariantsActivity.TAG", "initSubscribe: ${dataState.exception.message}")
                }
                is DataState.Success -> {
                    Log.e("VariantsActivity.TAG", "initSubscribe: ${dataState.data}")
                    pvLoad.makeGone()
                    llContent.makeVisible()
                    variant?.asks?.let { list ->
                        for (ask in list) {
                            for (apiAsk in dataState.data) {
                                if (ask == apiAsk._id) {
                                    asks.add(apiAsk)
                                }
                            }
                        }
                        asks = asks.sortedBy { it.number }.toMutableList()
                        asks.add(Asks())
                        setData(asks[number])
                    }
                }
            }
        })
    }

    fun checkAnswer(view: View) {
        var rightAnswer: Answer? = null
        currentAsk?.answer?.let {
            for (answer in it) {
                if (answer.right) {
                    rightAnswer = answer
                }
            }
        }
        if ((view as TextView).text == rightAnswer?.answer) {
            successCount += 1
            variant?.eid?.let { checkNumber(it) }
        } else {
            failCount += 1
        }
        getNext()
    }

    private fun checkNumber(eid: String) {
        when (eid) {
            MATEMATIKA -> {
                when (currentAsk?.number) {
                    in 1..12 -> {
                        points += 1
                    }
                    in 13..15 -> {
                        points += 2
                    }
                    in 16..17 -> {
                        points += 3
                    }
                    in 18..19 -> {
                        points += 4
                    }
                }
            }
            INFORMATIKA -> {
                when (currentAsk?.number) {
                    in 1..24 -> {
                        points += 1
                    }
                    in 25..27 -> {
                        points += 2
                    }
                }
            }
            FIZIKA -> {
                when (currentAsk?.number) {
                    in 1..4 -> {
                        points += 1
                    }
                    in 8..10 -> {
                        points += 1
                    }
                    in 13..15 -> {
                        points += 1
                    }
                    in 19..20 -> {
                        points += 1
                    }
                    in 22..23 -> {
                        points += 1
                    }
                    in 25..26 -> {
                        points += 1
                    }
                    in 5..7 -> {
                        points += 2
                    }
                    in 11..12 -> {
                        points += 2
                    }
                    in 16..18 -> {
                        points += 2
                    }
                    21 -> {
                        points += 2
                    }
                    24 -> {
                        points += 2
                    }
                    26 -> {
                        points += 2
                    }
                }
            }
            RYSSKIU -> {
                when (currentAsk?.number) {
                    in 1..7 -> {
                        points += 1
                    }
                    in 9..15 -> {
                        points += 1
                    }
                    in 17..25 -> {
                        points += 1
                    }
                    8 -> {
                        points += 5
                    }
                    16 -> {
                        points += 2
                    }
                }
            }
        }
    }

    private fun getNext() {
        number += 1
        if (asks[number].number == LAST_ITEM) {
            one.isEnabled = false
            two.isEnabled = false
            three.isEnabled = false
            four.isEnabled = false
            openListAnswersScreen(successCount, failCount, variant)
        } else setData(asks[number])
    }

    private fun openListAnswersScreen(successCount: Int, failCount: Int, variant: Variant?) {
        val bundle = Bundle().apply {
            putInt(ResultListActivity.SUCCESS_COUNT, successCount)
            putInt(ResultListActivity.FAIL_COUNT, failCount)
            putInt(ResultListActivity.POINTS, points)
            putParcelableArrayList(ResultListActivity.CURRENT_VARIANT, asks as ArrayList<Asks>)
        }
        val intent = Intent(this, ResultListActivity::class.java)
        intent.putExtras(bundle)
        startActivity(intent)
        finish()
    }

    private fun setData(ask: Asks) {
        currentAsk = ask
        val templateString = "${number + 1}/${asks.size - 1}"
        val spannableString = SpannableString(templateString)
        spannableString.setSpan(
            ForegroundColorSpan(Utils.getColor(R.color.black)),
            templateString.length - 3,
            templateString.length,
            0
        )
        tvNumber.text = spannableString
        tvText.isVerticalScrollBarEnabled = false
        val value =
            "<html><body dir=\\\"rtl\\\"; style=\\\"text-align:justify;\\\">${ask.ask}</body></html>"
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            tvText.text = Html.fromHtml(value, Html.FROM_HTML_MODE_COMPACT);
        } else {
            tvText.text = Html.fromHtml(value);
        }
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
                    .placeholder(R.drawable.ic_image_lost)
                    .error(R.drawable.ic_error)
                    .into(image)
            }
        }
    }
}