package com.jmc.covgrowth.model


import android.annotation.SuppressLint
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import android.os.Parcelable

@SuppressLint("ParcelCreator")
@Parcelize
data class GlobalSummary(
    @SerializedName("Countries")
    var countries: MutableList<Country>,
    @SerializedName("Date")
    val date: String, // 2020-08-11T21:43:33Z
    @SerializedName("Global")
    val global: Global
) : Parcelable