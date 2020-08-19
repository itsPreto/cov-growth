package com.jmc.covgrowth.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.jmc.covgrowth.R
import kotlinx.android.synthetic.main.splash_activity.*

class Splash : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_activity)
        val animation = AnimationUtils.loadAnimation(this, R.anim.text_inflator)
        textView3.startAnimation(animation)
    }

    override fun onStart() {
        super.onStart()

        Handler().postDelayed({ /* Create an Intent that will start the Menu-Activity. */
            val mainIntent = Intent(this@Splash, MainActivity::class.java)
            this@Splash.startActivity(mainIntent)
            finish()
        }, 10)

    }



}