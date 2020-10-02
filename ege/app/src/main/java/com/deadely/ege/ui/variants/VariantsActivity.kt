package com.deadely.ege.ui.variants

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.deadely.ege.R
import com.deadely.ege.model.Asks
import com.deadely.ege.model.Variant
import com.deadely.ege.utils.DataState
import com.deadely.ege.utils.makeGone
import com.deadely.ege.utils.makeVisible
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
                    llOne.makeGone()
                }
                is DataState.Error -> {
                    dataState.exception.printStackTrace()
                    pvLoad.makeGone()
                    llOne.makeGone()
                    Log.e("VariantsActivity.TAG", "initSubscribe: ${dataState.exception.message}")
                }
                is DataState.Success -> {
                    pvLoad.makeGone()
                    llOne.makeVisible()
                    Log.e("VariantsActivity.TAG", "initSubscribe: ${dataState.data}")
                    variant?.asks?.let {
                        for (ask in it) {
                            for (apiAsk in dataState.data) {
                                if (ask == apiAsk._id) {
                                    asks.add(apiAsk)
                                }
                            }
                        }

                        asks[0]?.let {
                            setData(asks[0])
                        }
                    }

                }
            }
        })
    }

    private fun initView() {

    }

    private fun setData(ask: Asks) {
        tvAsk.text = ask.ask
        ivAsk.visibility = if (ask.image.isEmpty()) View.GONE else View.VISIBLE
        Glide.with(baseContext)
            .load(ask.image)
            .error(R.drawable.ic_error)
            .into(ivAsk)
    }
}