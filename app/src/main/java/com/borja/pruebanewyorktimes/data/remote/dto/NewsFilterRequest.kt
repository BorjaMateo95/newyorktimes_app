package com.borja.pruebanewyorktimes.data.remote.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class NewsFilterRequest(
    @Expose
    @SerializedName("type")
    var type: String,

    @Expose
    @SerializedName("period")
    var period : Int,

    @Expose
    @SerializedName("most_shared_by")
    var mostSharedBy : String?
)
