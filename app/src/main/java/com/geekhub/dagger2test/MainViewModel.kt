package com.geekhub.dagger2test

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.geekhub.dagger2test.entity.Country
import com.geekhub.dagger2test.entity.ListCountry
import com.geekhub.dagger2test.entity.ListStatByDay
import com.geekhub.dagger2test.network.NetworkRepository

class MainViewModel : ViewModel() {

    private var countries: MutableLiveData<ListCountry>? = null
    private var statByDay: MutableLiveData<ListStatByDay>? = null
    private val networkRepository = NetworkRepository()

    fun getCountries(): LiveData<ListCountry>? {
        if (countries == null) {
            countries = MutableLiveData<ListCountry>()
        }
        loadUsers()
        return countries
    }
    fun getStatByDay(country: String): MutableLiveData<ListStatByDay>? {
        if (statByDay == null) {
            statByDay = MutableLiveData<ListStatByDay>()
        }
        var countryEntity = countries?.value?.find { it.country == country }
        if (countryEntity != null) {
            loadStatByDay(countryEntity)
        }
        return statByDay
    }

    private fun loadUsers() {

        countries = networkRepository.loadCountries()
    }

    private fun loadStatByDay(country: Country){
        statByDay = networkRepository.loadStatByDay(country)
    }

    fun getCountriesApiToCountrySpinner(listCountry: ListCountry): MutableList<String> {
        var listCountryForSpinner = mutableListOf<String>()
        for (i in listCountry){
            listCountryForSpinner.add(i.country)
        }
        listCountryForSpinner.sortBy { it.toLowerCase() }
        return listCountryForSpinner
    }
}

