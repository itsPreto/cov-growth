package com.jmc.covgrowth.ui

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.jmc.covgrowth.R
import com.jmc.covgrowth.model.GlobalSummary
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_read.*
import kotlinx.android.synthetic.main.content_read.*
import java.lang.Exception
import java.util.*

class CountryDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_read)
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            this.setDisplayHomeAsUpEnabled(true)
            this.setDisplayShowHomeEnabled(true)
            this.setDisplayShowTitleEnabled(false)
        }
        val imgUrl = "https://flagpedia.net/data/flags/normal/"
        toolbar.setNavigationIcon(R.drawable.back)
        val datalist = intent.getParcelableExtra<GlobalSummary>("datalistkey")
        val position = intent.getIntExtra("position", -1)
        Log.d("TAG_X", position.toString())
        Log.d("TAG_X", "MainActivity: ${datalist.countries[position].country}")

        val chosenCountry = datalist.countries[position]

        Picasso.get().load(imgUrl +
                "${chosenCountry.countryCode.toLowerCase(Locale.ROOT)}.png")
            .resize(800,500)
            .into(header_logo, object : Callback {
                override fun onSuccess() {
                    supportStartPostponedEnterTransition()
                }

                override fun onError(e: Exception?) {
                    supportStartPostponedEnterTransition()
                }

            })


        val transition = AnimationUtils.loadAnimation(this, R.anim.transition_animation)
        mainCardViewContainer.startAnimation(transition)
        header_logo.startAnimation(transition)

        country.text = chosenCountry.country
        totalConfirmed.apply {
            text = chosenCountry.totalConfirmed.toString()
            setTextColor(Color.YELLOW)
        }
        totalRecovered.apply {
            text = chosenCountry.totalRecovered.toString()
            setTextColor(Color.GREEN)
        }
        totalDeaths.apply {
            text = chosenCountry.totalDeaths.toString()
            setTextColor(Color.RED)
        }
    }
}
