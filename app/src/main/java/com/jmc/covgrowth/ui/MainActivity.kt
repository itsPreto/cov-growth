package com.jmc.covgrowth.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityOptionsCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.dev.adnetworkm.CheckNetworkStatus
import com.jmc.covgrowth.R
import com.jmc.covgrowth.adapter.DummyAdapter
import com.jmc.covgrowth.adapter.MyClickListener
import com.jmc.covgrowth.model.GlobalSummary
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.bottom_sheet.*
import kotlinx.android.synthetic.main.newlayout.view.*
import kotlinx.coroutines.ExperimentalCoroutinesApi

class MainActivity : AppCompatActivity(),
    MyClickListener {
    private val godlyViewModel: GodlyViewModel by viewModels()

    private var behavior: BottomSheetBehavior<*>? = null
    private var recyclerView: RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        checknetworkstatus()
        val bottomSheet = findViewById<View>(R.id.bottomSheetContainer)
        val bottomSheetTextView = findViewById<TextView>(R.id.textView)
        val bottomSheetTextView2 = findViewById<TextView>(R.id.textView2)
        behavior = BottomSheetBehavior.from(bottomSheet)
        behavior?.apply {
            peekHeight = 120
            addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
                override fun onStateChanged(bottomSheet: View, newState: Int) {
                    // React to state change
                }

                override fun onSlide(bottomSheet: View, slideOffset: Float) {
                    // React to state change
                    val text1 = "BottomSheet offset: ${slideOffset.toString()}"
                    val text2 = "AppBar Height: ${app_bar.height}"
                    Log.d("TAG_X",slideOffset.toString())
                    bottomSheetTextView.text = text1
                    bottomSheetTextView2.text = text2

                    app_bar.alpha = 1 - slideOffset
                }
            })
        }
    }

    @ExperimentalCoroutinesApi
    private fun checknetworkstatus() {
        CheckNetworkStatus.getNetworkLiveData(applicationContext).observe(this, Observer { t ->
            when (t) {
                true -> {
                    setObserver()
                }
                false -> {
                    Toast.makeText(this, "No Network Connection", Toast.LENGTH_SHORT).show()
                }
                null -> {
                    // TODO: Handle the connection...
                }
            }
        })
    }

    @ExperimentalCoroutinesApi
    private fun setObserver() {
        godlyViewModel.globalSummary.observe(this, Observer {
            recyclerView?.adapter =
                DummyAdapter(
                    it,
                    applicationContext,
                    this
                )

            global_cases_tv.text = "Global - \n${it.global.totalConfirmed}"
            turnOffProgressBar()
            recyclerView?.visibility = VISIBLE
        })
    }

    private fun turnOffProgressBar() {
        if(progressBar != null) {
            progressBar.visibility = GONE
        }
    }

    override fun countrySelected(view: View, dataList: GlobalSummary, pos: Int) {

        Log.d("TAG_X", "MainActivity: ${dataList.countries[pos].country}")
        // Ordinary Intent for launching a new activity
        val intent = Intent(this, CountryDetailActivity::class.java)
        // Get the transition name from the string
        val transitionName = getString(R.string.transition_string)
        // Define the view that the animation will start from

        intent.apply {
            putExtra("position", pos)
            putExtra("datalistkey", dataList)
        }
        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
            this,
            view,  // Starting view
            transitionName // The String
        )

        //Start the Intent
        ActivityCompat.startActivity(this, intent, options.toBundle())
    }

    override fun onBackPressed() {
        behavior?.let {
            if (it.state != BottomSheetBehavior.STATE_HIDDEN) {
                it.setState(BottomSheetBehavior.STATE_HALF_EXPANDED)
            } else {
                super.onBackPressed()
            }
        }

    }
}
