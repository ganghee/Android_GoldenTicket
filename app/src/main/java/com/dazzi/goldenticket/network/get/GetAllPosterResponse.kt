package com.dazzi.goldenticket.network.get


import com.dazzi.goldenticket.data.ShowAllData
import com.google.gson.annotations.SerializedName

data class GetAllPosterResponse(
    @SerializedName("data")
    val `data`: List<ShowAllData>,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Int,
    @SerializedName("success")
    val success: Boolean
)