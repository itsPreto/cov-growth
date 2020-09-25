package com.jmc.covgrowth.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
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
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.*
import com.jmc.covgrowth.R
import com.jmc.covgrowth.adapter.DummyAdapter
import com.jmc.covgrowth.adapter.MyClickListener
import com.jmc.covgrowth.model.Country
import com.jmc.covgrowth.model.GlobalSummary
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.bottom_sheet.*
import kotlinx.coroutines.ExperimentalCoroutinesApi

const val STATE_PEAKING : Int = 150

class MainActivity : AppCompatActivity(), MyClickListener {
    private val vm: ViewModel by viewModels()

    private lateinit var mutableListOfCountries: List<Country>

    private lateinit var myAdapter: DummyAdapter

    private var behavior: BottomSheetBehavior<*>? = null
    private var recyclerView: RecyclerView? = null

    val bottomSheetShowing = false


    private var newOffset = 342

    @ExperimentalCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(global_cases_toolbar)
        recyclerView = findViewById(R.id.recyclerView)
        checkConnectivity()

        val bottomSheet = findViewById<View>(R.id.bottomSheetContainer)
        val scrollView = findViewById<View>(R.id.scrollViewContainer)
        val bottomSheetTextView = findViewById<TextView>(R.id.textView)
        val bottomSheetTextView2 = findViewById<TextView>(R.id.textView2)


        searchView.addTextChangedListener( object :TextWatcher{

            override fun afterTextChanged(chagedText: Editable?) {
                filterList(chagedText.toString())
            }

            override fun beforeTextChanged(unchangedText: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

        })

        val handler = Handler()
        val r: Runnable = object : Runnable {
            override fun run() {
                toolbar_layout.title = "Dynamic Header"
                handler.postDelayed(this, 1500)
            }
        }
//        handler.postDelayed(r, 3000)

        behavior = BottomSheetBehavior.from(bottomSheet)
        behavior?.apply {

            /** This is how I'm currently setting/resetting the bottomSheet height*/
            peekHeight = STATE_PEAKING
            addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
                override fun onStateChanged(bottomSheet: View, newState: Int) {
                    // React to state change
                }

                override fun onSlide(bottomSheet: View, slideOffset: Float) {
                    // React to state change
                    val text1 = "BottomSheet Height: ${bottomSheet.height.toFloat() * slideOffset}"
                    val text2 = "AppBar Height: ${app_bar.height}"
                    Log.d("TAG_X",slideOffset.toString())
                    bottomSheetTextView.text = text1
                    bottomSheetTextView2.text = text2

                    app_bar.alpha = 1 - slideOffset
                }
            })
        }

        app_bar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
            Log.d("TAG_X", verticalOffset.toString())
        })
    }

    @ExperimentalCoroutinesApi
    private fun filterList(country: String) = myAdapter.updateList(vm.filter(country))

    @ExperimentalCoroutinesApi
    private fun checkConnectivity() {
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
        vm.globalSummary.observe(this, Observer {
            myAdapter = DummyAdapter(
                it,
                applicationContext,
                this
            )
            recyclerView?.apply {
                adapter = myAdapter
                visibility = VISIBLE
            }
            toolbar_layout.title = "Global - \n${it.global.totalConfirmed}"
            turnOffProgressBar()
            mutableListOfCountries = it.countries
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

        behavior?.state = STATE_HALF_EXPANDED

        //Start the Intent
        ActivityCompat.startActivity(this, intent, options.toBundle())
    }

    override fun onBackPressed() {
        behavior?.let {
            if (it.state != STATE_HIDDEN
                && it.state != STATE_COLLAPSED && it.state != STATE_HALF_EXPANDED) {
                it.setState(STATE_HALF_EXPANDED)
            } else if(it.state == STATE_HALF_EXPANDED) {
                behavior?.state = STATE_COLLAPSED
            } else {
                super.onBackPressed()
            }
        }
    }
}
