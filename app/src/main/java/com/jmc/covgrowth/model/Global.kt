package com.jmc.covgrowth.model


import android.annotation.SuppressLint
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import android.os.Parcelable

@SuppressLint("ParcelCreator")
@Parcelize
data class Global(
    @SerializedName("NewConfirmed")
    val newConfirmed: Int, // 227941
    @SerializedName("NewDeaths")
    val newDeaths: Int, // 4913
    @SerializedName("NewRecovered")
    val newRecovered: Int, // 164695
    @SerializedName("TotalConfirmed")
    val totalConfirmed: Int, // 20088890
    @SerializedName("TotalDeaths")
    val totalDeaths: Int, // 736223
    @SerializedName("TotalRecovered")
    val totalRecovered: Int // 12279869
) : Parcelable