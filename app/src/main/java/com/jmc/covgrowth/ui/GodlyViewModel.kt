package com.jmc.covgrowth.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.jmc.covgrowth.api.Repository
import com.jmc.covgrowth.model.GlobalSummary
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect

class GodlyViewModel : ViewModel() {
    private val repository: Repository =
        Repository()

//    private lateinit var globalSummary : GlobalSummary

    @ExperimentalCoroutinesApi
    val globalSummary: LiveData<GlobalSummary> = liveData(Dispatchers.IO) {
        val data = repository.dataFlow
        data.collect { response ->
            Log.d("TAG_X", response.countries.toString())
            emit(response)
        }
    }
}