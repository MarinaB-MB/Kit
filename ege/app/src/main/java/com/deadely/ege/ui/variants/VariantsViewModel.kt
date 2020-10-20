package com.deadely.ege.ui.variants

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deadely.ege.base.FIZIKA
import com.deadely.ege.base.INFORMATIKA
import com.deadely.ege.base.MATEMATIKA
import com.deadely.ege.base.RYSSKIU
import com.deadely.ege.model.Asks
import com.deadely.ege.repository.Repository
import com.deadely.ege.utils.DataState
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class VariantsViewModel @ViewModelInject constructor(private val repository: Repository) :
    ViewModel() {

    private val mAsks = MutableLiveData<DataState<List<Asks>>>()
    val asks: LiveData<DataState<List<Asks>>>
        get() = mAsks

    var currentAsk: Asks? = null
    var points = 0

    init {
        getAsks()
    }

    private fun getAsks() {
        viewModelScope.launch {
            repository.getAsks()
                .onEach { dataState -> subscribe(dataState) }
                .launchIn(viewModelScope)
        }
    }

    private fun subscribe(dataState: DataState<List<Asks>>) {
        when (dataState) {
            is DataState.Loading -> {
                mAsks.postValue(DataState.Loading)
            }
            is DataState.Error -> {
                mAsks.postValue(DataState.Error(dataState.exception))
            }
            is DataState.Success -> {
                mAsks.postValue(DataState.Success(dataState.data))
            }
        }
    }

    fun checkNumber(eid: String) {
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
}