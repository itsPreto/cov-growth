package com.jmc.covgrowth.ui

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import android.os.Build
import android.util.Log
import androidx.lifecycle.*
import androidx.lifecycle.ViewModel
import com.jmc.covgrowth.api.Repository
import com.jmc.covgrowth.model.Country
import com.jmc.covgrowth.model.GlobalSummary
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import java.util.*

class ViewModel : ViewModel() {
    private val repository: Repository = Repository()

    @ExperimentalCoroutinesApi
    val countryDataFlow: LiveData<GlobalSummary> = repository.dataFlow.asLiveData()

    @ExperimentalCoroutinesApi
    fun filter(filteredCountry: String): MutableList<Country> {
        return countryDataFlow.value?.countries?.filter{
            filteredCountry.toLowerCase(Locale.ROOT) in it.country.toLowerCase(Locale.ROOT)
        } as MutableList<Country>
    }
}

object CheckNetworkStatus : ViewModel() {
    val networkLiveData: MutableLiveData<Boolean> = MutableLiveData()
    fun Context.getConnectivityLiveData() : LiveData<Boolean> {
        val connectivityManager =
            this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkCallback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network?) = networkLiveData.postValue(true)
            override fun onLost(network: Network?) = networkLiveData.postValue(false)
        }
        with(Build.VERSION.SDK_INT) {
            if (this >= Build.VERSION_CODES.N) {
                connectivityManager.registerDefaultNetworkCallback(networkCallback)
                return@with
            } else {
                connectivityManager.registerNetworkCallback(
                    NetworkRequest.Builder()
                        .build(), networkCallback
                )
                return@with
            }
        }
        return networkLiveData
    }
}