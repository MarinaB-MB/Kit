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
        } else {
            failCount += 1
        }
        getNext()
    }

    private fun getNext() {
        number += 1
        if (asks[number].number == LAST_ITEM)
            openListAnswersScreen(successCount, failCount, variant)
        else setData(asks[number])
    }

    private fun openListAnswersScreen(successCount: Int, failCount: Int, variant: Variant?) {
        val bundle = Bundle().apply {
            putInt(ResultListActivity.SUCCESS_COUNT, successCount)
            putInt(ResultListActivity.FAIL_COUNT, successCount)
            putParcelableArrayList(ResultListActivity.CURRENT_VARIANT, asks as ArrayList<Asks>)
        }
        val intent = Intent(this, ResultListActivity::class.java)
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
                    .error(R.drawable.ic_error)
                    .into(image)
            }
        }
    }
}