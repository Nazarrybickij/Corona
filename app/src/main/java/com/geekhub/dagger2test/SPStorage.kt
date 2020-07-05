package com.geekhub.dagger2test

import android.content.Context
import android.content.SharedPreferences
import android.content.res.Resources
import android.util.Log
import com.geekhub.dagger2test.entity.ListCountry
import com.google.gson.Gson
import javax.inject.Inject


class SPStorage @Inject constructor(private var context: Context) {
    private var sp: SharedPreferences =
        context.getSharedPreferences("network", Context.MODE_PRIVATE)

    companion object {
        private val LIST_COUNTRY = "listcountry"
        private val COUNTRY = "country"
    }

    fun setCountry(country: String) {
        val editor = sp.edit()
        editor.putString(COUNTRY,country)
        editor.apply()
    }

    fun getCountry(): String {
        val country = sp.getString(COUNTRY,"Ukraine")
        return country!!
    }

    fun setListCountry(listCountry: ListCountry) {
        val editor = sp.edit()
        val gson = Gson()
        var listCountryJson = gson.toJson(listCountry)
        editor.putString(LIST_COUNTRY, listCountryJson)
        editor.apply()
    }

    fun getListCountry(): ListCountry {
        val gson = Gson()
        val def = App.getResources?.getString(R.string.defold_listcountry)
        val json: String? = sp.getString(LIST_COUNTRY, def)
        var listCountry = gson.fromJson(json, ListCountry::class.java)
        return listCountry
    }

}