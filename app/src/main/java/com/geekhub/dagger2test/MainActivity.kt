package com.geekhub.dagger2test

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.geekhub.dagger2test.entity.Country
import com.geekhub.dagger2test.entity.ListStatByDay
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.include_caseupdate.*
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject


class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var sp: SPStorage
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        App.appComponent?.inject(this)

        setGountry()

        country_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                var selectedCountry = parent?.getItemAtPosition(position).toString()
                setStatByDay(selectedCountry)
                sp.setCountry(selectedCountry)
            }
        }

        swipeRefreshLayout.setOnRefreshListener {
            setStatByDay(sp.getCountry())
        }
    }

    fun setGountry() {
        viewModel.getCountries()?.observe(this, Observer {
            var adapter = ArrayAdapter<String>(
                this,
                android.R.layout.simple_spinner_item,
                viewModel.getCountriesApiToCountrySpinner(it)
            )
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            country_spinner.adapter = adapter
            country_spinner.setSelection(adapter.getPosition(sp.getCountry()))
        })
    }

    fun setStatByDay(selectedCountry: String) {
        viewModel.getStatByDay(selectedCountry)
            ?.observe(this@MainActivity, Observer {
                setValueInView(it)
                if (swipeRefreshLayout.isRefreshing){
                    swipeRefreshLayout.isRefreshing = false
                }
            })
    }

    fun setValueInView(it: ListStatByDay) {
        if (it.isNotEmpty()) {
            val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
            var time = SimpleDateFormat("MMMM d", Locale.ENGLISH)
            var date = format.parse(it.last().Date)
            var str = time.format(date)
            case_update_text.text = "Case Update(${it.last().Country})"
            update_time_text.text = "Newest Update $str"
            infected_count.text = it.last().Confirmed.toString()
            death_count.text = it.last().Deaths.toString()
            recovered_count.text = it.last().Recovered.toString()
            if (it.last().Confirmed >= 10000 || it.last().Deaths >= 10000 || it.last().Recovered >= 10000) {
                infected_count.textSize = 22f
                death_count.textSize = 22f
                recovered_count.textSize = 22f
            } else {
                infected_count.textSize = 35f
                death_count.textSize = 35f
                recovered_count.textSize = 35f
            }
        } else {
            case_update_text.text = "Case Update(no information)"
            update_time_text.text = "Newest Update"
            infected_count.text = "0"
            death_count.text = "0"
            recovered_count.text = "0"
        }
    }
}

