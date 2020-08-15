package com.jmc.covgrowth.adapter

import android.content.Context
import android.graphics.Color
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.jmc.covgrowth.R
import com.jmc.covgrowth.model.GlobalSummary
import com.squareup.picasso.Picasso
import jp.wasabeef.picasso.transformations.CropCircleTransformation
import java.util.*

interface MyClickListener{
    fun countrySelected(view: View, dataList: GlobalSummary, pos: Int)
}

class DummyAdapter(
    private var dataList: GlobalSummary,
    private val context: Context,
    private val cellClickListener: MyClickListener
) :
    RecyclerView.Adapter<DummyAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.listnews,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return dataList.countries.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val dataModel = dataList.countries[position]
        val date = "- ${dataModel.date.subSequence(0 , 10)}"

        val totalConfirmed = "Total Confirmed : ${dataModel.totalConfirmed}"
        spannableColorChange(dataModel.totalConfirmed)

        val totalDeaths = "Total Deaths : ${dataModel.totalDeaths}"
        spannableColorChange(dataModel.totalDeaths)

        val totalRecovered = "Total Recovered : ${dataModel.totalRecovered}"
        spannableColorChange(dataModel.totalRecovered)

        Picasso.get().load("https://flagpedia.net/data/flags/normal/" +
                            "${dataModel.countryCode.toLowerCase(Locale.ROOT)}.png")
            .resize(250,250)
            .transform(CropCircleTransformation())
            .into(holder.logo)

        val transition =
            AnimationUtils.loadAnimation(context, R.anim.transition_animation)
        holder.itemView.startAnimation(transition)

        holder.itemView.setOnClickListener {
            cellClickListener.countrySelected(holder.itemView, dataList, holder.adapterPosition)
            Log.d("TAG_X", "adapter : ${holder.adapterPosition}")
        }


//        holder.totalConfirmed.text =  totalConfirmed
//        holder.totalDeaths.text = totalDeaths
//        holder.totalRecovered.text = totalRecovered
    }

    private fun spannableColorChange(cases: Int) {
        val spannable = SpannableStringBuilder("$cases")
        spannable.setSpan(
            ForegroundColorSpan(Color.RED),
            /* start index */ 0, /* end index */ cases.toString().length,
            Spannable.SPAN_EXCLUSIVE_INCLUSIVE
        )
    }

    class ViewHolder(itemLayoutView: View) : RecyclerView.ViewHolder(itemLayoutView) {

        var logo: ImageView = itemLayoutView.findViewById(R.id.logo)
//        var totalConfirmed: TextView = itemLayoutView.findViewById(R.id.totalConfirmed)
//        var country: TextView = itemLayoutView.findViewById(R.id.countryLabel)
//        var totalDeaths: TextView = itemLayoutView.findViewById(R.id.totalDeaths)
//        var totalRecovered: TextView = itemLayoutView.findViewById(R.id.totalRecovered)
        // var logout: ImageView = itemLayoutView.findViewById(R.id.listidentifierlogout)
        //   lateinit var description: TextView
        //  lateinit var image:ImageView


    }
}