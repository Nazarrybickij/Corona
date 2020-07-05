package com.geekhub.dagger2test.network

import com.geekhub.dagger2test.entity.ListCountry
import com.geekhub.dagger2test.entity.ListStatByDay
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path


interface JSONPlaceHolderApi {
    @GET("/countries")
    fun getPostCountries(): Call<ListCountry?>?

    @GET("/total/dayone/country/{country}")
    fun getPostStatByDay(@Path ("country") country:String):Call<ListStatByDay?>?

}