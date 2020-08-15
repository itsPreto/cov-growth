package com.jmc.covgrowth.model

import android.annotation.SuppressLint
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import android.os.Parcelable

@SuppressLint("ParcelCreator")
@Parcelize
data class Country(
    @SerializedName("Country")
    val country: String, // Afghanistan
    @SerializedName("CountryCode")
    val countryCode: String, // AF
    @SerializedName("Date")
    val date: String, // 2020-08-11T21:43:33Z
    @SerializedName("NewConfirmed")
    val newConfirmed: Int, // 108
    @SerializedName("NewDeaths")
    val newDeaths: Int, // 16
    @SerializedName("NewRecovered")
    val newRecovered: Int, // 268
    @SerializedName("Premium")
    val premium: Premium,
    @SerializedName("Slug")
    val slug: String, // afghanistan
    @SerializedName("TotalConfirmed")
    val totalConfirmed: Int, // 37162
    @SerializedName("TotalDeaths")
    val totalDeaths: Int, // 1328
    @SerializedName("TotalRecovered")
    val totalRecovered: Int // 26228
) : Parcelable