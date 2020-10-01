package com.deadely.ege.ui.home

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.deadely.ege.repository.Repository

class HomeViewModel @ViewModelInject constructor(private val repository: Repository) :
    ViewModel() {

}