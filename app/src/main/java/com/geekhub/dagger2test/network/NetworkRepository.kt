package com.geekhub.dagger2test.network

import android.content.Context
import android.content.SharedPreferences
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.geekhub.dagger2test.App
import com.geekhub.dagger2test.SPStorage
import com.geekhub.dagger2test.di.components.DaggerAppComponent
import com.geekhub.dagger2test.entity.Country
import com.geekhub.dagger2test.entity.ListCountry
import com.geekhub.dagger2test.entity.ListStatByDay
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class NetworkRepository {
    val postCountriesLiveData = MutableLiveData<ListCountry>()
    val postStatDayLiveData = MutableLiveData<ListStatByDay>()
    @Inject
    lateinit var spStorage:SPStorage

    init {
        App.appComponent?.inject(this)
    }
    fun loadCountries(): MutableLiveData<ListCountry> {
        NetworkService.instance?.jSONApi?.getPostCountries()?.enqueue(object :
            Callback<ListCountry?> {
            override fun onResponse(
                call: Call<ListCountry?>,
                response: Response<ListCountry?>
            ) {
                val post = response.body()
                if (post != null) {
                    spStorage.setListCountry(post)
                }
                postCountriesLiveData.value = post
            }
            override fun onFailure(call: Call<ListCountry?>, t: Throwable) {
                postCountriesLiveData.value = spStorage.getListCountry()
            }
        })
        return postCountriesLiveData
    }

    fun loadStatByDay(country: Country): MutableLiveData<ListStatByDay> {
        NetworkService.instance?.jSONApi?.getPostStatByDay(country.iso)?.enqueue(object :
            Callback<ListStatByDay?> {
            override fun onResponse(
                call: Call<ListStatByDay?>,
                response: Response<ListStatByDay?>
            ) {
                val post = response.body()
                postStatDayLiveData.value = post
            }
            override fun onFailure(call: Call<ListStatByDay?>, t: Throwable) {
                Toast.makeText(App.context,"No Internet",Toast.LENGTH_LONG).show()
            }
        })
        return postStatDayLiveData
    }
}