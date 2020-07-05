package com.geekhub.dagger2test.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Country(
    @SerializedName("Country")
    @Expose
    val country:String,
    @SerializedName("Slug")
    @Expose
    val slug:String,
    @SerializedName("ISO2")
    @Expose
    val iso:String
)