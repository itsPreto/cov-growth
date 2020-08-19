package com.jmc.covgrowth.api

import com.jmc.covgrowth.api.RetrofitClient
import com.jmc.covgrowth.api.Webservice
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow

class Repository {

    private var client: Webservice = RetrofitClient.retrofit

    @ExperimentalCoroutinesApi
    suspend fun getTodo() = client.getTodo()

    @ExperimentalCoroutinesApi
    suspend fun searchCountry() = client.getTodo()

    @ExperimentalCoroutinesApi
    var dataFlow = flow {
        emit(getTodo())
    }

    @ExperimentalCoroutinesApi
    var searchFlow = flow {
        emit(getTodo())
    }
}