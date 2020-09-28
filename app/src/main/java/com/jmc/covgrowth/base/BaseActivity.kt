package com.jmc.covgrowth.base

import android.os.Bundle
import android.os.Handler
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.app.AppCompatActivity

open class BaseActivity : AppCompatActivity(), View.OnClickListener{

    private val mHandler = Handler()
    private val TIMEOUT = 2000L

    private val mRunnable: Runnable = Runnable {
        reactToIdleState()
    }

    private fun reactToIdleState() {
        TODO("Not yet implemented")
    }

    private fun resetHandler() {
        mHandler.removeCallbacks(mRunnable)
        mHandler.postDelayed(mRunnable, TIMEOUT)
    }

    fun myCustomOnClick( view: View) {

    }

    override fun onClick(view: View) {
        resetHandler()
        myCustomOnClick(view)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        resetHandler()
        return false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mHandler.postDelayed(mRunnable, TIMEOUT)
        onSessionCreate(savedInstanceState)
    }

    fun onSessionCreate(savedInstanceState: Bundle?) {

    }
}