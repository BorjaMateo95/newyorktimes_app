package com.borja.pruebanewyorktimes.data.remote.dto

import com.google.gson.annotations.SerializedName

data class NewsResponse (
    @SerializedName("status") val status : String,
    @SerializedName("copyright") val copyright : String,
    @SerializedName("num_results") val num_results : Int,
    @SerializedName("results") val results : List<Result>

)